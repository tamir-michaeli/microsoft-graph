package api;

import com.google.gson.Gson;
import com.microsoft.aad.adal4j.AuthenticationContext;
import com.microsoft.aad.adal4j.AuthenticationResult;
import com.microsoft.aad.adal4j.ClientCredential;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import responses.Subscription;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class Office365HttpRequests {
    private static final String MICROSOFTONLINE_ADDRESS = "https://login.microsoftonline.com/";
    private static final Logger logger = LoggerFactory.getLogger(Office365HttpRequests.class.getName());
    private static final String RESOURCE = "https://manage.office.com";
    public static final String API_V1 = "api/v1.0/";
    public static final String FEED_SUBSCRIPTIONS = "/activity/feed/subscriptions/";

    private String tenantId;
    private String accessToken;


    public Office365HttpRequests(String tenantId, String clientId, String clientSecret) {
        logger.debug("requesting access token...");
        this.tenantId = tenantId;
        String authority = MICROSOFTONLINE_ADDRESS + tenantId;
        ExecutorService service = Executors.newFixedThreadPool(1);

        try {
            AuthenticationContext context = new AuthenticationContext(authority, true, service);
            Future<AuthenticationResult> future = context.acquireToken(RESOURCE,
                    new ClientCredential(clientId,clientSecret),null);
            accessToken = future.get().getAccessToken();
        } catch (MalformedURLException | ExecutionException | InterruptedException e) {
           logger.error("Error fetching access token: ", e.getMessage(), e);
        }
    }

    private Response executeApiOperation(String operation, boolean isGet) {
        return executeApiOperation(operation,"", isGet);
    }

    private Response executeApiOperation(String operation, String contentType, boolean isGet) {
        OkHttpClient client = new OkHttpClient();
        String url = MICROSOFTONLINE_ADDRESS + API_V1 + tenantId + FEED_SUBSCRIPTIONS + operation
                + (contentType.isEmpty() ? "" : ("?contentType=" + contentType)) ;

        RequestBody body = RequestBody.create(null, new byte[0]); //empty body
        Request.Builder requestBuilder = new Request.Builder();
        if (isGet) {
            requestBuilder = requestBuilder.get();
        } else {
            requestBuilder = requestBuilder.post(body);
        }
        Request request = requestBuilder.url(url).build();
        try {
            return client.newCall(request).execute();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean startSubscription(String contentType) {
        return executeApiOperation("start",contentType,false).isSuccessful();
    }

    public boolean stoptSubscription(String contentType) {
        return executeApiOperation("stop",contentType, false).isSuccessful();
    }

    public List<Subscription> listSubscription() {
        Response response = executeApiOperation("list", true);
        try {
            return (new Gson()).fromJson(response.body().string(), ArrayList.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new ArrayList<>();
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

    public String getAccessToken() {
        return accessToken;
    }

}