import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class SubscriptionTests {
    private static String TOKEN;
    private final String SUBSCRIPTION_ID = "6efcee72-c8d7-4de7-9e6a-4b20fdca5725";
    private static String tenantId = "c96a62e5-1e49-4187-b394-08b694e8bb0d";
    private static SubscriptionRequest subscriptionRequest;


    @BeforeClass
    public static void createConnection() throws Exception {
        Connection connection = new Connection(tenantId, "323a95a2-22eb-46c7-a80b-1ebeadc88ca8", "dsvSLXZdaKJAJ5GrSS29bd1h7k89u4sDMOiglihZkzE=");
        TOKEN = "Bearer " + connection.connect().replace("\"", "");
        System.out.println("token= " + TOKEN);
        String requestBody = "{\n" +
                "    \"webhook\" : {\n" +
                "        \"address\": \"https://webhook.myapp.com/o365/\",\n" +
                "        \"authId\": \"o365activityapinotification\",\n" +
                "        \"expiration\": \"\"\n" +
                "    }\n" +
                "}";
        subscriptionRequest = new SubscriptionRequest(TOKEN, ContentType.SHARE_POINT, tenantId, requestBody, null, null);
    }

    @Test
    public void startSubscriptionTest() {
        ManagementActivityApi activityApi = new ManagementActivityApi(tenantId);
        int responseCode = activityApi.startSubscriprion(subscriptionRequest);
        Assert.assertEquals(200, responseCode);
    }

    @Test
    public void stopSubscriptionTest() {
        ManagementActivityApi activityApi = new ManagementActivityApi(tenantId);
        int responseCode = activityApi.stopSubscriprion(subscriptionRequest);
        Assert.assertEquals(200, responseCode);
    }

    @Test
    public void listCurrentSubscriprionsTest() {
        ManagementActivityApi managementActivityApi = new ManagementActivityApi(tenantId);
        int responseCode = managementActivityApi.listCurrentSubscriprions(subscriptionRequest);
        Assert.assertEquals(200, responseCode);
    }

    @Test
    public void listAvailableContentTest() {
        ManagementActivityApi managementActivityApi = new ManagementActivityApi(tenantId);
        int responseCode = managementActivityApi.listAvailableContent(subscriptionRequest);
        Assert.assertEquals(200, responseCode);
    }


    @Test
    public void recievingNotificationsTest() {
        ManagementActivityApi managementActivityApi = new ManagementActivityApi(tenantId);
        int responseCode = managementActivityApi.recievingNotifications(ContentType.AZURE_ACTIVE_DIRECTORY, "", "");
        Assert.assertEquals(200, responseCode);
    }


    @Test
    public void retrievingContentTest() {
        ManagementActivityApi managementActivityApi = new ManagementActivityApi(tenantId);
        int responseCode = managementActivityApi.retrievingContent("", TOKEN);
        Assert.assertEquals(200, responseCode);
    }


    @Test
    public void listNotificationsTest() {
        ManagementActivityApi managementActivityApi = new ManagementActivityApi(tenantId);
        int responseCode = managementActivityApi.listNotifications(subscriptionRequest);
        Assert.assertEquals(200, responseCode);
    }


    @Test
    public void retrieveResourceFriendlyNamesTest() {
        ManagementActivityApi managementActivityApi = new ManagementActivityApi(tenantId);
        int responseCode = managementActivityApi.retrieveResourceFriendlyNames("", "en-US,en;q=0.5", TOKEN);
        Assert.assertEquals(200, responseCode);
    }
}
