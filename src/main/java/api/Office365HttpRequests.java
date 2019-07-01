package api;

import com.microsoft.aad.adal4j.AuthenticationContext;
import com.microsoft.aad.adal4j.AuthenticationResult;
import com.microsoft.aad.adal4j.ClientCredential;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.MalformedURLException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class Office365HttpRequests {
    private static final String MICROSOFTONLINE_ADDRESS = "https://login.microsoftonline.com/";
    private static final Logger logger = LoggerFactory.getLogger(Office365HttpRequests.class.getName());
    public static final String RESOURCE = "https://manage.office.com";
    private String accessToken;

    public Office365HttpRequests(String tenantId, String clientId, String clientSecret) {
        logger.debug("requesting access token...");
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

    public String getAccessToken() {
        return accessToken;
    }

}