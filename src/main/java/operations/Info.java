package operations;

public class Info {
    private String id;
    private int type;

    public Info(String id, int type) {
        this.id = id;
        this.type = type;
    }

    public String getId() {
        return id;
    }

    private void setId(String id) {
        this.id = id;
    }

    public int getType() {
        return type;
    }

    private void setType(int type) {
        this.type = type;
    }
}