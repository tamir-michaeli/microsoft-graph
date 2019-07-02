package api;

import io.logz.sender.com.google.gson.Gson;
import io.logz.sender.com.google.gson.JsonObject;
import main.Main;
import operations.Office365Client;
import org.mockserver.model.HttpResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.yaml.snakeyaml.Yaml;
import requests.SubscriptionRequest;
import responses.AvailableContent;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;


public class ManagementActivityApiWrapper {
    private static final Logger logger = LoggerFactory.getLogger(ManagementActivityApiWrapper.class.getName());
    private static final String HTTPS_PREFIX = "https://";
    private static final String PORT_SUFFIX = ":8071/";

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
    private final String startTime;
    private String acceptLanguage;

    //FIXME the below parameters aren't shared between the other endpoints, how to get the input?
    private String organizationId;
    private String contentId;


    public ManagementActivityApiWrapper() throws Exception {
        Office365Client officeClient = getConfigYaml();
        logzio_token = officeClient.getLogzioToken();
        logzio_host = HTTPS_PREFIX + officeClient.getLogzioListenerHost() + PORT_SUFFIX;
        tenant_id = officeClient.getTenantId();
        client_id = officeClient.getClientId();
        client_secret = officeClient.getClientSecret();
        publisherId = officeClient.getPublisherId();
        contentType = officeClient.getContentType();
        interval = officeClient.getInterval();
        startTime = officeClient.getStartTime();
        acceptLanguage = officeClient.getAcceptLanguage();

        sender = new LogzioSender(logzio_token, logzio_host);
        access_token = getMicrosoftAccessToken();
    }

    private Office365Client getConfigYaml() throws IOException {
        Yaml yaml = new Yaml();
        try (InputStream in = Main.class
                .getResourceAsStream("/conf.yml")) {
           return yaml.loadAs(in, Office365Client.class);
        }
    }

    private String getMicrosoftAccessToken() throws Exception {
//        Office365HttpRequests connection = new Office365HttpRequests(tenant_id);
//        HttpResponse httpResponse = Office365HttpRequests.getAccessToken(connection, client_id, client_secret);
//        Gson gson = new Gson();
//        JsonObject jsonObject = gson.fromJson(httpResponse.getBody().toString(), JsonObject.class);
//        return jsonObject.get("access_token").getAsString();
        return null;
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
        ManagementActivityApi.listCurrentSubscriprions(subscriptionRequest);
    }

    public void listAvailableContent(Date startTime, Date endTime) {
        String url = "https://manage.office.com/api/v1.0/" + tenant_id + "/activity/feed/";
        SubscriptionRequest subscriptionRequest = new SubscriptionRequest(url, access_token, contentType, publisherId, startTime, endTime);
        ArrayList<AvailableContent> availableContents = null;
        try {
            availableContents = ManagementActivityApi.listAvailableContent(subscriptionRequest);
        } catch (Exception e) {
            logger.error("failed while getting the list of the available content");
        }

        for (AvailableContent availableContent :
                availableContents) {
            String logs = getContentLogs(availableContent.getContentUri());
            String timeStamp = availableContent.getContentCreated();

            String parsedLogs = parseLogs(logs, timeStamp);
            sender.send(parsedLogs);
        }
    }

    private String parseLogs(String logs, String timeStamp) {
        return "{\"@timestamp\":\"" + timeStamp + "\",\n" + "\"body\":\"" + logs + "\"}";
    }

    private String getContentLogs(String path) {
        try {
            URL url = new URL(path);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            return conn.getResponseMessage();
        } catch (Exception e) {
            logger.error("couldn't getAccessToken to the uri");
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

    public void recievingNotifications(String url, String webhookAuthId) throws Exception {
        ManagementActivityApi.receivingNotifications(url, contentType, webhookAuthId);
    }

    public void retrievingContent() throws Exception {
        String path = "https://manage.office.com/api/v1.0/";
        ManagementActivityApi.retrievingContent(path, contentId, access_token, organizationId);
    }

    public int getInterval() {
        return interval;
    }

    public String getStartTime() {
        return startTime;
    }
}