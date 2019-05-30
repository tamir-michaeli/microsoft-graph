import com.google.gson.Gson;
import com.google.gson.JsonObject;
import io.logz.sender.exceptions.LogzioParameterErrorException;
import org.mockserver.model.HttpResponse;

import java.net.HttpURLConnection;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

public class Office365Apis {
    private static String host;
    private static String logzio_token;
    private static String tenant_id;
    private static String client_id;
    private static String client_secret;
    private static String access_token;
    private static Scanner scan;
    private static LogzioDao logzioDao;
    private static LogzioHttpsClient logzioHttpsClient;

    public static void getInput() {
        System.out.println("Logzio Host:");
        scan = new Scanner(System.in);
        host = scan.next();
        System.out.println("Logzio Token:");
        scan = new Scanner(System.in);
        logzio_token = scan.next();
        System.out.println("Logzio Tenant Id:");
        scan = new Scanner(System.in);
        tenant_id = scan.next();
        System.out.println("Logzio Client Id:");
        scan = new Scanner(System.in);
        client_id = scan.next();
        System.out.println("Logzio Client Secret:");
        scan = new Scanner(System.in);
        client_secret = scan.next();
    }

    public static void createConnectionToLogzIo() throws LogzioParameterErrorException {
        logzioHttpsClient = new LogzioHttpsClient(logzio_token, "", "");
        logzioDao = new LogzioDao(logzioHttpsClient, host, logzio_token);
    }

    public static void createConnectionToAzurePortal() throws Exception {
        HttpURLConnection httpConnection = Connection.createHttpConnection("https://login.microsoftonline.com/" + tenant_id + "/oauth2/token");
        HttpResponse httpResponse = Connection.connect(httpConnection, client_id, client_secret);
        Gson gson = new Gson();
        JsonObject jsonObject = gson.fromJson(httpResponse.getBody().toString(), JsonObject.class);
        access_token = jsonObject.get("access_token").getAsString();
    }

    public static void startSubscription() throws Exception {
        String url = "https://manage.office.com/api/v1.0/" + tenant_id + "/activity/feed/";
        ContentType contentType = chooseContentType();

        System.out.println("insert publisher Identifier");
        scan = new Scanner(System.in);
        String publisherId = scan.next();
        System.out.println("insert request body");
        scan = new Scanner(System.in);
        String requestBody = scan.next();

        SubscriptionRequest subscriptionRequest = new SubscriptionRequest(url, access_token, contentType, publisherId, requestBody);
        HttpResponse httpResponse = ManagementActivityApi.startSubscriprion(subscriptionRequest);
        logzioDao.push(httpResponse.getBodyAsString());
    }

    public static void stopSubscription() throws Exception {
        String url = "https://manage.office.com/api/v1.0/" + tenant_id + "/activity/feed/";
        ContentType contentType = chooseContentType();

        System.out.println("insert publisher Identifier");
        scan = new Scanner(System.in);
        String publisherId = scan.next();

        SubscriptionRequest subscriptionRequest = new SubscriptionRequest(url, access_token, contentType, publisherId, null);
        HttpResponse httpResponse = ManagementActivityApi.stopSubscriprion(subscriptionRequest);
        logzioDao.push(httpResponse.getBodyAsString());
    }

    public static void listCurrentSubscriprions() throws Exception {
        String url = "https://manage.office.com/api/v1.0/" + tenant_id + "/activity/feed/";
        System.out.println("insert publisher Identifier");
        scan = new Scanner(System.in);
        String publisherId = scan.next();

        SubscriptionRequest subscriptionRequest = new SubscriptionRequest(url, access_token, null, publisherId, null);
        HttpResponse httpResponse = ManagementActivityApi.listCurrentSubscriprions(subscriptionRequest);
        logzioDao.push(httpResponse.getBodyAsString());
    }

