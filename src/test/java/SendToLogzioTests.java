import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.io.IOException;

public class SendToLogzioTests {
    private String path = "http://localhost:8080";
    MockWebServer server = new MockWebServer();

    @BeforeClass
    public void beforeClass() throws IOException {
        server.enqueue(new MockResponse().setResponseCode(200));
        server.start(8080);
    }

    @AfterClass
    public void afterClass() throws IOException {
        server.shutdown();
    }

    @Test
    public void sendToLogzioTest() throws Exception {
       /* Office365Apis office365Apis = new Office365Apis();
        String expectedUrl = path + "/?token=" + Office365Apis.logzio_token + "&type=javaSenderType";
        String msg = "{\"content\" : \"type\"}";
        LogzioSender.send(Office365Apis.logzio_token, msg, path);
        RecordedRequest recordedRequest = server.takeRequest();
        Assert.assertEquals(recordedRequest.getRequestUrl().toString(), expectedUrl);*/
    }
}
