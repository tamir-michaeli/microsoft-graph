package operations;

public class Webhook {
    private String status;
    private String address;
    private String authId;
    private String expiration;

    public Webhook(String status, String address, String authId, String expiration) {
        this.status = status;
        this.address = address;
        this.authId = authId;
        this.expiration = expiration;
    }

    public String getAuthId() {
        return authId;
    }

    public void setAuthId(String authId) {
        this.authId = authId;
    }

    public String getExpiration() {
        return expiration;
    }

    public void setExpiration(String expiration) {
        this.expiration = expiration;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}