    public static void listAvailableContent() throws Exception {
        String url = "https://manage.office.com/api/v1.0/" + tenant_id + "/activity/feed/";
        System.out.println("insert publisher Identifier");
        scan = new Scanner(System.in);
        String publisherId = scan.next();
        System.out.println("insert start time");
        scan = new Scanner(System.in);
        String startTime = scan.next();
        System.out.println("insert end time");
        scan = new Scanner(System.in);
        String endTime = scan.next();
        ContentType contentType = chooseContentType();

        Date time1 = new SimpleDateFormat("dd/MM/yyyy").parse(startTime);
        Date time2 = new SimpleDateFormat("dd/MM/yyyy").parse(endTime);

        SubscriptionRequest subscriptionRequest = new SubscriptionRequest(url, access_token, contentType, publisherId, null, time1, time2);
        HttpResponse httpResponse = ManagementActivityApi.listAvailableContent(subscriptionRequest);
        logzioDao.push(httpResponse.getBodyAsString());
    }

    public static void listNotifications() throws Exception {
        String url = "https://manage.office.com/api/v1.0/" + tenant_id + "/activity/feed/";
        System.out.println("insert publisher Identifier");
        scan = new Scanner(System.in);
        String publisherId = scan.next();


        SubscriptionRequest subscriptionRequest = new SubscriptionRequest(url, access_token, chooseContentType(), publisherId, null);
        HttpResponse httpResponse = ManagementActivityApi.stopSubscriprion(subscriptionRequest);
        logzioDao.push(httpResponse.getBodyAsString());
    }

    public static void retrieveResourceFriendlyNames() throws Exception {
        String url = "https://manage.office.com/api/v1.0/" + tenant_id + "/activity/feed/";
        System.out.println("insert publisher Identifier");
        scan = new Scanner(System.in);
        String publisherId = scan.next();

        System.out.println("insert Accept Language");
        scan = new Scanner(System.in);
        String acceptLanguage = scan.next();
        HttpResponse httpResponse = ManagementActivityApi.retrieveResourceFriendlyNames(publisherId, acceptLanguage, access_token, url);
        logzioDao.push(httpResponse.getBodyAsString());
    }

    public static void recievingNotifications() throws Exception {
        String path = "https://webhook.myapp.com/o365/ ";

        System.out.println("insert webhook Auth Id");
        scan = new Scanner(System.in);
        String webhookAuthId = scan.next();

        System.out.println("insert request body");
        scan = new Scanner(System.in);
        String request = scan.next();
        HttpResponse httpResponse = ManagementActivityApi.recievingNotifications(path, chooseContentType(), webhookAuthId, request);
        logzioDao.push(httpResponse.getBodyAsString());
    }

    public static void retrievingContent() throws Exception {
        String path = "https://manage.office.com/api/v1.0/";

        System.out.println("insert webhook Auth Id");
        scan = new Scanner(System.in);
        String webhookAuthId = scan.next();

        System.out.println("insert request body");
        scan = new Scanner(System.in);
        String request = scan.next();
        System.out.println("insert content Id");
        String contentId = scan.next();
        System.out.println("insert organization Id");
        String organizationId = scan.next();
        HttpResponse httpResponse = ManagementActivityApi.retrievingContent(path, contentId, access_token, organizationId);
        logzioDao.push(httpResponse.getBodyAsString());
    }

    private static ContentType chooseContentType() {
        System.out.println("Choose Content Type (choose number):" + "\n" +
                "1. Audit.AzureActiveDirectory" + "\n" +
                "2. Audit.Exchange" + "\n" +
                "3. Audit.SharePoint" + "\n" +
                "4. Audit.General");

        scan = new Scanner(System.in);
        int input = Integer.parseInt(scan.next());
        ContentType contentType = null;
        switch (input) {
            case 1:
                contentType = ContentType.AZURE_ACTIVE_DIRECTORY;
                break;
            case 2:
                contentType = ContentType.EXCHANGE;
                break;
            case 3:
                contentType = ContentType.SHARE_POINT;
                break;
            case 4:
                contentType = ContentType.GENERAL;
                break;
            default:
                break;
        }
        return contentType;
    }
}
