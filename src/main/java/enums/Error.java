package enums;

public enum Error {
    MISSING_PERMISSIOM("AF10001","The permission set ({0}) sent in the request did not include the expected permission ActivityFeed.Read."),
    MISSING_PARAMETER("AF20001","Missing parameter: {0}."),
    INVALID_PARAMETER("AF20002","Invalid parameter type: {0}. Expected type: {1}"),
    PAST_DATE("AF20003","Expiration {0} provided is set to past date and time."),
    WRONG_TENANT_ID("AF20010","The tenant ID passed in the URL ({0}) does not match the tenant ID passed in the access token ({1})."),
    TENANT_ID_DOESNT_EXIST("AF20011","Specified tenant ID ({0}) does not exist in the system or has been deleted."),
    TENANT_ID_IS_INCOREECTLY_CONFIGURED("AF20012","Specified tenant ID ({0}) is incorrectly configured in the system."),
    INVALID_GUID("AF20013","The tenant ID passed in the URL ({0}) is not a valid GUID."),
    INVALID_CONTENT_TYPE("AF20020","The specified content type is not valid."),
    COULDNT_VALIDATE_WEBHOOK_ENDPOINT("AF20021","The webhook endpoint {{0}) could not be validated. {1}"),
    NO_SUBSCRIPTION_FOUND("AF20022","No subscription found for the specified content type."),
    SUBSCRIPTION_WAS_DISABLED("AF20023","The subscription was disabled by {0}."),
    INVALID_START_OR_END_TIME("AF20030","Start time and end time must both be specified (or both omitted) and must be less than or equal to 24 hours apart, with the start time no more than 7 days in the past."),
    INVALID_NEXT_PAGE_INPUT("AF20031","Invalid nextPage Input: {0}."),
    CONTENT_DOESNT_EXISTED("AF20050","The specified content ({0}) does not exist."),
    EXPIRED_CONTENT_KEY("AF20051","Content requested with the key {0} has already expired. Content older than 7 days cannot be retrieved."),
    INVALID_CONTENT_ID("AF20052","Content ID {0} in the URL is invalid."),
    INVALID_ACCEPT_LANGUAGE_NUMBER("AF20053","Only one language may be present in the Accept-Language header."),
    INVALID_ACCEPT_LANGUAGE("AF20054","Invalid syntax in Accept-Language header."),
    TOO_MANY_REQUESTS("AF429","Too many requests. Method={0}, PublisherId={1}"),
    INTERNAL_ERROR_OCCURRED("AF50000","An internal error occurred. Retry the request.");

    String code;
    String message;

    Error(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public String getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
