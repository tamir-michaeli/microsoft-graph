package operations;

public class AzureADClient {

    private String tenantId;
    private String clientId;
    private String clientSecret;
//    private String publisherId;
//    private String contentType; // FIXME should be list of content types
    private int pullInterval;
//    private String acceptLanguage;
//    private String startTime;


    public AzureADClient() {}

    public void setTenantId(String tenantId) {
        this.tenantId = tenantId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public void setClientSecret(String clientSecret) {
        this.clientSecret = clientSecret;
    }

//    private void setPublisherId(String publisherId) {
//        this.publisherId = publisherId;
//    }
//
//    private void setContentType(String contentType) {
//        this.contentType = contentType;
//    }
//
    public void setPullInterval(int pullInterval) {
        this.pullInterval = pullInterval;
    }
//
//    private void setAcceptLanguage(String acceptLanguage) {
//        this.acceptLanguage = acceptLanguage;
//    }
//
//    private void setStartTime(String startTime) {
//        this.startTime = startTime;
//    }

    public String getTenantId() {
        return tenantId;
    }

    public String getClientId() {
        return clientId;
    }

    public String getClientSecret() {
        return clientSecret;
    }
//
//    public String getPublisherId() {
//        return publisherId;
//    }
//
//    public String getContentType() {
//        return contentType;
//    }

    public int getPullInterval() {
        return pullInterval;
    }

//    public String getAcceptLanguage() {
//        return acceptLanguage;
//    }
//
//    public String getStartTime() {
//        return startTime;
//    }
}