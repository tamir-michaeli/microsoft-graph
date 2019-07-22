package api;

import org.json.JSONArray;
import org.json.JSONException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.naming.AuthenticationException;
import java.io.IOException;

import static api.MSGraphRequestExecutor.API_ULR;

public class Office365Apis {

    private static final Logger logger = LoggerFactory.getLogger(Office365Apis.class.getName());
    private static final String AD_SINGINS = "auditLogs/signIns";
    private static final String CREATED_DATE_TIME_FIELD = "createdDateTime";
    private static final String AD_DIRECTORY_AUDITS = "auditLogs/directoryaudits";
    private static final String ACTIVITY_DATE_TIME_FIELD = "activityDateTime";
    private final MSGraphRequestExecutor requestExecutor;

    public Office365Apis(MSGraphRequestExecutor executor) {
        this.requestExecutor = executor;
    }

    public JSONArray getSignIns() {
        try {
            return requestExecutor.getAllPages(API_ULR + AD_SINGINS + requestExecutor.timeFilterSuffix(CREATED_DATE_TIME_FIELD));
        } catch (IOException | JSONException e) {
            logger.error("error parsing response: {}", e.getMessage(), e);
        } catch (AuthenticationException e) {
            logger.error(e.getMessage(), e);
        }
        return new JSONArray();
    }

    public JSONArray getDirectoryAudits() {
        try {
            return requestExecutor.getAllPages(API_ULR + AD_DIRECTORY_AUDITS + requestExecutor.timeFilterSuffix(ACTIVITY_DATE_TIME_FIELD));
        } catch (IOException | JSONException e) {
            logger.error("error parsing response: {}", e.getMessage(), e);
        } catch (AuthenticationException e) {
            logger.error(e.getMessage(), e);
        }
        return new JSONArray();
    }
}
