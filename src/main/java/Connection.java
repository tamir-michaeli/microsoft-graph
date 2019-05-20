import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;


public class Connection {
    private final String tenantId;
    private final String clientId;
    private final String clientSecret;

    private static HttpURLConnection con;


    public Connection(String tenantId, String clientId, String clientSecret) {
        this.tenantId = tenantId;
        this.clientId = clientId;
        this.clientSecret = clientSecret;
    }


    protected String connect() throws Exception {
        String path = "https://login.microsoftonline.com/" + tenantId + "/oauth2/token";
        String body = "grant_type=client_credentials&client_id=" + clientId + "&client_secret=" + clientSecret;
        byte[] requestBody = body.getBytes("UTF-8");
        String responseMessage;

        try {

            URL myurl = new URL(path);
            con = (HttpURLConnection) myurl.openConnection();

            con.setDoOutput(true);
            con.setRequestMethod("POST");
            con.setRequestProperty("User-Agent", "Java client");
            con.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");

            try (DataOutputStream wr = new DataOutputStream(con.getOutputStream())) {
                wr.write(requestBody);
            }

            StringBuilder content;

            try (BufferedReader in = new BufferedReader(
                    new InputStreamReader(con.getInputStream()))) {

                String line;
                content = new StringBuilder();

                while ((line = in.readLine()) != null) {
                    content.append(line);
                    content.append(System.lineSeparator());
                }
            }
            responseMessage = content.toString();


        } finally {

            con.disconnect();
        }
        return extractToken(responseMessage);
    }

    private String extractToken(String responseMessage) {
        JsonObject jsonObject;
        try {
            jsonObject = new JsonParser().parse(responseMessage).getAsJsonObject();
        } catch (Exception e) {
            throw new IllegalArgumentException("Not a valid json received in body of request", e);
        }
        return jsonObject.get("access_token").toString();
    }
}
