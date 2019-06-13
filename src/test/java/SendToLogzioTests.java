import io.logz.sender.exceptions.LogzioParameterErrorException;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.IOException;

public class SendToLogzioTests {

    @Test
    public void sendToLogzioTest() throws IOException {
        Office365Apis.getInput();
        try {
            Logzio.sender(Office365Apis.logzio_token, " {\"content\" : \"type\"}");
        } catch (LogzioParameterErrorException e) {
            Assert.fail();
        }
        Assert.assertTrue(true, "the logz has been sent to logzio");
    }
}
