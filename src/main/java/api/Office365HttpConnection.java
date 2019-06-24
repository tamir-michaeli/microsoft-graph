package api;

import org.mockserver.model.HttpResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class Office365HttpConnection {
    private static final Logger logger = LoggerFactory.getLogger(Office365HttpConnection.class.getName());

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
        logger.info("connect to office 365: response code = " + httpResponse.getStatusCode() + " response body= " + httpResponse.getBodyAsString());
        return httpResponse;
    }
}