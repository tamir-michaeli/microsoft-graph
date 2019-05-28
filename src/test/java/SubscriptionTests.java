/*
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class SubscriptionTests {
    private static String TOKEN;
    private final String SUBSCRIPTION_ID = "6efcee72-c8d7-4de7-9e6a-4b20fdca5725";
    private static String tenantId = "c96a62e5-1e49-4187-b394-08b694e8bb0d";
    private static String clientId = "08fcc6d8-8092-4423-a561-53298aa8777e";
    private static String clientSecret = "=@oTXIEJ[pEKp.v9eNUqMtNhz4v06T5T";
    private static SubscriptionRequest subscriptionRequest;
    private ManagementActivityApi activityApi = new ManagementActivityApi(tenantId);


    @BeforeClass
    public static void createConnection() throws Exception {
        path = tenantId, clientId, clientSecret
        Connection connection = new Connection(path);
        //TOKEN = "Bearer " + connection.connect().replace("\"", "");
        TOKEN = "Bearer " + "eyJ0eXAiOiJKV1QiLCJhbGciOiJSUzI1NiIsIng1dCI6IkhCeGw5bUFlNmd4YXZDa2NvT1UyVEhzRE5hMCIsImtpZCI6IkhCeGw5bUFlNmd4YXZDa2NvT1UyVEhzRE5hMCJ9.eyJhdWQiOiJodHRwczovL21hbmFnZS5vZmZpY2UuY29tIiwiaXNzIjoiaHR0cHM6Ly9zdHMud2luZG93cy5uZXQvYzk2YTYyZTUtMWU0OS00MTg3LWIzOTQtMDhiNjk0ZThiYjBkLyIsImlhdCI6MTU1ODkzODU4MCwibmJmIjoxNTU4OTM4NTgwLCJleHAiOjE1NTg5NDI0ODAsImFpbyI6IjQyWmdZTkRZTjNQQ3pkSXpxMDdvbUIvYmU5ZlpBd0E9IiwiYXBwaWQiOiIzMjNhOTVhMi0yMmViLTQ2YzctYTgwYi0xZWJlYWRjODhjYTgiLCJhcHBpZGFjciI6IjEiLCJpZHAiOiJodHRwczovL3N0cy53aW5kb3dzLm5ldC9jOTZhNjJlNS0xZTQ5LTQxODctYjM5NC0wOGI2OTRlOGJiMGQvIiwib2lkIjoiNGM0ZjNlYWUtY2RmZC00ODA0LTkxMDYtZDZlYjViOWE4YTc1Iiwicm9sZXMiOlsiVGhyZWF0SW50ZWxsaWdlbmNlLlJlYWQiLCJBY3Rpdml0eVJlcG9ydHMuUmVhZCIsIkFjdGl2aXR5RmVlZC5SZWFkRGxwIiwiU2VydmljZUhlYWx0aC5SZWFkIiwiQWN0aXZpdHlGZWVkLlJlYWQiXSwic3ViIjoiNGM0ZjNlYWUtY2RmZC00ODA0LTkxMDYtZDZlYjViOWE4YTc1IiwidGlkIjoiYzk2YTYyZTUtMWU0OS00MTg3LWIzOTQtMDhiNjk0ZThiYjBkIiwidXRpIjoiSEZHd19oVUxJa2VDR1lYa0lkTVdBQSIsInZlciI6IjEuMCJ9.hjHenpjydjo-AYoDO-ZcAG00kR-CYNldAJ-WjIddAcJ4RKggWvXfa8OyrixELW6aNwTy3agSWnCT5eB_iw2OGK1q-muOpiuzH9mCSWr7rbIiJ9_VwbsevumhkEOysol-pns1orE0U7jVuQmV3GE-UB17v9eEFN0dS0ZPRALeCtjzYX-bICSoaZVn8l_C2ZIW8am_wT2rnYoZovbd36QGa4-65u_dlZPeYe0mMjtequOWUJBzbcxMN7G7eOwDmZ0urKs3_y3YnBpqh9w4lUpL1ME1igTab4-CqUGDBTUGWBLF2OUwI2e59uV6aFP5vda7Cg48CcZTt4VU1bRkK1UFcg";
        System.out.println("Token = " + TOKEN);
    }

    @Test
    public void startSubscriptionTest() {
        String requestBody = "{\n" +
                "    \"webhook\" : {\n" +
                "        \"address\": \"https://webhook.LogzIo.com/o365/\",\n" +
                "        \"authId\": \"o365activityapinotification\",\n" +
                "        \"expiration\": \"\"\n" +
                "    }\n" +
                "}";
        System.out.println(requestBody);
        subscriptionRequest = new SubscriptionRequest(TOKEN, ContentType.SHARE_POINT, tenantId, requestBody);
        int responseCode = activityApi.startSubscriprion(subscriptionRequest);
        Assert.assertEquals(200, responseCode);
    }

    @Test
    public void stopSubscriptionTest() {
        subscriptionRequest = new SubscriptionRequest(TOKEN, ContentType.SHARE_POINT, tenantId, null);
        int responseCode = activityApi.stopSubscriprion(subscriptionRequest);
        Assert.assertEquals(200, responseCode);
    }

    @Test
    public void listCurrentSubscriprionsTest() {
        subscriptionRequest = new SubscriptionRequest(TOKEN, ContentType.SHARE_POINT, tenantId, null);
        int responseCode = activityApi.listCurrentSubscriprions(subscriptionRequest);
        Assert.assertEquals(200, responseCode);
    }

    @Test
    public void listAvailableContentTest() {
        subscriptionRequest = new SubscriptionRequest(TOKEN, ContentType.SHARE_POINT, tenantId, null);
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
*/
