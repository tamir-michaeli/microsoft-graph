package main;

import operations.AzureADClient;

public class MSGraphConfiguration {


    private AzureADClient azureADClient;
    private LogzioJavaSenderParams logzioSenderParameters;
    //todo: pull backwards

    public MSGraphConfiguration() {}

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
