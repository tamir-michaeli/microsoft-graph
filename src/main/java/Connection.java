import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import okhttp3.HttpUrl;
import org.mockserver.model.HttpResponse;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;


public class Connection {
    /**
     * base_url = "https://login.microsoftonline.com/" + tenantId + "/oauth2/token";
     */


    public static HttpURLConnection createHttpConnection(String url) throws IOException {
        HttpURLConnection con = null;
        URL myurl = new URL(url);
        con = (HttpURLConnection) myurl.openConnection();

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
        HttpResponse httpResponse = HttpResponse.response(responseMessage);

        return httpResponse;
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
