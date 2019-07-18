package api;

import objects.AzureADClient;
import org.apache.http.HttpException;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.naming.AuthenticationException;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

public class AuthorizationManager {

    private static final Logger logger = LoggerFactory.getLogger(AuthorizationManager.class.getName());

    private static final String MICROSOFTONLINE_ADDRESS = "https://login.microsoftonline.com/";
    private static final String MICROSOFT_GRAPH = "https://graph.microsoft.com/.default";
    private static final String OAUTH2_TOKEN_API = "/oauth2/v2.0/token";
    private static final String CLIENT_CREDENTIALS = "client_credentials";
    private static final String REQUEST_CONTENT_TYPE = "Content-Type";
    private static final String APPLICATION_FORM_URLENCODED = "application/x-www-form-urlencoded";
    private static final String REQUEST_CONTENT_LENGTH = "Content-Length";
    private static final String JSON_ACCESS_TOKEN = "access_token";
    private static final String JSON_ACCESS_TOKEN_EXPIRE_DURATION = "expires_in";
    private static final int FIVE_MINUTES = 5 * 60 * 1000;
    private static final String GRANT_TYPE = "grant_type=";
    private static final String CLIENT_ID = "&client_id=";
    private static final String SCOPE = "&scope=";
    private static final String CLIENT_SECRET = "&client_secret=";

    private final String clientId;
    private final String clientSecret;
    private final String tenantId;
    private String accessToken;
    private long currentTokenExpiry = 0;


    public AuthorizationManager(AzureADClient client) throws AuthenticationException {
        logger.info("Initializing authorization manager");
        this.clientId = client.getClientId();
        this.clientSecret = client.getClientSecret();
        this.tenantId = client.getTenantId();
        if (!retrieveToken()) {
            throw new AuthenticationException("can't get access token, quiting..");
        }
    }

    public String getAccessToken() {
        if (System.currentTimeMillis() > currentTokenExpiry - FIVE_MINUTES) { // 5 minutes safety
            if (!retrieveToken()) {
                return null;
            }
        }
        return accessToken;
    }

    private boolean retrieveToken() {

        String url = MICROSOFTONLINE_ADDRESS + tenantId + OAUTH2_TOKEN_API;

        try {
            String urlParameters = GRANT_TYPE + CLIENT_CREDENTIALS
                    + CLIENT_ID + clientId
                    + SCOPE + MICROSOFT_GRAPH
                    + CLIENT_SECRET + URLEncoder.encode(clientSecret, StandardCharsets.UTF_8.toString());
            byte[] postData = urlParameters.getBytes(StandardCharsets.UTF_8);
            int postDataLength = postData.length;

            URL obj = new URL(url);
            HttpURLConnection con = (HttpURLConnection) obj.openConnection();

            con.setDoOutput(true);
            con.setRequestProperty(REQUEST_CONTENT_TYPE, APPLICATION_FORM_URLENCODED);
            con.setRequestProperty(REQUEST_CONTENT_LENGTH, Integer.toString(postDataLength));

            try (DataOutputStream wr = new DataOutputStream(con.getOutputStream())) {
                wr.write(postData);
            }

            BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
            String inputLine;
            StringBuilder response = new StringBuilder();

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();
            if (con.getResponseCode() == HttpURLConnection.HTTP_OK) {
                JSONObject jsonResponse = new JSONObject(response.toString());
                accessToken = jsonResponse.get(JSON_ACCESS_TOKEN).toString();
                currentTokenExpiry = System.currentTimeMillis() + jsonResponse.getInt(JSON_ACCESS_TOKEN_EXPIRE_DURATION) * 1000;
                return true;
            } else {
                throw new HttpException("Invalid response code while fetching access token: " + con.getResponseCode() + "\n response: " + response);
            }

        } catch (IOException | JSONException | HttpException e) {
            logger.error("Error fetching access token: {}", e.getMessage(), e);
        }
        return false;
    }
}
