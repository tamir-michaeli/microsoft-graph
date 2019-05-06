import java.util.Date;

public class SubscriptionRequest {
    private ContentType contentType;
    private String publisherIdentifier;
    private RequestBody requestBody;
    private Date startTime;
    private Date endTime;

    public SubscriptionRequest(ContentType contentType, String publisherIdentifier, RequestBody requestBody, Date startTime, Date endTime) {
        this.contentType = contentType;
        this.publisherIdentifier = publisherIdentifier;
        this.requestBody = requestBody;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public ContentType getContentType() {
        return contentType;
    }

    public String getPublisherIdentifier() {
        return publisherIdentifier;
    }

    public RequestBody getRequestBody() {
        return requestBody;
    }

    public Date getEndTime() {
        return endTime;
    }

    public Date getStartTime() {
        return startTime;
    }


    public class RequestBody{

    }
}
