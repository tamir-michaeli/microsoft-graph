import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import io.logz.sender.exceptions.LogzioParameterErrorException;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.mockserver.model.HttpResponse;

import java.io.File;
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
    private static String publisherId;
    private static ContentType contentType;
    public static int interval;

    private static Scanner scan = new Scanner(System.in);;
    private static LogzioDao logzioDao;
    private static LogzioHttpsClient logzioHttpsClient;

    public static void getInput() {

        ObjectMapper mapper = new ObjectMapper(new YAMLFactory());
        try {
            File file = new File("/Users/hananegbaria/workspace/integrations/office365/src/main/resources/conf.yml");
            Office365 office365 = mapper.readValue(file, Office365.class);
            System.out.println(ReflectionToStringBuilder.toString(office365, ToStringStyle.MULTI_LINE_STYLE));
            host = office365.getLogzioHost();
            logzio_token = office365.getLogzioToken();
            tenant_id = office365.getTenantId();
            client_id = office365.getClientId();
            client_secret = office365.getClientSecret();
            publisherId = office365.getPublisherId();
            contentType = ContentType.valueOf(office365.getContentType());
            interval= office365.getInterval();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
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

        System.out.println("insert request body");
        String requestBody = scan.next();

        SubscriptionRequest subscriptionRequest = new SubscriptionRequest(url, access_token, contentType, publisherId, requestBody);
        HttpResponse httpResponse = ManagementActivityApi.startSubscriprion(subscriptionRequest);
        logzioDao.push(httpResponse.getBodyAsString());
    }

    public static void stopSubscription() throws Exception {
        String url = "https://manage.office.com/api/v1.0/" + tenant_id + "/activity/feed/";
        ContentType contentType = chooseContentType();

        SubscriptionRequest subscriptionRequest = new SubscriptionRequest(url, access_token, contentType, publisherId, null);
        HttpResponse httpResponse = ManagementActivityApi.stopSubscriprion(subscriptionRequest);
        logzioDao.push(httpResponse.getBodyAsString());
    }

    public static void listCurrentSubscriprions() throws Exception {
        String url = "https://manage.office.com/api/v1.0/" + tenant_id + "/activity/feed/";

        SubscriptionRequest subscriptionRequest = new SubscriptionRequest(url, access_token, null, publisherId, null);
        HttpResponse httpResponse = ManagementActivityApi.listCurrentSubscriprions(subscriptionRequest);
        logzioDao.push(httpResponse.getBodyAsString());
    }

    public static void listAvailableContent() throws Exception {
        String url = "https://manage.office.com/api/v1.0/" + tenant_id + "/activity/feed/";

        System.out.println("insert start time");

        String startTime = scan.next();
        System.out.println("insert end time");
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
        String publisherId = scan.next();


        SubscriptionRequest subscriptionRequest = new SubscriptionRequest(url, access_token, chooseContentType(), publisherId, null);
        HttpResponse httpResponse = ManagementActivityApi.stopSubscriprion(subscriptionRequest);
        logzioDao.push(httpResponse.getBodyAsString());
    }

    public static void retrieveResourceFriendlyNames() throws Exception {
        String url = "https://manage.office.com/api/v1.0/" + tenant_id + "/activity/feed/";
        System.out.println("insert publisher Identifier");
        String publisherId = scan.next();

        System.out.println("insert Accept Language");
        String acceptLanguage = scan.next();
        HttpResponse httpResponse = ManagementActivityApi.retrieveResourceFriendlyNames(publisherId, acceptLanguage, access_token, url);
        logzioDao.push(httpResponse.getBodyAsString());
    }

    public static void recievingNotifications() throws Exception {
        String path = "https://webhook.myapp.com/o365/ ";

        System.out.println("insert webhook Auth Id");
        String webhookAuthId = scan.next();

        System.out.println("insert request body");
        String request = scan.next();
        HttpResponse httpResponse = ManagementActivityApi.recievingNotifications(path, chooseContentType(), webhookAuthId, request);
        logzioDao.push(httpResponse.getBodyAsString());
    }

    public static void retrievingContent() throws Exception {
        String path = "https://manage.office.com/api/v1.0/";

        System.out.println("insert webhook Auth Id");
        String webhookAuthId = scan.next();

        System.out.println("insert request body");
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
