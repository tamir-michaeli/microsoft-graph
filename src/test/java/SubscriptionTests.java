import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import okhttp3.mockwebserver.RecordedRequest;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.io.IOException;

public class SubscriptionTests {
    private SubscriptionRequest subscriptionRequest;
    private String path = "http://localhost:8080/";
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
    public void startSubscriptionTest() throws Exception {
        String requestBody = "{ \n\"webhook\" : {\n" +
                "        \"address\": \"https://webhook.myapp.com/o365/\",\n" +
                "        \"authId\": \"o365activityapinotification\"\n   }\n}";
        System.out.println(requestBody);
        subscriptionRequest = new SubscriptionRequest(path, "", ContentType.SHARE_POINT, "", requestBody);
        ManagementActivityApi.startSubscriprion(subscriptionRequest);
        RecordedRequest recordedRequest = server.takeRequest();
        String expectedUrl = subscriptionRequest.getPath() + "/subscriptions/start?contentType=" + subscriptionRequest.getContentType().getValue();
        Assert.assertEquals(recordedRequest.getMethod(), "POST", "request method isn't Post");
        Assert.assertEquals(recordedRequest.getRequestUrl().toString(), expectedUrl, "request method isn't Post");

    }

    @Test
    public void stopSubscriptionTest() throws Exception {
        subscriptionRequest = new SubscriptionRequest(path, "", ContentType.SHARE_POINT, "", null);
        ManagementActivityApi.stopSubscriprion(subscriptionRequest);
        RecordedRequest recordedRequest = server.takeRequest();
        String expectedUrl = subscriptionRequest.getPath() + "/subscriptions/stop?contentType=" + subscriptionRequest.getContentType().getValue() + "&PublisherIdentifier=" + subscriptionRequest.getPublisherIdentifier();
        Assert.assertEquals(recordedRequest.getMethod(), "POST", "request method isn't Post");
        Assert.assertEquals(recordedRequest.getRequestUrl().toString(), expectedUrl, "request method isn't Post");
    }

    @Test
    public void listCurrentSubscriprionsTest() throws Exception {
        subscriptionRequest = new SubscriptionRequest(path, "", ContentType.SHARE_POINT, "", null);
        ManagementActivityApi.listCurrentSubscriprions(subscriptionRequest);
        RecordedRequest recordedRequest = server.takeRequest();
        String expectedUrl = subscriptionRequest.getPath() + "/subscriptions/list?PublisherIdentifier=" + subscriptionRequest.getPublisherIdentifier();
        Assert.assertEquals(recordedRequest.getMethod(), "GET", "request method isn't Post");
        Assert.assertEquals(recordedRequest.getRequestUrl().toString(), expectedUrl, "request method isn't Post");
    }

    @Test
    public void listAvailableContentTest() throws Exception {
        subscriptionRequest = new SubscriptionRequest(path, "", ContentType.SHARE_POINT, "", null);
        ManagementActivityApi.listAvailableContent(subscriptionRequest);
        RecordedRequest recordedRequest = server.takeRequest();
        String expectedUrl = subscriptionRequest.getPath() + "/subscriptions/content?contentType=" + subscriptionRequest.getContentType()
                + "&amp;startTime=" + subscriptionRequest.getStartTime() + "&amp;endTime=" + subscriptionRequest.getEndTime();
        Assert.assertEquals(recordedRequest.getMethod(), "GET", "request method isn't Post");
        Assert.assertEquals(recordedRequest.getRequestUrl().toString(), expectedUrl, "request method isn't Post");
    }


    @Test
    public void recievingNotificationsTest() throws Exception {
        ManagementActivityApi.recievingNotifications(path, ContentType.AZURE_ACTIVE_DIRECTORY, "", "");
        RecordedRequest recordedRequest = server.takeRequest();
        Assert.assertEquals(recordedRequest.getMethod(), "POST", "request method isn't Post");
        Assert.assertEquals(recordedRequest.getRequestUrl().toString(), path, "request method isn't Post");
    }


    @Test
    public void retrievingContentTest() throws Exception {
        ManagementActivityApi.retrievingContent(path, "", "");
        RecordedRequest recordedRequest = server.takeRequest();
        Assert.assertEquals(recordedRequest.getMethod(), "GET", "request method isn't Post");
        Assert.assertEquals(recordedRequest.getRequestUrl().toString(), path, "request method isn't Post");
    }


    @Test
    public void listNotificationsTest() throws Exception {
        subscriptionRequest = new SubscriptionRequest(path, "", ContentType.SHARE_POINT, "", null);
        ManagementActivityApi.listNotifications(subscriptionRequest);
        RecordedRequest recordedRequest = server.takeRequest();
        String expectedUrl = subscriptionRequest.getPath() + "/subscriptions/notifications?contentType=" +
                subscriptionRequest.getContentType() + "&PublisherIdentifier=" + subscriptionRequest.getPublisherIdentifier();
        Assert.assertEquals(recordedRequest.getMethod(), "GET", "request method isn't Post");
        Assert.assertEquals(recordedRequest.getRequestUrl().toString(), expectedUrl, "request method isn't Post");
    }


    @Test
    public void retrieveResourceFriendlyNamesTest() throws Exception {
        ManagementActivityApi.retrieveResourceFriendlyNames("", "en-US,en;q=0.5", "", path);
        RecordedRequest recordedRequest = server.takeRequest();
        String expectedUrl = path + "/resources/dlpSensitiveTypes?PublisherIdentifier=" + "";
        ;
        Assert.assertEquals(recordedRequest.getMethod(), "GET", "request method isn't Post");
        Assert.assertEquals(recordedRequest.getRequestUrl().toString(), expectedUrl, "request method isn't Post");
    }
}

