import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class ManagementActivityApi {
    private final String base_path;

    //FIXME should understand what is that means "Authorization" and where can we find it..

    public ManagementActivityApi(String tenant_id) {
        this.base_path = "https://manage.office.com/api/v1.0/" + tenant_id + "/activity/feed/";
    }

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
    public int startSubscriprion(SubscriptionRequest subscriptionRequest) {
        String path = base_path + "/subscriptions/start?contentType=" + "Audit.General";// + "&PublisherIdentifier=46b472a7-c68e-4adf-8ade-3db49497518e" + subscriptionRequest.getPublisherIdentifier();

        System.out.println("url= " + path);

        //post method
        URL obj = null;
        try {
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
            return responseCode;
        } catch (MalformedURLException e) {
        } catch (IOException e) {
        } catch (Exception e) {
        }
        return 0;
    }

    /**
     * this method is a post method that stops subscription
     * sending post request without body/ with empty body
     * the response should be 200 if the post succeed our any http response code otherwise
     *
     * @param subscriptionRequest
     */
    public int stopSubscriprion(SubscriptionRequest subscriptionRequest) {
        String path = base_path + "/subscriptions/stop?contentType=" + subscriptionRequest.getContentType() + "&PublisherIdentifier=" + subscriptionRequest.getPublisherIdentifier();
        //post method
        URL obj = null;
        try {
            obj = new URL(path);
            HttpURLConnection conn = (HttpURLConnection) obj.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setRequestProperty("Authorization", subscriptionRequest.getToken());
            conn.setRequestProperty("Content-Length", String.valueOf(1000));
            System.out.println(conn.getResponseMessage());
            return conn.getResponseCode();
        } catch (MalformedURLException e) {
        } catch (IOException e) {
        } catch (Exception e) {
        }
        return 0;
    }

    /**
     * this method is to get the list of current subscribers
     * needs only the publisher identifier
     * <p>
     * response examlpe:
     * HTTP/1.1 200 OK
     * Content-Type: application/json; charset=utf-8
     * <p>
     * [
     * {
     * "contentType" : "Audit.SharePoint",
     * "status": "enabled",
     * "webhook": {
     * "status": "enabled",
     * "address": "https://webhook.myapp.com/o365/",
     * "authId": "o365activityapinotification",
     * "expiration": null
     * }
     * },
     * <p>
     * ...
     * <p>
     * {
     * "contentType": "Audit.Exchange",
     * "webhook": null
     * }
     * ]
     * <p>
     * this method should return a Json array
     *
     * @param subscriptionRequest
     */
    public int listCurrentSubscriprions(SubscriptionRequest subscriptionRequest) {
        String path = base_path + "/subscriptions/list?PublisherIdentifier=" + subscriptionRequest.getPublisherIdentifier();
        //get method
        URL obj = null;
        try {
            obj = new URL(path);
            HttpURLConnection conn = (HttpURLConnection) obj.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Authorization", subscriptionRequest.getToken());
            System.out.println(conn.getResponseMessage());
            return conn.getResponseCode();
        } catch (MalformedURLException e) {
        } catch (IOException e) {
        } catch (Exception e) {
        }
        return 0;
    }

    /**
     * This operation lists the content currently available for retrieval for the specified content type.
     *
     * @param subscriptionRequest
     */

    public int listAvailableContent(SubscriptionRequest subscriptionRequest) {
        String path = base_path + "/subscriptions/content?contentType=" + subscriptionRequest.getContentType() + "&amp;startTime=" + subscriptionRequest.getStartTime() + "&amp;endTime=" + subscriptionRequest.getEndTime();
        //get method
        URL obj = null;
        try {
            obj = new URL(path);
            HttpURLConnection conn = (HttpURLConnection) obj.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Authorization", subscriptionRequest.getToken());
            conn.setRequestProperty("Content-Length", String.valueOf(1000));
            System.out.println(conn.getResponseMessage());
            return conn.getResponseCode();
        } catch (MalformedURLException e) {
        } catch (IOException e) {
        } catch (Exception e) {
        }
        return 0;
    }

    /**
     * Notifications are sent to the configured webhook for a subscription as new content becomes available.
     */
    public int recievingNotifications(ContentType contentType, String webhookAuthId, String request) {
        String path = "https://webhook.myapp.com/o365/ ";
        //post method
        URL obj = null;
        try {
            obj = new URL(path);
            HttpURLConnection conn = (HttpURLConnection) obj.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setRequestProperty("Webhook-AuthID", webhookAuthId);
            conn.setRequestProperty("body", request);
            conn.setRequestProperty("Content-Length", String.valueOf(1000));
            System.out.println(conn.getResponseMessage());
            return conn.getResponseCode();
        } catch (MalformedURLException e) {
        } catch (IOException e) {
        } catch (Exception e) {
        }
        return 0;
    }

    public int retrievingContent(String contentId, String token) {
        String path = "https://manage.office.com/api/v1.0/idohlogz.onmicrosoft.com/activity/feed/audit/CONTENTID";//base_path + "/activity/feed/audit/" + contentId;
        //get method
        URL obj = null;
        try {
            obj = new URL(path);
            HttpURLConnection conn = (HttpURLConnection) obj.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Authorization", token);
            conn.setRequestProperty("Content-Length", String.valueOf(1000));
            System.out.println(conn.getResponseMessage());
            return conn.getResponseCode();
        } catch (MalformedURLException e) {
        } catch (IOException e) {
        } catch (Exception e) {
        }
        return 0;
    }

    /**
     * should return a list of notifications
     *
     * @param subscriptionRequest
     */
    public int listNotifications(SubscriptionRequest subscriptionRequest) {
        String path = base_path + "/subscriptions/notifications?contentType=" + subscriptionRequest.getContentType() + "&PublisherIdentifier=" + subscriptionRequest.getPublisherIdentifier();
        //get method
        URL obj = null;
        try {
            obj = new URL(path);
            HttpURLConnection conn = (HttpURLConnection) obj.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Authorization", subscriptionRequest.getToken());
            conn.setRequestProperty("Content-Length", String.valueOf(1000));
            System.out.println(conn.getResponseMessage());
            return conn.getResponseCode();
        } catch (MalformedURLException e) {
        } catch (IOException e) {
        } catch (Exception e) {
        }
        return 0;
    }

    /**
     * accept_language will be sent as a property
     *
     * @param publisherId
     * @param acceptLanguage
     */
    public int retrieveResourceFriendlyNames(String publisherId, String acceptLanguage, String token) {
        String path = base_path + "/resources/dlpSensitiveTypes?PublisherIdentifier=" + publisherId;
        //get method
        URL obj = null;
        try {
            obj = new URL(path);
            HttpURLConnection conn = (HttpURLConnection) obj.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Accept-Language", acceptLanguage);
            conn.setRequestProperty("Authorization", token);
            conn.setRequestProperty("Content-Length", String.valueOf(1000));
            System.out.println(conn.getResponseMessage());
            return conn.getResponseCode();
        } catch (MalformedURLException e) {
        } catch (IOException e) {
        } catch (Exception e) {
        }
        return 0;
    }
}
