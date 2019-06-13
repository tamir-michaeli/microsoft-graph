import io.logz.sender.HttpsRequestConfiguration;
import io.logz.sender.LogzioSender;
import io.logz.sender.SenderStatusReporter;
import io.logz.sender.com.google.gson.Gson;
import io.logz.sender.com.google.gson.JsonObject;
import io.logz.sender.exceptions.LogzioParameterErrorException;
import org.slf4j.LoggerFactory;

import java.util.concurrent.Executors;
import java.util.logging.Logger;

public class Logzio {
    private static final Logger LOGGER = Logger.getLogger(Logzio.class.getName());

    public static void sender(String logzioToken, String msg) throws LogzioParameterErrorException {
        LOGGER.info("sending msg to logz.io");
        HttpsRequestConfiguration conf = HttpsRequestConfiguration
                .builder()
                .setLogzioListenerUrl("https://listener.logz.io:8071/")
                .setLogzioType("javaSenderType")
                .setLogzioToken(logzioToken)
                .build();

        SenderStatusReporter statusReporter = StatusReporterFactory.newSenderStatusReporter(LoggerFactory.getLogger(LogzioSender.class));

        LogzioSender logzioSender = LogzioSender
                .builder()
                .setTasksExecutor(Executors.newScheduledThreadPool(3))
                .setHttpsRequestConfiguration(conf)
                .setReporter(statusReporter)
                .withInMemoryQueue()
                .endInMemoryQueue()
                .build();

        logzioSender.start();
        JsonObject jsonMessage = createLogMessage(msg); // create JsonObject to send to logz.io
        logzioSender.send(jsonMessage);
    }

    private static JsonObject createLogMessage(String msg) {
        Gson g = new Gson();
        JsonObject object = g.fromJson(msg, JsonObject.class);
        return object;
    }
}
