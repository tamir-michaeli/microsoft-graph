package responses;

import operations.Webhook;

public class Subscription {
    private String contentType;
    private String status; //its values enabled/disabled
    private Webhook webhook;

    public Subscription(String contentType, String status, Webhook webhook) {
        this.contentType = contentType;
        this.status = status;
        this.webhook = webhook;
    }

    public String getContentType() {
        return contentType;
    }

    private void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public String getStatus() {
        return status;
    }

    private void setStatus(String status) {
        this.status = status;
    }

    public Webhook getWebhook() {
        return webhook;
    }

    private void setWebhook(Webhook webhook) {
        this.webhook = webhook;
    }
}