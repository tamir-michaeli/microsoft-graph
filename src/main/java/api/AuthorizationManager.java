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

public class AuthorizationManager {

    private static final String MICROSOFTONLINE_ADDRESS = "https://login.microsoftonline.com/";
    private static final String RESOURCE = "https://manage.office.com";
    private static final Logger logger = LoggerFactory.getLogger(AuthorizationManager.class.getName());


    private String accessToken;
    private AuthenticationContext context;
    private long currentTokenExpiry = 0;
    private Future<AuthenticationResult> future;


    public AuthorizationManager(String tenantId, String clientId, String clientSecret) {
        String authority = MICROSOFTONLINE_ADDRESS + tenantId;
        ExecutorService service = Executors.newSingleThreadExecutor();
        logger.debug("creating authorization context");
        try {
            context = new AuthenticationContext(authority, true, service);
        } catch (MalformedURLException e) {
            logger.error("malformed authority url error: {}", e.getMessage(), e);
        }
        future = context.acquireToken(AuthorizationManager.RESOURCE,
                new ClientCredential(clientId, clientSecret), null);
        try {
            accessToken = future.get().getAccessToken();
            System.out.println("got access token exp:" + future.get().getExpiresAfter() + " token: " + accessToken);
            currentTokenExpiry = System.currentTimeMillis() + future.get().getExpiresAfter()*1000;
        } catch (ExecutionException | InterruptedException e) {
            logger.error("Error fetching access token: ", e.getMessage(), e);
        }
    }

    public String getAccessToken() {
        if (System.currentTimeMillis() > currentTokenExpiry - 5 * 60 * 1000) { // 5 minutes safety
            if (!refreshToken()) {
                return null;
            }
        }
        return accessToken;
    }

    private boolean refreshToken() {
        try {
            accessToken = future.get().getRefreshToken();
            System.out.println("got refresh token: " + accessToken);
            currentTokenExpiry = System.currentTimeMillis() + future.get().getExpiresAfter()*1000; // 50 minutes form now (token is valid for 1 hour, 10 minutes safety)
            return true;
        } catch (InterruptedException | ExecutionException e) {
            logger.error("Error refreshing access token: ", e.getMessage(), e);
        }
        return false;
    }
}
