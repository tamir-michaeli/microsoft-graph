import org.mockserver.model.HttpResponse;

import java.io.BufferedWriter;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

public class ManagementActivityApi {
    /**
     * base_path = "https://manage.office.com/api/v1.0/" + tenant_id + "/activity/feed/";
     * */
    /* **************************************** */

    /**
     * post method to start subscription.
     * JSon example: {
     * "webhook" : {
     * "address": "https://webhook.myapp.com/o365/",
     * "authId": "o365activityapinotification",
     * "expiration": ""
     * }
     * }
     * <p>
     * Response example:
     * HTTP/1.1 200 OK
     * Content-Type: application/json; charset=utf-8
     * <p>
     * {
     * "contentType": "Audit.SharePoint",
     * "status": "enabled",
     * "webhook": {
     * "status": "enabled",
     * "address":  "https://webhook.myapp.com/o365/",
     * "authId": "o365activityapinotification",
     * "expiration": null
     * }
     * }
     *
     * @param subscriptionRequest
     */
    public static HttpResponse startSubscriprion(SubscriptionRequest subscriptionRequest) throws Exception {
        String path = subscriptionRequest.getPath() + "/subscriptions/start?contentType=" + subscriptionRequest.getContentType().getValue();// + "&PublisherIdentifier=46b472a7-c68e-4adf-8ade-3db49497518e" + subscriptionRequest.getPublisherIdentifier();

        URL obj = null;
        obj = new URL(path);
        HttpURLConnection conn = (HttpURLConnection) obj.openConnection();
        conn.setRequestMethod("POST");
        conn.setRequestProperty("Content-Type", "application/json; charset=utf-8");
        conn.setRequestProperty("Authorization", subscriptionRequest.getToken());
        conn.setReadTimeout(15000);
        conn.setConnectTimeout(15000);
        conn.setDoInput(true);
        conn.setDoOutput(true);
        OutputStream os = conn.getOutputStream();
        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));
        writer.write(subscriptionRequest.getRequestBody());

        writer.flush();
        writer.close();
        os.close();
        int responseCode = conn.getResponseCode();
        System.out.println(conn.getResponseMessage());
        HttpResponse httpResponse = HttpResponse.response(conn.getResponseMessage());
        return httpResponse;
    }

    public static HttpResponse stopSubscriprion(SubscriptionRequest subscriptionRequest) throws Exception {
        String path = subscriptionRequest.getPath() + "/subscriptions/stop?contentType=" + subscriptionRequest.getContentType().getValue() + "&PublisherIdentifier=" + subscriptionRequest.getPublisherIdentifier();
        //post method
        URL obj = null;

        obj = new URL(path);
        HttpURLConnection conn = (HttpURLConnection) obj.openConnection();
        conn.setRequestMethod("POST");
        conn.setRequestProperty("Content-Type", "application/json");
        conn.setRequestProperty("Authorization", subscriptionRequest.getToken());
        conn.setRequestProperty("Content-Length", String.valueOf(1000));
        System.out.println(conn.getResponseMessage());
        HttpResponse httpResponse = HttpResponse.response(conn.getResponseMessage());
        return httpResponse;

    }

    public static HttpResponse listCurrentSubscriprions(SubscriptionRequest subscriptionRequest) throws Exception {
        String path = subscriptionRequest.getPath() + "/subscriptions/list?PublisherIdentifier=" + subscriptionRequest.getPublisherIdentifier();
        //get method
        URL obj = null;

        obj = new URL(path);
        HttpURLConnection conn = (HttpURLConnection) obj.openConnection();
        conn.setRequestMethod("GET");
        conn.setRequestProperty("Authorization", subscriptionRequest.getToken());
        System.out.println(conn.getResponseMessage());
        HttpResponse httpResponse = HttpResponse.response(conn.getResponseMessage());
        return httpResponse;
    }


    public static HttpResponse listAvailableContent(SubscriptionRequest subscriptionRequest) throws Exception {
        String path = subscriptionRequest.getPath() + "/subscriptions/content?contentType=" + subscriptionRequest.getContentType()
                + "&amp;startTime=" + subscriptionRequest.getStartTime() + "&amp;endTime=" + subscriptionRequest.getEndTime();
        //get method
        URL obj = null;

        obj = new URL(path);
        HttpURLConnection conn = (HttpURLConnection) obj.openConnection();
        conn.setRequestMethod("GET");
        conn.setRequestProperty("Authorization", subscriptionRequest.getToken());
        conn.setRequestProperty("Content-Length", String.valueOf(1000));
        System.out.println(conn.getResponseMessage());
        HttpResponse httpResponse = HttpResponse.response(conn.getResponseMessage());
        return httpResponse;
    }

    /**
     * String path = "https://webhook.myapp.com/o365/ ";
     */
    public static HttpResponse recievingNotifications(String path, ContentType contentType, String webhookAuthId, String request) throws Exception {
        //post method
        URL obj = null;

        obj = new URL(path);
        HttpURLConnection conn = (HttpURLConnection) obj.openConnection();
        conn.setRequestMethod("POST");
        conn.setRequestProperty("Content-Type", "application/json");
        conn.setRequestProperty("Webhook-AuthID", webhookAuthId);
        conn.setRequestProperty("body", request);
        conn.setRequestProperty("Content-Length", String.valueOf(1000));
        System.out.println(conn.getResponseMessage());
        HttpResponse httpResponse = HttpResponse.response(conn.getResponseMessage());
        return httpResponse;

    }

    /**
     * String path = "https://manage.office.com/api/v1.0/idohlogz.onmicrosoft.com/activity/feed/audit/CONTENTID";//base_path + "/activity/feed/audit/" + contentId;
     */
    public static HttpResponse retrievingContent(String path, String contentId, String token) throws Exception {
        //get method
        URL obj = null;
        obj = new URL(path);
        HttpURLConnection conn = (HttpURLConnection) obj.openConnection();
        conn.setRequestMethod("GET");
        conn.setRequestProperty("Authorization", token);
        conn.setRequestProperty("Content-Length", String.valueOf(1000));
        System.out.println(conn.getResponseMessage());
        HttpResponse httpResponse = HttpResponse.response(conn.getResponseMessage());
        return httpResponse;

    }

    public static HttpResponse listNotifications(SubscriptionRequest subscriptionRequest) throws Exception {
        String path = subscriptionRequest.getPath() + "/subscriptions/notifications?contentType=" +
                subscriptionRequest.getContentType() + "&PublisherIdentifier=" + subscriptionRequest.getPublisherIdentifier();
        URL obj = null;
        obj = new URL(path);
        HttpURLConnection conn = (HttpURLConnection) obj.openConnection();
        conn.setRequestMethod("GET");
        conn.setRequestProperty("Authorization", subscriptionRequest.getToken());
        conn.setRequestProperty("Content-Length", String.valueOf(1000));
        System.out.println(conn.getResponseMessage());
        HttpResponse httpResponse = HttpResponse.response(conn.getResponseMessage());
        return httpResponse;

    }

    public static HttpResponse retrieveResourceFriendlyNames(String publisherId, String acceptLanguage, String token, String base_path) throws Exception {
        String path = base_path + "/resources/dlpSensitiveTypes?PublisherIdentifier=" + publisherId;
        //get method
        URL obj = null;

        obj = new URL(path);
        HttpURLConnection conn = (HttpURLConnection) obj.openConnection();
        conn.setRequestMethod("GET");
        conn.setRequestProperty("Accept-Language", acceptLanguage);
        conn.setRequestProperty("Authorization", token);
        conn.setRequestProperty("Content-Length", String.valueOf(1000));
        System.out.println(conn.getResponseMessage());
        HttpResponse httpResponse = HttpResponse.response(conn.getResponseMessage());
        return httpResponse;

    }
}
