package responses;

public class ResourceFriendlyNames {
    private String id;
    private String name;

    public ResourceFriendlyNames(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public String getId() {
        return id;
    }

    private void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    private void setName(String name) {
        this.name = name;
    }
}