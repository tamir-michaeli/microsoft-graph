package api;

import objects.RequestDataResult;
import org.apache.log4j.Logger;
import org.json.JSONException;

import javax.naming.AuthenticationException;
import java.io.IOException;

public class Office365Apis {

    private static final Logger logger = Logger.getLogger(Office365Apis.class);
    private static final String AD_SINGINS = "auditLogs/signIns";
    private static final String CREATED_DATE_TIME_FIELD = "createdDateTime";
    private static final String AD_DIRECTORY_AUDITS = "auditLogs/directoryaudits";
    private static final String ACTIVITY_DATE_TIME_FIELD = "activityDateTime";
    private final MSGraphRequestExecutor requestExecutor;

    public Office365Apis(MSGraphRequestExecutor executor) {
        this.requestExecutor = executor;
    }

    private RequestDataResult office365request(String api, String dateField) {
        RequestDataResult dataResult = new RequestDataResult();
        try {
            dataResult.setData(requestExecutor.getAllPages(api, dateField));
            return dataResult;
        } catch (IOException | JSONException e) {
            logger.warn("error parsing response: " + e.getMessage(), e);
        } catch (AuthenticationException e) {
            logger.error(e.getMessage(), e);
        }
        dataResult.setSucceed(false);
        return dataResult;
    }

    public RequestDataResult getSignIns() {
        return office365request(AD_SINGINS ,CREATED_DATE_TIME_FIELD);
    }

    public RequestDataResult getDirectoryAudits() {
        return office365request(AD_DIRECTORY_AUDITS, ACTIVITY_DATE_TIME_FIELD);
    }
}
