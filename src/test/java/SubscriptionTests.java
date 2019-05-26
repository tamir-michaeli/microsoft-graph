import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class SubscriptionTests {
    private static String TOKEN;
    private final String SUBSCRIPTION_ID = "6efcee72-c8d7-4de7-9e6a-4b20fdca5725";
    private static String tenantId = "c96a62e5-1e49-4187-b394-08b694e8bb0d";
    private static String clientId = "08fcc6d8-8092-4423-a561-53298aa8777e";
    private static String clientSecret = "r=Pvtm_TTrysYUO=nes8lJr]Dum7ji29";
    private static String resource = "https://manage.office.com";
    private static SubscriptionRequest subscriptionRequest;
    private ManagementActivityApi activityApi = new ManagementActivityApi(tenantId);;


    @BeforeClass
    public static void createConnection() throws Exception {
        Connection connection = new Connection(tenantId, "323a95a2-22eb-46c7-a80b-1ebeadc88ca8", "dsvSLXZdaKJAJ5GrSS29bd1h7k89u4sDMOiglihZkzE=");
        //TOKEN = "Bearer " + connection.connect().replace("\"", "");
        TOKEN = "Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJSUzI1NiIsIng1dCI6IkhCeGw5bUFlNmd4YXZDa2NvT1UyVEhzRE5hMCIsImtpZCI6IkhCeGw5bUFlNmd4YXZDa2NvT1UyVEhzRE5hMCJ9.eyJhdWQiOiJodHRwczovL21hbmFnZS5vZmZpY2UuY29tIiwiaXNzIjoiaHR0cHM6Ly9zdHMud2luZG93cy5uZXQvYzk2YTYyZTUtMWU0OS00MTg3LWIzOTQtMDhiNjk0ZThiYjBkLyIsImlhdCI6MTU1ODg3NTgzOCwibmJmIjoxNTU4ODc1ODM4LCJleHAiOjE1NTg4Nzk3MzgsImFpbyI6IjQyWmdZS2o2N3R2Z0lzc1c5R2pTbFZOR3Ftb1RBQT09IiwiYXBwaWQiOiIwOGZjYzZkOC04MDkyLTQ0MjMtYTU2MS01MzI5OGFhODc3N2UiLCJhcHBpZGFjciI6IjEiLCJpZHAiOiJodHRwczovL3N0cy53aW5kb3dzLm5ldC9jOTZhNjJlNS0xZTQ5LTQxODctYjM5NC0wOGI2OTRlOGJiMGQvIiwib2lkIjoiMDY3ZWUzZTQtOWVkZS00ZTk4LWJjZjMtMGRkMTRmZTYwOTc5Iiwicm9sZXMiOlsiU2VydmljZUhlYWx0aC5SZWFkIiwiQWN0aXZpdHlGZWVkLlJlYWQiXSwic3ViIjoiMDY3ZWUzZTQtOWVkZS00ZTk4LWJjZjMtMGRkMTRmZTYwOTc5IiwidGlkIjoiYzk2YTYyZTUtMWU0OS00MTg3LWIzOTQtMDhiNjk0ZThiYjBkIiwidXRpIjoidU4yQnY5MlBaVXU4c0ZDRjZWNFJBQSIsInZlciI6IjEuMCJ9.BNj6xJb2bZzjU_p2Xs1dMDxlYGaxzSogHQZnPWL2NX1Vvv_gCsffJpE_DpmpjZ-cParZA8DoGJrqMEA_6n6QikKRczIsQmsjdgvQzYGZvlRP82UV3Jyj0Vksid9HDMJJEvJRiLG20VOKEmDIkrsxQ4HWDNbs1nBxsCK63Y3gyUamVbt1wg_hGIOi159tvM8h4MkFRDrPrP9LouVz46kHGgxbw54c9hVS56epDI-KNbBfpE5rt6BZP34Wz1tTkCnLuo45aFyrHicua7Kk69-JuKsrN-pj1q1QN6-isew2zyTrJpzP_ERCtmyID_kopECa7K8DqvkB-zb1Pt_r-I0t3Q";
        System.out.println("token= " + TOKEN);
    }

    @Test
    public void startSubscriptionTest() {
        String requestBody = "{\n" +
                "    \"webhook\" : {\n" +
                "        \"address\": \"https://webhook.myapp.com/o365/\",\n" +
                "        \"authId\": \"o365activityapinotification\",\n" +
                "        \"expiration\": \"\"\n" +
                "    }\n" +
                "}";
        subscriptionRequest = new SubscriptionRequest(TOKEN, ContentType.AZURE_ACTIVE_DIRECTORY, tenantId, requestBody);
        int responseCode = activityApi.startSubscriprion(subscriptionRequest);
        Assert.assertEquals(200, responseCode);
    }

    @Test
    public void stopSubscriptionTest() {

        int responseCode = activityApi.stopSubscriprion(subscriptionRequest);
        Assert.assertEquals(200, responseCode);
    }

    @Test
    public void listCurrentSubscriprionsTest() {
        int responseCode = activityApi.listCurrentSubscriprions(subscriptionRequest);
        Assert.assertEquals(200, responseCode);
    }

    @Test
    public void listAvailableContentTest() {
        int responseCode = activityApi.listAvailableContent(subscriptionRequest);
        Assert.assertEquals(200, responseCode);
    }


    @Test
    public void recievingNotificationsTest() {
        int responseCode = activityApi.recievingNotifications(ContentType.AZURE_ACTIVE_DIRECTORY, "", "");
        Assert.assertEquals(200, responseCode);
    }


    @Test
    public void retrievingContentTest() {
        int responseCode = activityApi.retrievingContent("", TOKEN);
        Assert.assertEquals(200, responseCode);
    }


    @Test
    public void listNotificationsTest() {
        int responseCode = activityApi.listNotifications(subscriptionRequest);
        Assert.assertEquals(200, responseCode);
    }


    @Test
    public void retrieveResourceFriendlyNamesTest() {
        int responseCode = activityApi.retrieveResourceFriendlyNames("", "en-US,en;q=0.5", TOKEN);
        Assert.assertEquals(200, responseCode);
    }
}
