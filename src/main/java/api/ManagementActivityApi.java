package api;

import io.logz.sender.com.google.gson.Gson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import requests.SubscriptionRequest;
import responses.AvailableContent;
import responses.Notification;
import responses.ResourceFriendlyNames;
import responses.RetrievingContent;
import responses.Subscription;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class ManagementActivityApi {
    private static final Logger logger = LoggerFactory.getLogger(ManagementActivityApi.class.getName());
    private static final Gson GSON = new Gson();

    public static Subscription startSubscription(SubscriptionRequest subscriptionRequest) throws Exception {
        String path = subscriptionRequest.getPath() + "/subscriptions/start?contentType=" + subscriptionRequest.getContentType();
        URL url = new URL(path);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("POST");
        conn.setRequestProperty("Content-Type", "application/json; charset=utf-8");
        conn.setRequestProperty("Authorization", subscriptionRequest.getToken());
        conn.setRequestProperty("Content-Length", String.valueOf(1000));
        conn.setReadTimeout(15000);
        conn.setConnectTimeout(15000);
        conn.setDoInput(true);
        conn.setDoOutput(true);
        Subscription subscription = GSON.fromJson(conn.getResponseMessage(), Subscription.class);
        logger.info("start subscription request: response code= " + conn.getResponseCode() + " response body= " + conn.getResponseMessage());
        return subscription;
    }

    public static void stopSubscription(SubscriptionRequest subscriptionRequest) throws Exception {
        String path = subscriptionRequest.getPath() + "/subscriptions/stop?contentType=" + subscriptionRequest.getContentType() + "&PublisherIdentifier=" + subscriptionRequest.getPublisherIdentifier();
        URL url = new URL(path);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("POST");
        conn.setRequestProperty("Content-Type", "application/json");
        conn.setRequestProperty("Authorization", subscriptionRequest.getToken());
        conn.setRequestProperty("Content-Length", String.valueOf(1000));
        logger.info("stop subscription request: response code= " + conn.getResponseCode() + " response body= " + conn.getResponseMessage());
    }

    public static ArrayList<Subscription> listCurrentSubscriprions(SubscriptionRequest subscriptionRequest) throws Exception {
        String path = subscriptionRequest.getPath() + "/subscriptions/list?PublisherIdentifier=" + subscriptionRequest.getPublisherIdentifier();
        URL url = new URL(path);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.setRequestProperty("Authorization", subscriptionRequest.getToken());
        conn.setRequestProperty("Content-Length", "1000");
        ArrayList<Subscription> subscriptions = GSON.fromJson(conn.getResponseMessage(), ArrayList.class);
        logger.info("list Current subscriptions request: response code= " + conn.getResponseCode() + " response body= " + conn.getResponseMessage());
        return subscriptions;
    }

    public static ArrayList<AvailableContent> listAvailableContent(SubscriptionRequest subscriptionRequest) throws Exception {
        String path = subscriptionRequest.getPath() + "/subscriptions/content?contentType=" + subscriptionRequest.getContentType()
                + "&amp;startTime=" + subscriptionRequest.getStartTime() + "&amp;endTime=" + subscriptionRequest.getEndTime();
        URL url = new URL(path);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.setRequestProperty("Authorization", subscriptionRequest.getToken());
        conn.setRequestProperty("Content-Length", String.valueOf(1000));
        ArrayList<AvailableContent> availableContents = GSON.fromJson(conn.getResponseMessage(), ArrayList.class);
        logger.info("list available content request: response code= " + conn.getResponseCode() + " response body= " + conn.getResponseMessage());
        return availableContents;
    }

    public static RetrievingContent retrievingContent(String path, String contentId, String token, String OrganizationId) throws Exception {
        URL url = new URL(path + OrganizationId + "/activity/feed/audit/" + contentId);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.setRequestProperty("Authorization", token);
        conn.setRequestProperty("Content-Length", String.valueOf(1000));
        RetrievingContent retrievingContent = GSON.fromJson(conn.getResponseMessage(), RetrievingContent.class);
        logger.info("retrieving content request: response code= " + conn.getResponseCode() + " response body= " + conn.getResponseMessage());
        return retrievingContent;
    }

    public static ArrayList<Notification> listNotifications(SubscriptionRequest subscriptionRequest) throws Exception {
        String path = subscriptionRequest.getPath() + "/subscriptions/notifications?contentType=" +
                subscriptionRequest.getContentType() + "&PublisherIdentifier=" + subscriptionRequest.getPublisherIdentifier();
        URL url = new URL(path);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.setRequestProperty("Authorization", subscriptionRequest.getToken());
        conn.setRequestProperty("Content-Length", String.valueOf(1000));
        ArrayList<Notification> notifications = GSON.fromJson(conn.getResponseMessage(), ArrayList.class);
        logger.info("list notifications request: response code= " + conn.getResponseCode() + " response body= " + conn.getResponseMessage());
        return notifications;
    }

    public static ArrayList<ResourceFriendlyNames> retrieveResourceFriendlyNames(String publisherId, String acceptLanguage, String token, String base_path) throws Exception {
        String path = base_path + "/resources/dlpSensitiveTypes?PublisherIdentifier=" + publisherId;
        URL url = new URL(path);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.setRequestProperty("Accept-Language", acceptLanguage);
        conn.setRequestProperty("Authorization", token);
        conn.setRequestProperty("Content-Length", String.valueOf(1000));
        ArrayList<ResourceFriendlyNames> names = GSON.fromJson(conn.getResponseMessage(), ArrayList.class);
        logger.info("retrieve resource friendly names request: response code= " + conn.getResponseCode() + " response body= " + conn.getResponseMessage());
        return names;
    }

    public static ArrayList<Notification> receivingNotifications(String path, String contentType, String webhookAuthId) throws Exception {
        URL url = new URL(path);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("POST");
        conn.setRequestProperty("Content-Type", "application/json");
        conn.setRequestProperty("Webhook-AuthID", webhookAuthId);
        conn.setRequestProperty("Content-Length", String.valueOf(1000));
        ArrayList<Notification> notifications = GSON.fromJson(conn.getResponseMessage(), ArrayList.class);
        logger.info("receiving notifications request: response code= " + conn.getResponseCode() + " response body= " + conn.getResponseMessage());
        return notifications;
    }
}