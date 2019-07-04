package api;

import io.logz.sender.HttpsRequestConfiguration;
import io.logz.sender.SenderStatusReporter;
import io.logz.sender.com.google.gson.Gson;
import io.logz.sender.com.google.gson.JsonObject;
import io.logz.sender.exceptions.LogzioParameterErrorException;
import operations.StatusReporterFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.concurrent.Executors;

public class LogzioSender {
    private static final Logger logger = LoggerFactory.getLogger(LogzioSender.class.getName());
    private static final String LOGZIO_TYPE = "office365";
    private static final int DEFAULT_POOL_SIZE = 3;
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
                .setLogzioType(LOGZIO_TYPE)
                .setLogzioToken(token)
                .build();

        SenderStatusReporter statusReporter = StatusReporterFactory.newSenderStatusReporter(LoggerFactory.getLogger(io.logz.sender.LogzioSender.class));

        logzioSender = io.logz.sender.LogzioSender
                .builder()
                .setTasksExecutor(Executors.newScheduledThreadPool(DEFAULT_POOL_SIZE))
                .setHttpsRequestConfiguration(conf)
                .setReporter(statusReporter)
                .withInMemoryQueue()
                .endInMemoryQueue()
                .build();

        logzioSender.start();
    }

    public void send(String msg) {
        logger.debug("enqueueing msg to sender: \n" + msg);
        JsonObject jsonMessage = createJSONLogMessage(msg); // create JsonObject to send to logz.io
        logzioSender.send(jsonMessage);
    }

    private JsonObject createJSONLogMessage(String msg) {
        Gson g = new Gson();
        JsonObject object = g.fromJson(msg, io.logz.sender.com.google.gson.JsonObject.class);
        return object;
    }
}