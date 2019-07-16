package main;

import io.logz.sender.HttpsRequestConfiguration;
import io.logz.sender.LogzioSender;
import io.logz.sender.SenderStatusReporter;
import io.logz.sender.exceptions.LogzioParameterErrorException;
import operations.StatusReporterFactory;
import org.json.JSONArray;
import org.json.JSONException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.charset.StandardCharsets;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class FetchSendManager {

    private static final Logger logger = LoggerFactory.getLogger(FetchSendManager.class.getName());

    private ScheduledExecutorService taskScheduler;
    private JsonArrayRequest[] dataRequests;
    private LogzioJavaSenderParams logzioSenderParams;
    private ScheduledExecutorService senderExecutors;
    private LogzioSender sender;
    private int lastFetch;
    private int interval;

    public FetchSendManager(JsonArrayRequest[] dataRequests, LogzioJavaSenderParams senderParams) {
        this.taskScheduler = Executors.newSingleThreadScheduledExecutor();
        this.logzioSenderParams = senderParams;
        this.dataRequests = dataRequests;
        this.sender = getLogzioSender();
    }

    public void start() {
        taskScheduler.scheduleAtFixedRate(this::pullAndSendData,0,60, TimeUnit.SECONDS);
    }

    public void pullAndSendData() {
        for (JsonArrayRequest request : dataRequests) {
            JSONArray result = request.getData(lastFetch, lastFetch + interval);
            for (int i = 0; i < result.length(); i++) {
                try {
                    byte[] jsonAsBytes = StandardCharsets.UTF_8.encode(result.getJSONObject(i).toString()).array();
                    sender.send(jsonAsBytes);
                } catch (JSONException e) {
                    logger.error("error parsing response: {}", e.getMessage(), e);
                }

            }
        }
        lastFetch += interval;
    }

    private LogzioSender getLogzioSender() {

        senderExecutors = Executors.newScheduledThreadPool(logzioSenderParams.getThreadPoolSize());
        try {
            HttpsRequestConfiguration requestConf = HttpsRequestConfiguration
                    .builder()
                    .setLogzioListenerUrl(logzioSenderParams.getListenerUrl())
                    .setLogzioType(logzioSenderParams.getType())
                    .setLogzioToken(logzioSenderParams.getAccountToken())
                    .setCompressRequests(logzioSenderParams.isCompressRequests())
                    .build();

        SenderStatusReporter statusReporter = StatusReporterFactory.newSenderStatusReporter(logger);
        LogzioSender.Builder senderBuilder = LogzioSender
                .builder();
        senderBuilder.setTasksExecutor(senderExecutors);
        senderBuilder.setReporter(statusReporter);
        senderBuilder.setHttpsRequestConfiguration(requestConf);
        senderBuilder.setDebug(logzioSenderParams.isDebug());

        if (logzioSenderParams.isFromDisk()) {
            senderBuilder.withDiskQueue()
                    .setQueueDir(logzioSenderParams.getQueueDir())
                    .setCheckDiskSpaceInterval(logzioSenderParams.getDiskSpaceCheckInterval())
                    .setFsPercentThreshold(logzioSenderParams.getFileSystemFullPercentThreshold())
                    .setGcPersistedQueueFilesIntervalSeconds(logzioSenderParams.getGcPersistedQueueFilesIntervalSeconds())
                    .endDiskQueue();
        } else {
            senderBuilder.withInMemoryQueue()
                    .setCapacityInBytes(logzioSenderParams.getInMemoryQueueCapacityInBytes())
                    .setLogsCountLimit(logzioSenderParams.getLogsCountLimit())
                    .endInMemoryQueue();
        }

            return senderBuilder.build();
        } catch (LogzioParameterErrorException e) {
            logger.error("problem in one or more parameters with error {}", e.getMessage(), e);
        }
        return null;
    }

}
