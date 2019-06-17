package requests;

import java.util.Date;

public class SubscriptionRequest {
    private String path;
    private String token;
    private String contentType;
    private String publisherIdentifier;
    private Date startTime;
    private Date endTime;

    public SubscriptionRequest(String path, String token, String contentType, String publisherIdentifier, Date startTime, Date endTime) {
        this.path = path;
        this.token = token;
        this.contentType = contentType;
        this.publisherIdentifier = publisherIdentifier;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public SubscriptionRequest(String path, String token, String contentType, String publisherIdentifier) {
        this.path = path;
        this.token = token;
        this.contentType = contentType;
        this.publisherIdentifier = publisherIdentifier;
        this.startTime = null;
        this.endTime = null;
    }

    public String getContentType() {
        return contentType;
    }

    public String getPublisherIdentifier() {
        return publisherIdentifier;
    }

    public Date getEndTime() {
        return endTime;
    }

    public Date getStartTime() {
        return startTime;
    }

    public String getToken() {
        return token;
    }


    public String getPath() {
        return path;
    }
}
