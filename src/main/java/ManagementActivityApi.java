import org.mockserver.model.HttpResponse;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.logging.Logger;

public class ManagementActivityApi {
    private static final Logger LOGGER = Logger.getLogger(ManagementActivityApi.class.getName());

    public static HttpResponse startSubscription(SubscriptionRequest subscriptionRequest) throws Exception {
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
        HttpResponse httpResponse = HttpResponse.response(conn.getResponseMessage());
        LOGGER.info("start subscription request: response code= " + httpResponse.getStatusCode() + " response body= " + httpResponse.getBodyAsString());
        return httpResponse;
    }

    public static HttpResponse stopSubscription(SubscriptionRequest subscriptionRequest) throws Exception {
        String path = subscriptionRequest.getPath() + "/subscriptions/stop?contentType=" + subscriptionRequest.getContentType() + "&PublisherIdentifier=" + subscriptionRequest.getPublisherIdentifier();
        URL url = new URL(path);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("POST");
        conn.setRequestProperty("Content-Type", "application/json");
        conn.setRequestProperty("Authorization", subscriptionRequest.getToken());
        conn.setRequestProperty("Content-Length", String.valueOf(1000));
        HttpResponse httpResponse = HttpResponse.response(conn.getResponseMessage());
        LOGGER.info("stop subscription request: response code= " + httpResponse.getStatusCode() + " response body= " + httpResponse.getBodyAsString());
        return httpResponse;
    }

    public static HttpResponse listCurrentSubscriprions(SubscriptionRequest subscriptionRequest) throws Exception {
        String path = subscriptionRequest.getPath() + "/subscriptions/list?PublisherIdentifier=" + subscriptionRequest.getPublisherIdentifier();
        URL url = new URL(path);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.setRequestProperty("Authorization", subscriptionRequest.getToken());
        HttpResponse httpResponse = HttpResponse.response(conn.getResponseMessage());
        LOGGER.info("list Current subscriptions request: response code= " + httpResponse.getStatusCode() + " response body= " + httpResponse.getBodyAsString());
        return httpResponse;
    }


    public static HttpResponse listAvailableContent(SubscriptionRequest subscriptionRequest) throws Exception {
        String path = subscriptionRequest.getPath() + "/subscriptions/content?contentType=" + subscriptionRequest.getContentType()
                + "&amp;startTime=" + subscriptionRequest.getStartTime() + "&amp;endTime=" + subscriptionRequest.getEndTime();
        URL url = new URL(path);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.setRequestProperty("Authorization", subscriptionRequest.getToken());
        conn.setRequestProperty("Content-Length", String.valueOf(1000));
        HttpResponse httpResponse = HttpResponse.response(conn.getResponseMessage());
        LOGGER.info("list available content request: response code= " + httpResponse.getStatusCode() + " response body= " + httpResponse.getBodyAsString());
        return httpResponse;
    }


    public static HttpResponse retrievingContent(String path, String contentId, String token, String OrganizationId) throws Exception {
        URL url = new URL(path + OrganizationId + "/activity/feed/audit/" + contentId);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.setRequestProperty("Authorization", token);
        conn.setRequestProperty("Content-Length", String.valueOf(1000));
        HttpResponse httpResponse = HttpResponse.response(conn.getResponseMessage());
        LOGGER.info("retrieve content request: response code= " + httpResponse.getStatusCode() + " response body= " + httpResponse.getBodyAsString());
        return httpResponse;
    }

    public static HttpResponse listNotifications(SubscriptionRequest subscriptionRequest) throws Exception {
        String path = subscriptionRequest.getPath() + "/subscriptions/notifications?contentType=" +
                subscriptionRequest.getContentType() + "&PublisherIdentifier=" + subscriptionRequest.getPublisherIdentifier();
        URL url = new URL(path);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.setRequestProperty("Authorization", subscriptionRequest.getToken());
        conn.setRequestProperty("Content-Length", String.valueOf(1000));
        HttpResponse httpResponse = HttpResponse.response(conn.getResponseMessage());
        LOGGER.info("list notifications request: response code= " + httpResponse.getStatusCode() + " response body= " + httpResponse.getBodyAsString());
        return httpResponse;
    }

    public static HttpResponse retrieveResourceFriendlyNames(String publisherId, String acceptLanguage, String token, String base_path) throws Exception {
        String path = base_path + "/resources/dlpSensitiveTypes?PublisherIdentifier=" + publisherId;
        URL url = new URL(path);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.setRequestProperty("Accept-Language", acceptLanguage);
        conn.setRequestProperty("Authorization", token);
        conn.setRequestProperty("Content-Length", String.valueOf(1000));
        HttpResponse httpResponse = HttpResponse.response(conn.getResponseMessage());
        LOGGER.info("retrieve resource friendly names request: response code= " + httpResponse.getStatusCode() + " response body= " + httpResponse.getBodyAsString());
        return httpResponse;
    }

    public static HttpResponse receivingNotifications(String path, String contentType, String webhookAuthId) throws Exception {
        URL url = new URL(path);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("POST");
        conn.setRequestProperty("Content-Type", "application/json");
        conn.setRequestProperty("Webhook-AuthID", webhookAuthId);
        conn.setRequestProperty("Content-Length", String.valueOf(1000));
        HttpResponse httpResponse = HttpResponse.response(conn.getResponseMessage());
        LOGGER.info("recieving notifications request: response code= " + httpResponse.getStatusCode() + " response body= " + httpResponse.getBodyAsString());
        return httpResponse;
    }
}
