package objects;

public class MSGraphConfiguration {


    private AzureADClient azureADClient;
    private LogzioJavaSenderParams senderParams;

    public MSGraphConfiguration() {
    }

    public void setAzureADClient(AzureADClient azureADClient) {
        this.azureADClient = azureADClient;
    }

    public AzureADClient getAzureADClient() {
        return azureADClient;
    }

    public LogzioJavaSenderParams getSenderParams() {
        return senderParams;
    }

    public void setSenderParams(LogzioJavaSenderParams senderParams) {
        this.senderParams = senderParams;
    }
}
