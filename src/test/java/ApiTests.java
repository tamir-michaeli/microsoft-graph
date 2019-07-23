import api.AuthorizationManager;
import objects.AzureADClient;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import okhttp3.mockwebserver.RecordedRequest;
import org.json.JSONException;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import javax.naming.AuthenticationException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

public class ApiTests {

    private static MockWebServer mockWebServer = new MockWebServer();

    @BeforeClass
    public static void startMockServer() {
        try {
            mockWebServer.start(8123);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void accessTokenRequestTest() throws InterruptedException, UnsupportedEncodingException {
        AzureADClient client = getSampleAzureADClient();
        mockWebServer.enqueue(new MockResponse());
            Assert.assertThrows(AuthenticationException.class, () -> {
            AuthorizationManager manager = new AuthorizationManager(client,"http://localhost:8123");
        });
        RecordedRequest request = mockWebServer.takeRequest();
        String body = request.getBody().readUtf8();
        Assert.assertTrue(body.contains("client_id=" + client.getClientId()));
        Assert.assertTrue(body.contains("client_secret=" + URLEncoder.encode(client.getClientSecret(), StandardCharsets.UTF_8.toString())));
    }

    public static AzureADClient getSampleAzureADClient() {
        String clientID = "1234-5678";
        String clientSecret = "shh don't tell";
        AzureADClient client = new AzureADClient();
        client.setClientId(clientID);
        client.setClientSecret(clientSecret);
        return client;
    }
}
