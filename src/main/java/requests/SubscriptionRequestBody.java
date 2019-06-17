package requests;

import operations.Webhook;

public class SubscriptionRequestBody {
    private Webhook webhook;

    public SubscriptionRequestBody(Webhook webhook) {
        this.webhook = webhook;
    }

    public Webhook getWebhook() {
        return webhook;
    }

    public void setWebhook(Webhook webhook) {
        this.webhook = webhook;
    }
}
