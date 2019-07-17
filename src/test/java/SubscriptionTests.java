import api.ManagementActivityApi;
import enums.ContentType;
import okhttp3.mockwebserver.MockWebServer;
import okhttp3.mockwebserver.RecordedRequest;
import org.testng.Assert;
import org.testng.annotations.Test;
import requests.SubscriptionRequest;

public class SubscriptionTests {
    private SubscriptionRequest subscriptionRequest;
    private String path = "http://localhost:8080/";
    MockWebServer server = new MockWebServer();

//    @BeforeClass
//    public void beforeClass() throws IOException {
//        server.enqueue(new MockResponse().setResponseCode(200));
//        server.start(8080);
//    }
//
//    @AfterClass
//    public void afterClass() throws IOException {
//        server.shutdown();
//    }

    @Test
    public void acToken() {
//        MSGraphRequestExecutor client = new MSGraphRequestExecutor(
//                "015fe495-52fc-4a7c-8332-a4db3c331def",
//                "4ecccc8c-8cf5-4718-a14a-cb089f64468a",
//                "LYaMDS56oTe=DNpIHuhFl*4deF:*GzP8");

//        System.out.println(client.getAccessToken());
    }

    @Test
    public void startSubscriptionTest() throws Exception {
        subscriptionRequest = new SubscriptionRequest(path, "", ContentType.SHARE_POINT.getValue(), "");
        ManagementActivityApi.startSubscription(subscriptionRequest);
        RecordedRequest recordedRequest = server.takeRequest();
        String expectedUrl = subscriptionRequest.getPath() + "/subscriptions/start?contentType=" + subscriptionRequest.getContentType();
        Assert.assertEquals(recordedRequest.getMethod(), "POST", "request method isn't Post");
        Assert.assertEquals(recordedRequest.getRequestUrl().toString(), expectedUrl, "request method isn't Post");
    }

    @Test
    public void stopSubscriptionTest() throws Exception {
        subscriptionRequest = new SubscriptionRequest(path, "", ContentType.SHARE_POINT.getValue(), "");
        ManagementActivityApi.stopSubscription(subscriptionRequest);
        RecordedRequest recordedRequest = server.takeRequest();
        String expectedUrl = subscriptionRequest.getPath() + "/subscriptions/stop?contentType=" + subscriptionRequest.getContentType() + "&PublisherIdentifier=" + subscriptionRequest.getPublisherIdentifier();
        Assert.assertEquals(recordedRequest.getMethod(), "POST", "request method isn't Post");
        Assert.assertEquals(recordedRequest.getRequestUrl().toString(), expectedUrl, "request method isn't Post");
    }

    @Test
    public void listCurrentSubscriprionsTest() throws Exception {
        subscriptionRequest = new SubscriptionRequest(path, "", ContentType.SHARE_POINT.getValue(), "");
        ManagementActivityApi.listCurrentSubscriprions(subscriptionRequest);
        RecordedRequest recordedRequest = server.takeRequest();
        String expectedUrl = subscriptionRequest.getPath() + "/subscriptions/list?PublisherIdentifier=" + subscriptionRequest.getPublisherIdentifier();
        Assert.assertEquals(recordedRequest.getMethod(), "GET", "request method isn't Post");
        Assert.assertEquals(recordedRequest.getRequestUrl().toString(), expectedUrl, "request method isn't Post");
    }

    @Test
    public void listAvailableContentTest() throws Exception {
        subscriptionRequest = new SubscriptionRequest(path, "", ContentType.SHARE_POINT.getValue(), "");
        ManagementActivityApi.listAvailableContent(subscriptionRequest);
        RecordedRequest recordedRequest = server.takeRequest();
        String expectedUrl = subscriptionRequest.getPath() + "/subscriptions/content?contentType=" + subscriptionRequest.getContentType()
                + "&amp;startTime=" + subscriptionRequest.getStartTime() + "&amp;endTime=" + subscriptionRequest.getEndTime();
        Assert.assertEquals(recordedRequest.getMethod(), "GET", "request method isn't Post");
        Assert.assertEquals(recordedRequest.getRequestUrl().toString(), expectedUrl, "request method isn't Post");
    }

    @Test
    public void receivingNotificationsTest() throws Exception {
        ManagementActivityApi.receivingNotifications(path, ContentType.AZURE_ACTIVE_DIRECTORY.getValue(), "");
        RecordedRequest recordedRequest = server.takeRequest();
        Assert.assertEquals(recordedRequest.getMethod(), "POST", "request method isn't Post");
        Assert.assertEquals(recordedRequest.getRequestUrl().toString(), path, "request method isn't Post");
    }

    @Test
    public void retrievingContentTest() throws Exception {
        ManagementActivityApi.retrievingContent(path, "", "", "");
        RecordedRequest recordedRequest = server.takeRequest();
        Assert.assertEquals(recordedRequest.getMethod(), "GET", "request method isn't Post");
        Assert.assertEquals(recordedRequest.getRequestUrl().toString(), path, "request method isn't Post");
    }

    @Test
    public void listNotificationsTest() throws Exception {
        subscriptionRequest = new SubscriptionRequest(path, "", ContentType.SHARE_POINT.getValue(), "");
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
        Assert.assertEquals(recordedRequest.getMethod(), "GET", "request method isn't Post");
        Assert.assertEquals(recordedRequest.getRequestUrl().toString(), expectedUrl, "request method isn't Post");
    }
}

