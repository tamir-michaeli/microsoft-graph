import main.FetchSendManager;
import objects.JsonArrayRequest;
import objects.LogzioJavaSenderParams;
import org.apache.commons.io.FileUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mockserver.client.MockServerClient;
import org.mockserver.integration.ClientAndServer;
import org.mockserver.model.HttpRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import static org.mockserver.integration.ClientAndServer.startClientAndServer;
import static org.mockserver.model.HttpRequest.request;
import static org.mockserver.model.HttpResponse.response;

public class FetchSendTests {

    private static final Logger logger = LoggerFactory.getLogger(FetchSendTests.class.getName());

    private static MockServerClient mockServerClient = null;
    private static ClientAndServer mockServer;
    private static LogzioJavaSenderParams senderParams = new LogzioJavaSenderParams();


    @BeforeClass
    public static void startMockServer() {
        logger.info("starting mock server");

        mockServer = startClientAndServer(8070);

        mockServerClient = new MockServerClient("localhost", 8070);
        mockServerClient
                .when(request().withMethod("POST"))
                .respond(response().withStatusCode(200));
    }

    @BeforeClass
    public static void setup() {
        senderParams.setFullListenerUrl("http://127.0.0.1:8070");
        senderParams.setAccountToken("not-a-real-token");
        senderParams.setDebug(true);
        senderParams.setSenderDrainIntervals(1);
    }

    @AfterClass
    public static void close() throws IOException {
        mockServer.stop();

    }

    @Test
    public void FetchSendTest() throws InterruptedException, JSONException {
        ArrayList<JsonArrayRequest> requests = new ArrayList<>();
        requests.add(new JsonArrayRequest() {
            @Override
            public JSONArray getData() {
                File signinsFile = new File(getClass().getClassLoader().getResource("sampleSignins.json").getFile());

                try {
                    String content = FileUtils.readFileToString(signinsFile, "utf-8");
                    JSONTokener tokener = new JSONTokener(content);
                    JSONObject object = new JSONObject(tokener);
                    return object.getJSONArray("value"); //(JSONArray) obj.get("value");
                } catch (JSONException | IOException e) {
                    e.printStackTrace();
                }
                return null;
            }
        });
        System.out.println(requests.get(0).getData().toString());

        FetchSendManager manager = new FetchSendManager(requests,senderParams, 10);
        manager.start();
        manager.pullAndSendData();
        Thread.sleep(2000);
        HttpRequest[] recordedRequests = mockServerClient.retrieveRecordedRequests(request().withMethod("POST"));
        Assert.assertEquals(1,recordedRequests.length);
        JSONObject jsonObject = new JSONObject(recordedRequests[0].getBodyAsString());
        Assert.assertEquals("aaaaa-bbbb-cccc-dddd-123456789",jsonObject.getString("id"));
        Assert.assertEquals("John Smith",jsonObject.getString("userDisplayName"));
        Assert.assertEquals("john.s@gmail.com",jsonObject.getString("userPrincipalName"));
    }
}
