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

    protected String connect() {
        String path = "https://login.microsoftonline.com/" + tenantId + "/oauth2/token";
        //post method
        URL obj = null;
        try {
            obj = new URL(path);
            HttpURLConnection conn = (HttpURLConnection) obj.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            conn.setRequestProperty("grant_type", "client_credentials");
            conn.setRequestProperty("client_id", clientId);
            conn.setRequestProperty("client_secret", clientSecret);
            // conn.setRequestProperty("resource", resource);
            ConnectionResponse connectionResponse = (ConnectionResponse) conn.getContent();
            return connectionResponse.getAccessToken();
        } catch (MalformedURLException e) {
        } catch (IOException e) {
        } catch (Exception e) {
        }
        return null;
    }
}
