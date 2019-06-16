import io.logz.sender.com.google.gson.Gson;
import io.logz.sender.com.google.gson.JsonObject;
import org.mockserver.model.HttpResponse;
import org.yaml.snakeyaml.Yaml;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.logging.Logger;


public class Office365Apis {
    private static final Logger LOGGER = Logger.getLogger(Office365Apis.class.getName());

    private final LogzioSender sender;

    private final String logzio_token;
    private final String logzio_host;
    private final String tenant_id;
    private final String client_id;
    private final String client_secret;
    private final String access_token;
    private final String publisherId;
    private final String contentType;
    private final int interval;

    //FIXME the below parameters aren't shared between the other endpoints, how to get the input?
    private String acceptLanguage;
    private String webhookAuthId;
    private String contentId;
    private String organizationId;
    private String startTime;
    private String endTime;

    public Office365Apis() throws Exception {
        Office365 office365 = configYamil();
        logzio_token = office365.getLogzioToken();
        logzio_host = "https://listener.logz.io:8071/";
        tenant_id = office365.getTenantId();
        client_id = office365.getClientId();
        client_secret = office365.getClientSecret();
        publisherId = office365.getPublisherId();
        contentType = office365.getContentType();
        interval = office365.getInterval();

        sender = new LogzioSender(logzio_token, logzio_host);
        access_token = createConnectionToAzurePortal();
    }

    private Office365 configYamil() throws IOException {
        Yaml yaml = new Yaml();
        try (InputStream in = Main.class
                .getResourceAsStream("/conf.yml")) {
            Office365 office365 = yaml.loadAs(in, Office365.class);
            return office365;
        }
    }

    private String createConnectionToAzurePortal() throws Exception {
        HttpURLConnection httpConnection = Connection.createHttpConnection("https://login.microsoftonline.com/" + tenant_id + "/oauth2/token");
        HttpResponse httpResponse = Connection.connect(httpConnection, client_id, client_secret);
        Gson gson = new Gson();
        JsonObject jsonObject = gson.fromJson(httpResponse.getBody().toString(), JsonObject.class);
        return jsonObject.get("access_token").getAsString();
    }

    public void startSubscription() throws Exception {
        String url = "https://manage.office.com/api/v1.0/" + tenant_id + "/activity/feed/";
        SubscriptionRequest subscriptionRequest = new SubscriptionRequest(url, access_token, contentType, publisherId);
        ManagementActivityApi.startSubscription(subscriptionRequest);
    }

    public void stopSubscription() throws Exception {
        String url = "https://manage.office.com/api/v1.0/" + tenant_id + "/activity/feed/";
        SubscriptionRequest subscriptionRequest = new SubscriptionRequest(url, access_token, contentType, publisherId);
        ManagementActivityApi.stopSubscription(subscriptionRequest);
    }

    public void listCurrentSubscriprions() throws Exception {
        String url = "https://manage.office.com/api/v1.0/" + tenant_id + "/activity/feed/";
        SubscriptionRequest subscriptionRequest = new SubscriptionRequest(url, access_token, null, publisherId);
        HttpResponse httpResponse = ManagementActivityApi.listCurrentSubscriprions(subscriptionRequest);
    }

    public void listAvailableContent() {
        String url = "https://manage.office.com/api/v1.0/" + tenant_id + "/activity/feed/";

        Date time1 = null;
        Date time2 = null;
        try {
            time1 = new SimpleDateFormat("dd/MM/yyyy").parse(startTime);
            time2 = new SimpleDateFormat("dd/MM/yyyy").parse(endTime);
        } catch (ParseException e) {
            LOGGER.warning("couldn't parse the time");
        }

        SubscriptionRequest subscriptionRequest = new SubscriptionRequest(url, access_token, contentType, publisherId, time1, time2);
        ArrayList<AvailableContent> availableContents = null;
        try {
            availableContents = ManagementActivityApi.listAvailableContent(subscriptionRequest);
        } catch (Exception e) {
            LOGGER.warning("failed while getting the list of the available content");
        }

        for (AvailableContent availableContent :
                availableContents) {
            String logs = getContentLogs(availableContent);
            String timeStamp = availableContent.getContentCreated();

            String parsedLogs = parseLogs(logs, timeStamp);
            sender.send(parsedLogs);
        }
    }

    private String parseLogs(String logs, String timeStamp) {
        return "{\"@timestamp\":\"" + timeStamp + "\",\n" + "\"body\":\"" + logs + "\"}";
    }

    private String getContentLogs(AvailableContent availableContent) {
        try {
            URL url = new URL(availableContent.getContentUri());
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            return conn.getResponseMessage();
        } catch (Exception e) {
            LOGGER.warning("couldn't connect to the uri");
        }
        return null;
    }

    public void listNotifications() throws Exception {
        String url = "https://manage.office.com/api/v1.0/" + tenant_id + "/activity/feed/";
        SubscriptionRequest subscriptionRequest = new SubscriptionRequest(url, access_token, contentType, publisherId);
        ManagementActivityApi.listNotifications(subscriptionRequest);
    }

    public void retrieveResourceFriendlyNames() throws Exception {
        String url = "https://manage.office.com/api/v1.0/" + tenant_id + "/activity/feed/";
        ManagementActivityApi.retrieveResourceFriendlyNames(publisherId, acceptLanguage, access_token, url);
    }

    public void recievingNotifications() throws Exception {
        String path = "https://webhook.myapp.com/o365/ "; //FIXME
        ManagementActivityApi.receivingNotifications(path, contentType, webhookAuthId);
    }

    public void retrievingContent() throws Exception {
        String path = "https://manage.office.com/api/v1.0/";
        AvailableContent availableContent = ManagementActivityApi.retrievingContent(path, contentId, access_token, organizationId);
        String logs = getContentLogs(availableContent);
        String timeStamp = availableContent.getContentCreated();

        String parsedLogs = parseLogs(logs, timeStamp);
        sender.send(parsedLogs);
    }

    public int getInterval() {
        return interval;
    }
}
