package responses;

public class AvailableContent {
    private String contentType;
    private String contentId;
    private String contentUri;
    private String contentCreated;
    private String contentExpiration;

    public AvailableContent(String contentType, String contentId, String contentUri, String contentCreated, String contentExpiration) {
        this.contentType = contentType;
        this.contentId = contentId;
        this.contentUri = contentUri;
        this.contentCreated = contentCreated;
        this.contentExpiration = contentExpiration;
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
}
