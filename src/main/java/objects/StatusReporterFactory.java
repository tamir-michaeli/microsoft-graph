package objects;

import io.logz.sender.SenderStatusReporter;
import org.slf4j.Logger;

public class StatusReporterFactory {
    public static SenderStatusReporter newSenderStatusReporter(final Logger logger) {
        return new SenderStatusReporter() {

            public void error(String s) {
                logger.error("AzureADClient" + s);
            }

            public void error(String s, Throwable throwable) {
                logger.error("AzureADClient" + s + " " + throwable.getMessage());
            }

            public void warning(String s) {
                logger.warn("AzureADClient" + s);
                logger.warn("AzureADClient" + s);
            }

            public void warning(String s, Throwable throwable) {
                logger.warn("AzureADClient" + s + " " + throwable.getMessage());
            }

            @Override
            public void info(String s) {
                logger.info("AzureADClient" + s);
            }

            @Override
            public void info(String s, Throwable throwable) {
                logger.info("AzureADClient" + s + " " + throwable.getMessage());
            }
        };
    }
}

