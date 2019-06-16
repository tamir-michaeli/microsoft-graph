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

    public void setAcceptLanguage(String acceptLanguage) {
        this.acceptLanguage = acceptLanguage;
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
}
