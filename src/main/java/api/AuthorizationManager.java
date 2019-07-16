package api;

import okio.Utf8;
import operations.AzureADClient;
import org.apache.http.HttpException;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

public class AuthorizationManager {

    private static final Logger logger = LoggerFactory.getLogger(AuthorizationManager.class.getName());

    private static final String MICROSOFTONLINE_ADDRESS = "https://login.microsoftonline.com/";
    private static final String SCOPE = "https://graph.microsoft.com/.default";
    private static final String OAUTH2_TOKEN_API = "/oauth2/v2.0/token";
    private static final String CLIENT_CREDENTIALS_GRANT_TYPE = "client_credentials";
    private static final String REQUEST_CONTENT_TYPE = "Content-Type";
    private static final String APPLICATION_FORM_URLENCODED = "application/x-www-form-urlencoded";
    private static final String REQUEST_CONTENT_LENGTH = "Content-Length";
    private static final String JSON_ACCESS_TOKEN = "access_token";
    private static final String JSON_ACCESS_TOKEN_EXPIRE_DURATION = "expires_in";

    private final String clientId;
    private final String clientSecret;
    private final String tenantId;
    private String accessToken;
    private long currentTokenExpiry = 0;


    public AuthorizationManager(AzureADClient client){
        //todo init message
        this.clientId = client.getClientId();
        this.clientSecret = client.getClientSecret();
        this.tenantId = client.getTenantId();
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
                    + "&client_secret=" + URLEncoder.encode(clientSecret, StandardCharsets.UTF_8.toString());
            byte[] postData = urlParameters.getBytes(StandardCharsets.UTF_8);
            int postDataLength = postData.length;

            URL obj = new URL(url);
            HttpURLConnection con = (HttpURLConnection) obj.openConnection();

            con.setDoOutput(true);
            con.setRequestProperty(REQUEST_CONTENT_TYPE, APPLICATION_FORM_URLENCODED);
            con.setRequestProperty(REQUEST_CONTENT_LENGTH, Integer.toString(postDataLength));
            con.setRequestProperty("Accept", "*/*");
            con.setRequestProperty("accept-encoding", "gzip, deflate");
            con.setRequestProperty("User-Agent", "Java client");
            con.setRequestProperty("Cache-Control", "no-cache");
            con.setRequestProperty("Host", "login.microsoftonline.com");


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
