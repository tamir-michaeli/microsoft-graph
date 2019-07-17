package api;

import org.json.JSONArray;
import org.json.JSONException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

import static api.MSGraphRequestExecutor.API_ULR;

public class Office365Apis {

    private static final Logger logger = LoggerFactory.getLogger(Office365Apis.class.getName());
    private MSGraphRequestExecutor requestExecutor;

    public Office365Apis(MSGraphRequestExecutor executor) {
        this.requestExecutor = executor;
    }

    public JSONArray getSignIns() {
        try {
            return requestExecutor.getAllPages(API_ULR + "auditLogs/signIns" + requestExecutor.timeFilterSuffix("createdDateTime"));
        } catch (IOException | JSONException e) {
            logger.error("error parsing response: {}", e.getMessage(), e);
        }
        return new JSONArray();
    }

    public JSONArray getDirectoryAudits() {
        try {
            return requestExecutor.getAllPages(API_ULR + "auditLogs/directoryaudits" + requestExecutor.timeFilterSuffix("activityDateTime"));
        } catch (IOException | JSONException e) {
            logger.error("error parsing response: {}", e.getMessage(), e);
        }
        return new JSONArray();
    }
}
