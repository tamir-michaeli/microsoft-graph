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

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public String getContentId() {
        return contentId;
    }

    public void setContentId(String contentId) {
        this.contentId = contentId;
    }

    public String getContentUri() {
        return contentUri;
    }

    public void setContentUri(String contentUri) {
        this.contentUri = contentUri;
    }

    public String getContentCreated() {
        return contentCreated;
    }

    public void setContentCreated(String contentCreated) {
        this.contentCreated = contentCreated;
    }

    public String getContentExpiration() {
        return contentExpiration;
    }

    public void setContentExpiration(String contentExpiration) {
        this.contentExpiration = contentExpiration;
    }
}
