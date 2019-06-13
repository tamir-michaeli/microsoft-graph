import io.logz.sender.com.google.gson.Gson;
import io.logz.sender.com.google.gson.JsonObject;
import org.mockserver.model.HttpResponse;
import org.yaml.snakeyaml.Yaml;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.text.SimpleDateFormat;
import java.util.Date;


public class Office365Apis {
    public static String logzio_token;
    private static String tenant_id;
    private static String client_id;
    private static String client_secret;
    private static String access_token;
    private static String publisherId;
    private static String contentType;
    public static int interval;

    //FIXME the below parameters aren't shared between the other endpoints, how to get the input?
    private static String acceptLanguage;
    private static String webhookAuthId;
    private static String contentId;
    private static String organizationId;
    private static String startTime;
    private static String endTime;

    public static void getInput() throws IOException {
        Office365 office365 = configYamil();
        logzio_token = office365.getLogzioToken();
        tenant_id = office365.getTenantId();
        client_id = office365.getClientId();
        client_secret = office365.getClientSecret();
        publisherId = office365.getPublisherId();
        contentType = office365.getContentType();
        interval = office365.getInterval();
    }

    private static Office365 configYamil() throws IOException {
        Yaml yaml = new Yaml();
        try (InputStream in = Main.class
                .getResourceAsStream("/conf.yml")) {
            Office365 office365 = yaml.loadAs(in, Office365.class);
            return office365;
        }
    }

    public static void createConnectionToAzurePortal() throws Exception {
        HttpURLConnection httpConnection = Connection.createHttpConnection("https://login.microsoftonline.com/" + tenant_id + "/oauth2/token");
        HttpResponse httpResponse = Connection.connect(httpConnection, client_id, client_secret);
        Gson gson = new Gson();
        JsonObject jsonObject = gson.fromJson(httpResponse.getBody().toString(), JsonObject.class);
        access_token = jsonObject.get("access_token").getAsString();
    }

    public static void startSubscription() throws Exception {
        String url = "https://manage.office.com/api/v1.0/" + tenant_id + "/activity/feed/";
        SubscriptionRequest subscriptionRequest = new SubscriptionRequest(url, access_token, contentType, publisherId);
        HttpResponse httpResponse = ManagementActivityApi.startSubscriprion(subscriptionRequest);
        Logzio.sender(logzio_token, httpResponse.toString());
    }

    public static void stopSubscription() throws Exception {
        String url = "https://manage.office.com/api/v1.0/" + tenant_id + "/activity/feed/";
        SubscriptionRequest subscriptionRequest = new SubscriptionRequest(url, access_token, contentType, publisherId);
        HttpResponse httpResponse = ManagementActivityApi.stopSubscriprion(subscriptionRequest);
        Logzio.sender(logzio_token, httpResponse.toString());
    }

    public static void listCurrentSubscriprions() throws Exception {
        String url = "https://manage.office.com/api/v1.0/" + tenant_id + "/activity/feed/";
        SubscriptionRequest subscriptionRequest = new SubscriptionRequest(url, access_token, null, publisherId);
        HttpResponse httpResponse = ManagementActivityApi.listCurrentSubscriprions(subscriptionRequest);
        Logzio.sender(logzio_token, httpResponse.toString());
    }

    public static void listAvailableContent() throws Exception {
        String url = "https://manage.office.com/api/v1.0/" + tenant_id + "/activity/feed/";

        Date time1 = new SimpleDateFormat("dd/MM/yyyy").parse(startTime);
        Date time2 = new SimpleDateFormat("dd/MM/yyyy").parse(endTime);

        SubscriptionRequest subscriptionRequest = new SubscriptionRequest(url, access_token, contentType, publisherId, time1, time2);
        HttpResponse httpResponse = ManagementActivityApi.listAvailableContent(subscriptionRequest);
        Logzio.sender(logzio_token, httpResponse.toString());
    }

    public static void listNotifications() throws Exception {
        String url = "https://manage.office.com/api/v1.0/" + tenant_id + "/activity/feed/";
        SubscriptionRequest subscriptionRequest = new SubscriptionRequest(url, access_token, contentType, publisherId);
        HttpResponse httpResponse = ManagementActivityApi.stopSubscriprion(subscriptionRequest);
        Logzio.sender(logzio_token, httpResponse.toString());
    }

    public static void retrieveResourceFriendlyNames() throws Exception {
        String url = "https://manage.office.com/api/v1.0/" + tenant_id + "/activity/feed/";
        HttpResponse httpResponse = ManagementActivityApi.retrieveResourceFriendlyNames(publisherId, acceptLanguage, access_token, url);
        Logzio.sender(logzio_token, httpResponse.toString());
    }

    public static void recievingNotifications() throws Exception {
        String path = "https://webhook.myapp.com/o365/ "; //FIXME
        HttpResponse httpResponse = ManagementActivityApi.recievingNotifications(path, contentType, webhookAuthId);
        Logzio.sender(logzio_token, httpResponse.toString());
    }

    public static void retrievingContent() throws Exception {
        String path = "https://manage.office.com/api/v1.0/";
        HttpResponse httpResponse = ManagementActivityApi.retrievingContent(path, contentId, access_token, organizationId);
        Logzio.sender(logzio_token, httpResponse.toString());
    }
}
