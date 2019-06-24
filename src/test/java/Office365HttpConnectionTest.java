import api.Office365HttpConnection;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import okhttp3.mockwebserver.RecordedRequest;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.IOException;
import java.net.HttpURLConnection;

public class Office365HttpConnectionTest {

    @Test
    public void createHttpUrlConnectionTest() throws IOException {
        HttpURLConnection con = Office365HttpConnection.createHttpConnection("http://localhost:8080/");
        Assert.assertEquals(con.getRequestMethod(), "POST", "the request method isn't POST");
    }

    @Test
    public void connectionTest() throws Exception {
        MockWebServer server = new MockWebServer();
        server.enqueue(new MockResponse().setResponseCode(200));
        server.start(8080);
        String url = "http://localhost:8080/";
        HttpURLConnection con = Office365HttpConnection.createHttpConnection(url);
        Office365HttpConnection.connect(con, "0000", "0000");

        RecordedRequest recordedRequest = server.takeRequest();
        Assert.assertEquals(recordedRequest.getRequestUrl().toString(), url);
        Assert.assertEquals(recordedRequest.getMethod(), "POST", "the request method isn't POST");
        Assert.assertEquals(recordedRequest.getHeader("Content-Type"), "application/x-www-form-urlencoded", "the value of 'Content-Type' isn't as expected");
        Assert.assertEquals(recordedRequest.getHeader("User-Agent"), "Java client", "the value of 'User-Agent' isn't as expected");
    }
}