package enums;

public enum ContentType {
    AZURE_ACTIVE_DIRECTORY("Audit.AzureActiveDirectory"),
    EXCHANGE("Audit.Exchange"),
    SHARE_POINT("Audit.SharePoint"),
    GENERAL("Audit.General"),
    ALL("DLP.All");

    String value;

    ContentType(String val) {
        value = val;
    }

    public String getValue() {
        return value;
    }
}