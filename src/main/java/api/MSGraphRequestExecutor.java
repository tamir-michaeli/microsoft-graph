package api;

import objects.AzureADClient;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.naming.AuthenticationException;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

public class MSGraphRequestExecutor {
    private static final Logger logger = LoggerFactory.getLogger(MSGraphRequestExecutor.class.getName());
    public static final String API_ULR = "https://graph.microsoft.com/v1.0/";
    private static final String AUTHORIZATION = "Authorization";
    private static final String BEARER_PREFIX = "Bearer ";
    private static final String CONTENT_TYPE = "Content-Type";
    private static final String APPLICATION_JSON = "application/json";
    private static final String JSON_VALUE = "value";
    private static final String JSON_NEXT_LINK = "@odata.nextLink";
    private static final String ISO_8601 = "yyyy-MM-dd'T'HH:mm:ss'Z'";
    private static final String UTC = "UTC";
    private static final String FILTER_PREFIX = "?$filter=";
    private static final String GREATER_OR_EQUEAL = " ge ";

    private final AuthorizationManager auth;
    private final int interval; // in millis

    public MSGraphRequestExecutor(AzureADClient client) throws AuthenticationException {
        auth = new AuthorizationManager(client);
        this.interval = client.getPullInterval() * 1000;
    }

    private Response executeRequest(String url) throws IOException {
        OkHttpClient client = new OkHttpClient();
        auth.getAccessToken();
        Request request = new Request.Builder()
                .addHeader(AUTHORIZATION, BEARER_PREFIX + auth.getAccessToken())
                .addHeader(CONTENT_TYPE, APPLICATION_JSON)
                .url(url)
                .get()
                .build();
        return client.newCall(request).execute();
    }

    public JSONArray getAllPages(String url) throws IOException, JSONException {
        System.out.println("URL: " + url);
        Response response = executeRequest(url);
        String responseBody = response.body().string();
        JSONObject resultJson = new JSONObject(responseBody);

        JSONArray thisPage = resultJson.getJSONArray(JSON_VALUE);
        System.out.println(thisPage.length() + " RECORDS IN THIS PAGE");
        if (resultJson.has(JSON_NEXT_LINK)) {
            System.out.println("FOUND NEXT PAGE = " + resultJson.getString("@odata.nextLink"));
            JSONArray nextPages = getAllPages(resultJson.getString(JSON_NEXT_LINK));
            for (int i = 0; i < thisPage.length(); i++) {
                nextPages.put(thisPage.get(i));
            }
            return nextPages;
        }
        return thisPage;
    }

    public String timeFilterSuffix(String timeField) {
        DateFormat df = new SimpleDateFormat(ISO_8601);
        df.setTimeZone(TimeZone.getTimeZone(UTC));
        Date fromDate = new Date();
        fromDate.setTime(fromDate.getTime() - interval);
        return FILTER_PREFIX + timeField + GREATER_OR_EQUEAL + df.format(fromDate);
    }


//    public JSONArray sampleRequest(int i, int j) {
//        return new JSONArray();
//    }
//    public boolean startSubscription(String contentType) {
//        Response response = executeOperationApi("start",contentType,false);
//        try {
//            if (response != null) {
//                if (response.body().string().contains("\"status\": \"enabled\"")) {
//                    return true;
//                }
//                logger.error("error while creating a subscription for content type {}, response: {}", contentType, response.body().string());
//            }
//        } catch (IOException e) {
//            logger.error("error parsing response msg for subscription to {}: {}", contentType, e.getMessage(), e);
//        }
//        return false;
//    }
//
//    public boolean stoptSubscription(String contentType) {
//        Response response = executeOperationApi("start",contentType,false);
//        if (response != null) {
//            if (response.isSuccessful()) {
//                return true;
//            }
//            logger.error("error while stopping a subscription for content type {}", contentType, response);
//        }
//        return false;
//    }
//
//    public List<Subscription> listSubscriptions() {
//        ArrayList<Subscription> subs = new ArrayList<>();
//        Response response = executeOperationApi("list", true);
//        if (response != null) {
//            try {
//                parseJsonArray(response.body().string(), json -> {
//                    String contentType = json.getString("contentType");
//                        boolean enabled = json.has("status") && json.getString("status").equals("enabled");
//                        subs.add(new Subscription(contentType, enabled));
//                });
//            } catch (IOException | JSONException e) {
//                logger.error("error parsing response: {}", e.getMessage(), e);
//            }
//        }
//        return subs;
//    }
//

//
//    public void getsh() {
//        System.out.println("ken " + auth.getAccessToken());
//
//    }
//
//    public List<String> listAvailableContent(String contentType, DateTime start, DateTime end) {
//        ArrayList<String> contentUris = new ArrayList<>();
//        DateFormat dateFormat = new SimpleDateFormat("YYYY-MM-DDTHH:MM:SS");
//        String timeRange = String.format("&amp;startTime={}&amp;endTime={}",dateFormat.format(start),dateFormat.format(end));
//        Response response = executeOperationApi("content",contentType,false,timeRange);
//        if (response != null) {
//            try {
//                parseJsonArray(response.body().string(), json ->
//                        contentUris.add(json.getString("contentUri")));
//            } catch (IOException | JSONException e) {
//                logger.error("error parsing response: {}", e.getMessage(), e);
//            }
//        }
//        return contentUris;
//    }
//
//    public JSONArray getContent(String uri) {
//        OkHttpClient client = new OkHttpClient();
//        Request request = new Request.Builder()
//            .get()
//            .url(uri).build();
//        try {
//            Response response = client.newCall(request).execute();
//        } catch (IOException e) {
//            logger.error("error calling get content api: {}", e.getMessage(), e);
//        }
//        return null;
//    }

//    private void parseJsonArray(String stringArray, JsonOps ops) throws JSONException {
//        JSONArray array = (JSONArray) JSONParser.parseJSON(stringArray);
//        for (int i = 0; i < array.length() ; i++) {
//            ops.arrayOp(array.getJSONObject(i));
//        }
//    }

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