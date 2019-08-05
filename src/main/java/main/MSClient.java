package main;

import api.MSGraphRequestExecutor;
import api.Office365Apis;
import objects.JsonArrayRequest;
import objects.MSGraphConfiguration;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.constructor.Constructor;

import javax.naming.AuthenticationException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;

import static io.logz.sender.com.google.common.base.Preconditions.checkNotNull;
import static java.util.Arrays.asList;

public class MSClient {

    private static final Logger logger = Logger.getLogger(MSClient.class);
    private MSGraphConfiguration configuration;

    public MSClient(String configFile) throws FileNotFoundException {
        configuration = loadMSGraphConfig(configFile);
        org.apache.log4j.Logger root = org.apache.log4j.Logger.getRootLogger();
        root.setLevel(Level.toLevel(configuration.getLogLevel()));
    }

    public void start() {
        if (getConfiguration() == null) {
            return;
        }
        MSGraphRequestExecutor executor;
        try {
            executor = new MSGraphRequestExecutor(configuration.getAzureADClient());
        } catch (AuthenticationException e) {
            logger.error(e.getMessage(), e);
            return;
        }
        Office365Apis officeApis = new Office365Apis(executor);

        ArrayList<JsonArrayRequest> requests = new ArrayList<>();
        requests.addAll(asList(officeApis::getSignIns,officeApis::getDirectoryAudits));
        FetchSendManager manager = new FetchSendManager(requests, configuration.getSenderParams(), configuration.getAzureADClient().getPullInterval());
        manager.start();
    }

    public MSGraphConfiguration getConfiguration() {
        return this.configuration;
    }

    private MSGraphConfiguration loadMSGraphConfig(String yamlFile) throws FileNotFoundException {
        Yaml yaml = new Yaml(new Constructor(MSGraphConfiguration.class));
        InputStream inputStream = new FileInputStream(new File(yamlFile));
        MSGraphConfiguration config = yaml.load(inputStream);

        try {
            checkNotNull(config.getSenderParams().getAccountToken());
            checkNotNull(config.getAzureADClient().getTenantId());
            checkNotNull(config.getAzureADClient().getClientId());
            checkNotNull(config.getAzureADClient().getClientSecret());
        } catch (NullPointerException e) {
            throw new NullPointerException("The following parameters are mandatory: \n" +
                        "azureADClient.tenantId, \n" +
                        "azureADClient.clientId,\n" +
                        "azureADClient.clientSecret,\n" +
                        "logzioSenderParameters.accountToken");
        }
        return config;
    }
}
