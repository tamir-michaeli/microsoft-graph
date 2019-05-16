import org.junit.Assert;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;


public class Connection {
    private final String tenantId;
    private final String clientId;
    private final String clientSecret;
    //private final String resource; //FIXME should check if this mandatory


    public Connection(String tenantId, String clientId, String clientSecret) {
        this.tenantId = tenantId;
        this.clientId = clientId;
        this.clientSecret = clientSecret;
    }


    protected String connect() throws Exception {
        String path = "https://login.microsoftonline.com/" + tenantId + "/oauth2/token";
        String body = "grant_type=client_credentials&client_id=" + clientId + "&client_secret=" + clientSecret;
        byte[] requestBody = body.getBytes("UTF-8");

        //post method
        URL url = null;
        try {
            int responseCode;
            String responseMessage;
            url = new URL(path);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");

            conn.setDoOutput(true);
            conn.setDoInput(true);

            conn.getOutputStream().write(requestBody);

            responseCode = conn.getResponseCode();
            responseMessage = conn.getResponseMessage();

            return responseMessage;
        } catch (MalformedURLException e) {
            Assert.fail(e.getMessage());
        } catch (IOException e) {
            Assert.fail(e.getMessage());
        } catch (Exception e) {
            throw new Exception(e);
        }
        return null;
    }
}
