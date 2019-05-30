public class Office365 {
    private String logzioToken;
    private String logzioHost;
    private String tenantId;
    private String clientId;
    private String clientSecret;
    private String publisherId;
    private String contentType;
    private int interval;

    public Office365(String logzioToken, String logzioHost, String tenantId, String clientId, String clientSecret, String publisherId, String contentType, int interval) {
        this.logzioToken = logzioToken;
        this.logzioHost = logzioHost;
        this.tenantId = tenantId;
        this.clientId = clientId;
        this.clientSecret = clientSecret;
        this.publisherId = publisherId;
        this.contentType = contentType;
        this.interval = interval;
    }

    public void setLogzioToken(String logzioToken) {
        this.logzioToken = logzioToken;
    }

    public void setLogzioHost(String logzioHost) {
        this.logzioHost = logzioHost;
    }

    public void setTenantId(String tenantId) {
        this.tenantId = tenantId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public void setClientSecret(String clientSecret) {
        this.clientSecret = clientSecret;
    }

    public void setPublisherId(String publisherId) {
        this.publisherId = publisherId;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public void setInterval(int interval) {
        this.interval = interval;
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
}
