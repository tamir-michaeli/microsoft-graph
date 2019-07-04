package api;

import net.sf.json.JSONArray;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import org.json.JSONException;
import org.json.JSONObject;
import org.skyscreamer.jsonassert.JSONParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import responses.Subscription;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Office365HttpRequests {
    private static final Logger logger = LoggerFactory.getLogger(Office365HttpRequests.class.getName());
    public static final String MICROSOFTONLINE_ADDRESS = "https://login.microsoftonline.com/";
    private static final String API_V1 = "api/v1.0/";
    private static final String FEED_SUBSCRIPTIONS = "/activity/feed/subscriptions/";

    private String tenantId;
    private AuthorizationManager auth;

    public Office365HttpRequests(String tenantId, String clientId, String clientSecret) {

        auth = new AuthorizationManager(tenantId, clientId, clientSecret);
        logger.debug("requesting access token...");
        this.tenantId = tenantId;
    }

    private Response executeOperationApi(String operation, boolean isGet) {
        return executeOperationApi(operation,"", isGet);
    }

    private Response executeOperationApi(String operation, String contentType, boolean isGet) {
        OkHttpClient client = new OkHttpClient();
        String url = MICROSOFTONLINE_ADDRESS + API_V1 + tenantId + FEED_SUBSCRIPTIONS + operation
                + (contentType.isEmpty() ? "" : ("?contentType=" + contentType)) ;
        auth.getAccessToken();
        RequestBody body = RequestBody.create(null, new byte[0]); //empty body
        Request.Builder requestBuilder = new Request.Builder();
        requestBuilder.addHeader("Authorization", "Bearer " + auth.getAccessToken());
        requestBuilder.addHeader("Content-Type","application/x-www-form-urlencoded");
        if (isGet) {
            requestBuilder = requestBuilder.get();
        } else {
            requestBuilder = requestBuilder.post(body);
        }
        Request request = requestBuilder.url(url).build();
        try {
            return client.newCall(request).execute();
        } catch (IOException e) {
            logger.error("error calling api operation {}: {}", operation, e.getMessage(), e);
        }
        return null;
    }

    public boolean startSubscription(String contentType) {
        Response response = executeOperationApi("start",contentType,false);
        try {
            if (response != null) {
                if (response.body().string().contains("\"status\": \"enabled\"")) {
                    return true;
                }
                logger.error("error while creating a subscription for content type {}, response: {}", contentType, response.body().string());
            }
        } catch (IOException e) {
            logger.error("error parsing response msg for subscription to {}: {}", contentType, e.getMessage(), e);
        }
        return false;
    }

    public boolean stoptSubscription(String contentType) {
        Response response = executeOperationApi("start",contentType,false);
        if (response != null) {
            if (response.isSuccessful()) {
                return true;
            }
            logger.error("error while stopping a subscription for content type {}", contentType, response);
        }
        return false;
    }

    public List<Subscription> listSubscriptions() {
        ArrayList<Subscription> subs = new ArrayList<>();
        Response response = executeOperationApi("list", true);
        if (response != null) {
            try {
                parseJsonArray(response.body().string(), json -> {
                    String contentType = json.getString("contentType");
                        boolean enabled = json.has("status") && json.getString("status").equals("enabled");
                        subs.add(new Subscription(contentType, enabled));
                });
            } catch (IOException | JSONException e) {
                logger.error("error parsing response msg: {}", e.getMessage(), e);
            }
        }
        return subs;
    }

    public List<String> listAvailableContent(String contentType) {
        ArrayList<String> contentUris = new ArrayList<>();
        Response response = executeOperationApi("content",contentType,false);
        if (response != null) {
            try {
                parseJsonArray(response.body().string(), json -> {
                        contentUris.add(json.getString("contentUri"));
                });
            } catch (IOException | JSONException e) {
                logger.error("error parsing response msg: {}", e.getMessage(), e);
            }
        }
        return contentUris;
    }

    private void parseJsonArray(String stringArray, JsonOps ops) throws JSONException {
        JSONArray array = (JSONArray) JSONParser.parseJSON(stringArray);
        for (Object json : array) {
            ops.arrayOp((JSONObject) json);
        }
    }

//   ***Content Types***
//    Audit.AzureActiveDirectory
//
//    Audit.Exchange
//
//    Audit.SharePoint
//
//      Audit.General (includes all other workloads not included in the previous content types)
//
//     DLP.All (DLP events only for all workloads)Audit.AzureActiveDirectory
//


}