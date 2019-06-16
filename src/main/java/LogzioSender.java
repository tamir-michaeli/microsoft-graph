import io.logz.sender.HttpsRequestConfiguration;
import io.logz.sender.SenderStatusReporter;
import io.logz.sender.com.google.gson.Gson;
import io.logz.sender.com.google.gson.JsonObject;
import io.logz.sender.exceptions.LogzioParameterErrorException;
import org.slf4j.LoggerFactory;

import java.util.concurrent.Executors;
import java.util.logging.Logger;

public class LogzioSender {
    private static final Logger LOGGER = Logger.getLogger(LogzioSender.class.getName());
    private String token;
    private String host;
    private io.logz.sender.LogzioSender logzioSender;

    public LogzioSender(String token, String host) throws LogzioParameterErrorException {
        this.token = token;
        this.host = host;

        connect();
    }

    private void connect() throws LogzioParameterErrorException {
        HttpsRequestConfiguration conf = HttpsRequestConfiguration
                .builder()
                .setLogzioListenerUrl(host)
                .setLogzioType("javaSenderType")
                .setLogzioToken(token)
                .build();

        SenderStatusReporter statusReporter = StatusReporterFactory.newSenderStatusReporter(LoggerFactory.getLogger(io.logz.sender.LogzioSender.class));

        logzioSender = io.logz.sender.LogzioSender
                .builder()
                .setTasksExecutor(Executors.newScheduledThreadPool(3))
                .setHttpsRequestConfiguration(conf)
                .setReporter(statusReporter)
                .withInMemoryQueue()
                .endInMemoryQueue()
                .build();

        logzioSender.start();
    }

    public void send(String msg) {
        LOGGER.info("sending msg to logz.io: \n" + msg);
        JsonObject jsonMessage = createLogMessage(msg); // create JsonObject to send to logz.io
        logzioSender.send(jsonMessage);
    }

    private JsonObject createLogMessage(String msg) {
        Gson g = new Gson();
        JsonObject object = g.fromJson(msg, io.logz.sender.com.google.gson.JsonObject.class);
        return object;
    }
}