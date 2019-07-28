import api.MSGraphRequestExecutor;
import main.FetchSendManager;
import objects.JsonArrayRequest;
import objects.LogzioJavaSenderParams;
import objects.RequestDataResult;
import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
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

import javax.naming.AuthenticationException;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import static org.mockserver.integration.ClientAndServer.startClientAndServer;
import static org.mockserver.model.HttpRequest.request;
import static org.mockserver.model.HttpResponse.response;

public class FetchSendTests {

    private static final Logger logger = Logger.getLogger(FetchSendTests.class);

    private static MockServerClient mockServerClient = null;
    private static ClientAndServer mockServer;
    private static LogzioJavaSenderParams senderParams = new LogzioJavaSenderParams();


    @BeforeClass
    public static void startMockServer() throws JSONException {
        logger.info("starting mock server");
        JSONObject requestResponseBody = new JSONObject();
        requestResponseBody.put("@odata.nextLink", "http://localhost:8070/nextlink1");
        requestResponseBody.put("value", new JSONArray("[{\"key\":1}]"));

        JSONObject link1ResponseBody = new JSONObject();
        link1ResponseBody.put("@odata.nextLink", "http://localhost:8070/nextlink2");
        link1ResponseBody.put("value", new JSONArray("[{\"key\":2}]"));

        JSONObject link2ResponseBody = new JSONObject();
        link2ResponseBody.put("value", new JSONArray("[{\"key\":3}]"));


        mockServer = startClientAndServer(8070);

        mockServerClient = new MockServerClient("localhost", 8070);
        mockServerClient
                .when(request().withMethod("POST"))
                .respond(response().withStatusCode(200));
        mockServer
                .when(request().withMethod("GET").withPath("/chainRequest"))
                .respond(response().withStatusCode(200).withBody(requestResponseBody.toString()));
        mockServer
                .when(request().withMethod("GET").withPath("/nextlink1"))
                .respond(response().withStatusCode(200).withBody(link1ResponseBody.toString()));
        mockServer
                .when(request().withMethod("GET").withPath("/nextlink2"))
                .respond(response().withStatusCode(200).withBody(link2ResponseBody.toString()));
    }

    @BeforeClass
    public static void setup() {
        senderParams.setFullListenerUrl("http://127.0.0.1:8070");
        senderParams.setAccountToken("not-a-real-token");
        senderParams.setSenderDrainIntervals(1);
    }

    @AfterClass
    public static void close() {
        mockServer.stop();
    }

    @Test
    public void FetchSendTest() throws InterruptedException, JSONException {
        ArrayList<JsonArrayRequest> requests = new ArrayList<>();
        requests.add(new JsonArrayRequest() {
            @Override
            public RequestDataResult getResult() {
                File signinsFile = new File(getClass().getClassLoader().getResource("sampleSignins.json").getFile());

                try {
                    String content = FileUtils.readFileToString(signinsFile, "utf-8");
                    JSONTokener tokener = new JSONTokener(content);
                    JSONObject object = new JSONObject(tokener);
                    return new RequestDataResult(object.getJSONArray("value"));
                } catch (JSONException | IOException e) {
                    e.printStackTrace();
                }
                return null;
            }
        });

        FetchSendManager manager = new FetchSendManager(requests, senderParams, 10);
        manager.start();
        manager.pullAndSendData();
        Thread.sleep(2000);
        HttpRequest[] recordedRequests = mockServerClient.retrieveRecordedRequests(request().withMethod("POST"));
        Assert.assertEquals(1, recordedRequests.length);
        JSONObject jsonObject = new JSONObject(recordedRequests[0].getBodyAsString());
        Assert.assertEquals("aaaaa-bbbb-cccc-dddd-123456789", jsonObject.getString("id"));
        Assert.assertEquals("John Smith", jsonObject.getString("userDisplayName"));
        Assert.assertEquals("john.s@gmail.com", jsonObject.getString("userPrincipalName"));
    }

    @Test
    public void paginationTest() throws IOException, JSONException, AuthenticationException {
        MSGraphRequestExecutor requestExecutor = new MSGraphRequestExecutor(ApiTests.getSampleAzureADClient()
                , () -> "sampleAccessToken");
        JSONArray jsonArray = requestExecutor.getAllPages("http://localhost:8070/chainRequest");
        Assert.assertEquals(3, jsonArray.length());
    }

}
