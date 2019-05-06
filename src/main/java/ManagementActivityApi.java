import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class ManagementActivityApi {
    private final String base_path;

    //FIXME should understand what is that means "Authorization" and where can we find it..

    public ManagementActivityApi(String tenant_id, String operation) {
        this.base_path = "https://manage.office.com/api/v1.0/" + tenant_id + "/activity/feed/" + operation;
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
    public String startSubscriprion(SubscriptionRequest subscriptionRequest) {
        String path = base_path + "/subscriptions/start?contentType=" + subscriptionRequest.getContentType() + "&PublisherIdentifier=" + subscriptionRequest.getPublisherIdentifier();
        //post method
        URL obj = null;
        try {
            obj = new URL(path);
            HttpURLConnection conn = (HttpURLConnection) obj.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setRequestProperty("body", subscriptionRequest.getRequestBody().toString());
            return conn.getResponseMessage();
        } catch (MalformedURLException e) {
        } catch (IOException e) {
        } catch (Exception e) {
        }
        return null;
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
    public String listCurrentSubscriprions(SubscriptionRequest subscriptionRequest) {
        String path = base_path + "/subscriptions/list?PublisherIdentifier=" + subscriptionRequest.getPublisherIdentifier();
        //get method
        URL obj = null;
        try {
            obj = new URL(path);
            HttpURLConnection conn = (HttpURLConnection) obj.openConnection();
            conn.setRequestMethod("GET");
            return conn.getResponseMessage();
        } catch (MalformedURLException e) {
        } catch (IOException e) {
        } catch (Exception e) {
        }
        return null;
    }

    /**
     * This operation lists the content currently available for retrieval for the specified content type.
     *
     * @param subscriptionRequest
     */

    public String listAvailableContent(SubscriptionRequest subscriptionRequest) {
        String path = base_path + "/subscriptions/content?contentType=" + subscriptionRequest.getContentType() + "&amp;startTime=" + subscriptionRequest.getStartTime() + "&amp;endTime=" + subscriptionRequest.getEndTime();
        //get method
        URL obj = null;
        try {
            obj = new URL(path);
            HttpURLConnection conn = (HttpURLConnection) obj.openConnection();
            conn.setRequestMethod("GET");
            return conn.getResponseMessage();
        } catch (MalformedURLException e) {
        } catch (IOException e) {
        } catch (Exception e) {
        }
        return null;
    }

    /**
     * Notifications are sent to the configured webhook for a subscription as new content becomes available.
     */
    public String recievingNotifications(ContentType contentType, String webhookAuthId, String request) {
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
            return conn.getResponseMessage();
        } catch (MalformedURLException e) {
        } catch (IOException e) {
        } catch (Exception e) {
        }
        return null;
    }

    public String retrievingContent(String contentId) {
        String path = base_path + "/activity/feed/audit/" + contentId;
        //get method
        URL obj = null;
        try {
            obj = new URL(path);
            HttpURLConnection conn = (HttpURLConnection) obj.openConnection();
            conn.setRequestMethod("GET");
            return conn.getResponseMessage();
        } catch (MalformedURLException e) {
        } catch (IOException e) {
        } catch (Exception e) {
        }
        return null;
    }

    /**
     * should return a list of notifications
     *
     * @param subscriptionRequest
     */
    public String listNotifications(SubscriptionRequest subscriptionRequest) {
        String path = base_path + "/subscriptions/notifications?contentType=" + subscriptionRequest.getContentType() + "&PublisherIdentifier=" + subscriptionRequest.getPublisherIdentifier();
        //get method
        URL obj = null;
        try {
            obj = new URL(path);
            HttpURLConnection conn = (HttpURLConnection) obj.openConnection();
            conn.setRequestMethod("GET");
            return conn.getResponseMessage();
        } catch (MalformedURLException e) {
        } catch (IOException e) {
        } catch (Exception e) {
        }
        return null;
    }

    /**
     * accept_language will be sent as a property
     *
     * @param publisherId
     * @param acceptLanguage
     */
    public String retrieveResourceFriendlyNames(String publisherId, String acceptLanguage) {
        String path = base_path + "/resources/dlpSensitiveTypes?PublisherIdentifier=" + publisherId;
        //get method
        URL obj = null;
        try {
            obj = new URL(path);
            HttpURLConnection conn = (HttpURLConnection) obj.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Accept-Language", "en-US,en;q=0.5");
            return conn.getResponseMessage();
        } catch (MalformedURLException e) {
        } catch (IOException e) {
        } catch (Exception e) {
        }
        return null;
    }
}
