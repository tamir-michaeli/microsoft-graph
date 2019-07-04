package responses;

public class Subscription {
    private String contentType;
    private boolean enabled; //its values enabled/disabled

    public Subscription(String contentType, boolean enabled) {
        this.contentType = contentType;
        this.enabled = enabled;
    }

    public String getContentType() {
        return contentType;
    }

    private void setContentType(String contentType) {
        this.contentType = contentType;
    }


    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }
}