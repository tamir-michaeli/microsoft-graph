package main;

import io.logz.sender.HttpsRequestConfiguration;
import io.logz.sender.LogzioSender;
import io.logz.sender.SenderStatusReporter;
import io.logz.sender.exceptions.LogzioParameterErrorException;
import objects.JsonArrayRequest;
import objects.LogzioJavaSenderParams;
import objects.RequestDataResult;
import org.apache.log4j.Logger;
import org.awaitility.Awaitility;
import org.json.JSONArray;
import org.json.JSONException;
import utils.HangupInterceptor;
import utils.Shutdownable;
import utils.StatusReporterFactory;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

import static java.util.concurrent.TimeUnit.SECONDS;

public class FetchSendManager implements Shutdownable {

    private static final Logger logger = Logger.getLogger(FetchSendManager.class);
    private static final int NO_DELAY = 0;
    private static final int DEFAULT_POLLING_INTERVAL = 1;
    private static final int DEFULAT_TIMEOUT_DURATION = 10;

    private final ScheduledExecutorService taskScheduler;
    private final ArrayList<JsonArrayRequest> dataRequests;
    private final LogzioJavaSenderParams logzioSenderParams;
    private final LogzioSender sender;
    private final int interval;
    private ScheduledExecutorService senderExecutors;


    public FetchSendManager(ArrayList<JsonArrayRequest> dataRequests, LogzioJavaSenderParams senderParams, int interval) {
        this.taskScheduler = Executors.newSingleThreadScheduledExecutor();
        this.logzioSenderParams = senderParams;
        this.dataRequests = dataRequests;
        this.sender = getLogzioSender();
        this.interval = interval;
    }

    public void start() {
        logger.info("starting fetch-send scheduled operation");
        enableHangupSupport();
        taskScheduler.scheduleAtFixedRate(this::pullAndSendData, NO_DELAY, interval, SECONDS);
        sender.start();
    }

    public void pullAndSendData() {
        for (JsonArrayRequest request : dataRequests) {
            RequestDataResult dataResult = new RequestDataResult();
            Awaitility.with()
                    .pollInterval(DEFAULT_POLLING_INTERVAL,  SECONDS)
                    .atMost(DEFULAT_TIMEOUT_DURATION, SECONDS)
                    .await()
                    .until(() -> {
                        dataResult.setRequestDataResult(request.getResult());
                        return  dataResult.isSucceed();
                    });
            for (int i = 0; i < dataResult.getData().length(); i++) {
                try {
                    byte[] jsonAsBytes = StandardCharsets.UTF_8.encode(dataResult.getData().getJSONObject(i).toString()).array();
                    sender.send(jsonAsBytes);
                } catch (JSONException e) {
                    logger.error("error extracting json object from response: " + e.getMessage(), e);
                }
            }
        }
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

            SenderStatusReporter statusReporter = StatusReporterFactory.newSenderStatusReporter(Logger.getLogger(LogzioJavaSenderParams.class));
            LogzioSender.Builder senderBuilder = LogzioSender
                    .builder();
            senderBuilder.setTasksExecutor(senderExecutors);
            senderBuilder.setReporter(statusReporter);
            senderBuilder.setHttpsRequestConfiguration(requestConf);
            senderBuilder.setDebug(logger.isDebugEnabled());
            senderBuilder.setDrainTimeoutSec(logzioSenderParams.getSenderDrainIntervals());

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
            logger.error("problem in one or more parameters with error " + e.getMessage(), e);
        }
        return null;
    }

    private void enableHangupSupport() {
        HangupInterceptor interceptor = new HangupInterceptor(this);
        Runtime.getRuntime().addShutdownHook(interceptor);
    }

    @Override
    public void shutdown() {
        logger.info("requesting data fetcher to stop");
        try {
            taskScheduler.shutdown();
            if (!taskScheduler.awaitTermination(20, SECONDS)) {
                taskScheduler.shutdownNow();
            }
            logger.info("stopping data sender");
            sender.stop();
            senderExecutors.shutdown();
            if (!senderExecutors.awaitTermination(20, SECONDS)) {
                senderExecutors.shutdownNow();
            }
        } catch (InterruptedException e) {
            logger.warn("final request was interrupted: " + e.getMessage(), e);
        } catch (SecurityException ex) {
            logger.error("can't submit final request: " + ex.getMessage(), ex);
        }
        logger.info("Shutting down...");
    }
}
