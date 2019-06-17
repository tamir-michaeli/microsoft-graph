package operations;

public class Office365 {
    private String logzioToken;
    private String logzioHost;
    private String tenantId;
    private String clientId;
    private String clientSecret;
    private String publisherId;
    private String contentType; // FIXME should be list of content types
    private Integer interval;
    private String acceptLanguage;
    private String startTime;


    private void setLogzioToken(String logzioToken) {
        this.logzioToken = logzioToken;
    }

    private void setLogzioHost(String logzioHost) {
        this.logzioHost = logzioHost;
    }

    private void setTenantId(String tenantId) {
        this.tenantId = tenantId;
    }

    private void setClientId(String clientId) {
        this.clientId = clientId;
    }

    private void setClientSecret(String clientSecret) {
        this.clientSecret = clientSecret;
    }

    private void setPublisherId(String publisherId) {
        this.publisherId = publisherId;
    }

    private void setContentType(String contentType) {
        this.contentType = contentType;
    }

    private void setInterval(int interval) {
        this.interval = interval;
    }

    private void setAcceptLanguage(String acceptLanguage) {
        this.acceptLanguage = acceptLanguage;
    }

    private void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getLogzioToken() {
        return logzioToken;
    }

    public String getLogzioHost() {
        return logzioHost;
    }

    public String getTenantId() {
        return tenantId;
    }

    public String getClientId() {
        return clientId;
    }

    public String getClientSecret() {
        return clientSecret;
    }

    public String getPublisherId() {
        return publisherId;
    }

    public String getContentType() {
        return contentType;
    }

    public int getInterval() {
        return interval;
    }

    public String getAcceptLanguage() {
        return acceptLanguage;
    }

    public String getStartTime() {
        return startTime;
    }
}