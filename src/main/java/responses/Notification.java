package responses;

public class Notification {
    private String tenantId;
    private String clientId;
    private String contentType;
    private String contentId;
    private String contentUri;
    private String contentCreated;
    private String contentExpiration;
    private String notificationSent;
    private String notificationStatus; //values: success/ failed

    public Notification(String tenantId, String clientId, String contentType, String contentId, String contentUri, String contentCreated, String contentExpiration, String notificationSent, String notificationStatus) {
        this.tenantId = tenantId;
        this.clientId = clientId;
        this.contentType = contentType;
        this.contentId = contentId;
        this.contentUri = contentUri;
        this.contentCreated = contentCreated;
        this.contentExpiration = contentExpiration;
        this.notificationSent = notificationSent;
        this.notificationStatus = notificationStatus;
    }

    public String getTenantId() {
        return tenantId;
    }

    private void setTenantId(String tenantId) {
        this.tenantId = tenantId;
    }

    public String getClientId() {
        return clientId;
    }

    private void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public String getContentType() {
        return contentType;
    }

    private void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public String getContentId() {
        return contentId;
    }

    private void setContentId(String contentId) {
        this.contentId = contentId;
    }

    public String getContentUri() {
        return contentUri;
    }

    private void setContentUri(String contentUri) {
        this.contentUri = contentUri;
    }

    public String getContentCreated() {
        return contentCreated;
    }

    private void setContentCreated(String contentCreated) {
        this.contentCreated = contentCreated;
    }

    public String getContentExpiration() {
        return contentExpiration;
    }

    private void setContentExpiration(String contentExpiration) {
        this.contentExpiration = contentExpiration;
    }

    public String getNotificationSent() {
        return notificationSent;
    }

    private void setNotificationSent(String notificationSent) {
        this.notificationSent = notificationSent;
    }

    public String getNotificationStatus() {
        return notificationStatus;
    }

    private void setNotificationStatus(String notificationStatus) {
        this.notificationStatus = notificationStatus;
    }
}