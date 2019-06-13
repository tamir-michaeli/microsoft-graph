import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.mockserver.model.HttpResponse;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.logging.Logger;


public class Connection {
    private static final Logger LOGGER = Logger.getLogger(Connection.class.getName());

    public static HttpURLConnection createHttpConnection(String url) throws IOException {
        URL myUrl = new URL(url);
        HttpURLConnection con = (HttpURLConnection) myUrl.openConnection();
        con.setDoOutput(true);
        con.setRequestMethod("POST");
        con.setRequestProperty("User-Agent", "Java client");
        con.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
        return con;
    }

    public static HttpResponse connect(HttpURLConnection con, String clientId, String clientSecret) throws Exception {
        String body = "grant_type=client_credentials&client_id=" + clientId + "&client_secret=" + clientSecret + "&resource=https://manage.office.com";
        byte[] requestBody = body.getBytes("UTF-8");
        String responseMessage;
        try {
            try (DataOutputStream wr = new DataOutputStream(con.getOutputStream())) {
                wr.write(requestBody);
            }
            StringBuilder content;
            try (BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()))) {
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
        HttpResponse httpResponse = HttpResponse.response(responseMessage);
        LOGGER.info("connect to office 365: response code = " + httpResponse.getStatusCode() + " response body= " + httpResponse.getBodyAsString());
        return httpResponse;
    }

    private String extractToken(String responseMessage) {
        JsonObject jsonObject;
        try {
            jsonObject = new JsonParser().parse(responseMessage).getAsJsonObject();
        } catch (Exception e) {
            LOGGER.warning("extract token from body message: " + e.getMessage());
            throw new IllegalArgumentException("Not a valid json received in body of request", e);
        }
        return jsonObject.get("access_token").toString();
    }
}