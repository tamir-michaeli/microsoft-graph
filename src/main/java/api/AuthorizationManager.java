package api;

import org.apache.http.HttpException;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

public class AuthorizationManager {

    private static final Logger logger = LoggerFactory.getLogger(AuthorizationManager.class.getName());

    private static final String MICROSOFTONLINE_ADDRESS = "https://login.microsoftonline.com/";
    private static final String SCOPE = "https://graph.microsoft.com/.default";
    private static final String OAUTH2_TOKEN_API = "/oauth2/v2.0/token";
    public static final String CLIENT_CREDENTIALS_GRANT_TYPE = "client_credentials";
    public static final String REQUEST_CONTENT_TYPE = "Content-Type";
    public static final String APPLICATION_FORM_URLENCODED = "application/x-www-form-urlencoded";
    public static final String REQUEST_CONTENT_LENGTH = "Content-Length";
    public static final String JSON_ACCESS_TOKEN = "access_token";
    public static final String JSON_ACCESS_TOKEN_EXPIRE_DURATION = "expires_in";

    private final String clientId;
    private final String clientSecret;
    private final String tenantId;
    private String accessToken;
    private long currentTokenExpiry = 0;


    public AuthorizationManager(String tenantId, String clientId, String clientSecret) {
        this.clientId = clientId;
        this.clientSecret = clientSecret;
        this.tenantId = tenantId;

        retrieveToken();
    }

    public String getAccessToken() {
        if (System.currentTimeMillis() > currentTokenExpiry - 5 * 60 * 1000) { // 5 minutes safety
            if (!retrieveToken()) {
                return null;
            }
        }
        return accessToken;
    }

    private boolean retrieveToken() {

        String url = MICROSOFTONLINE_ADDRESS + tenantId + OAUTH2_TOKEN_API;

        try {
            String urlParameters  = "grant_type=" + CLIENT_CREDENTIALS_GRANT_TYPE
                    + "&client_id=" + clientId
                    + "&scope=" + SCOPE
                    + "&client_secret=" + clientSecret;
            byte[] postData = urlParameters.getBytes(StandardCharsets.UTF_8);
            int postDataLength = postData.length;

            URL obj = new URL(url);
            HttpURLConnection con = (HttpURLConnection) obj.openConnection();

            con.setDoOutput(true);
            con.setRequestProperty(REQUEST_CONTENT_TYPE, APPLICATION_FORM_URLENCODED);
            con.setRequestProperty(REQUEST_CONTENT_LENGTH, Integer.toString(postDataLength));

            try( DataOutputStream wr = new DataOutputStream(con.getOutputStream())) {
                wr.write( postData );
            }

            BufferedReader in = new BufferedReader(
                    new InputStreamReader(con.getInputStream()));
            String inputLine;
            StringBuilder response = new StringBuilder();

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();

            if (con.getResponseCode() == HttpURLConnection.HTTP_OK) {
                JSONObject jsonResponse = new JSONObject(response.toString());
                accessToken = jsonResponse.get(JSON_ACCESS_TOKEN).toString();
                System.out.println(accessToken); //TODO REMOVE THIS
                currentTokenExpiry = System.currentTimeMillis() + jsonResponse.getInt(JSON_ACCESS_TOKEN_EXPIRE_DURATION)*1000;
                return true;
            } else {
                throw new HttpException("Invalid response code: " + con.getResponseCode() + "\n response: " + response);
            }

        } catch (IOException | JSONException | HttpException e) {
            logger.error("Error fetching access token: {}", e.getMessage(), e);
        }
        return false;
    }
}
