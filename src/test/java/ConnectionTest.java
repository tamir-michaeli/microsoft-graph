import org.mockserver.client.MockServerClient;
import org.mockserver.integration.ClientAndServer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import static org.mockserver.integration.ClientAndServer.startClientAndServer;
import static org.mockserver.model.HttpRequest.request;
import static org.mockserver.model.HttpResponse.response;

public class ConnectionTest {
    private final Logger logger = LoggerFactory.getLogger(Connection.class);
    private ClientAndServer mockServer;
    private MockServerClient mockServerClient = null;

    @BeforeTest
    private void startMockServer() {
        logger.debug("starting mock server");
        mockServer = startClientAndServer(8070);

        mockServerClient = new MockServerClient("localhost", 8070);
        mockServerClient
                .when(request().withMethod("POST"))
                .respond(response().withStatusCode(200));
    }

    @AfterTest
    public void stopMockServer() {
        logger.info("stoping mock server...");
        mockServer.stop();
    }

    @Test
    public void connectionTest() throws Exception {
        String tenantId = "tenant-id";
        String clientId = "client-id";
        String clientSecret = "client-secret";
        Connection connection = new Connection(tenantId, clientId, clientSecret);
        String token = connection.connect();
        Assert.assertNotNull(token);
    }
}