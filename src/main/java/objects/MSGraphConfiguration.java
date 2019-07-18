package objects;

public class MSGraphConfiguration {


    private AzureADClient azureADClient;
    private LogzioJavaSenderParams logzioSenderParameters;

    public MSGraphConfiguration() {
    }

    public void setAzureADClient(AzureADClient azureADClient) {
        this.azureADClient = azureADClient;
    }

    public AzureADClient getAzureADClient() {
        return azureADClient;
    }

    public LogzioJavaSenderParams getLogzioSenderParameters() {
        return logzioSenderParameters;
    }

    public void setLogzioSenderParameters(LogzioJavaSenderParams logzioSenderParameters) {
        this.logzioSenderParameters = logzioSenderParameters;
    }
}
