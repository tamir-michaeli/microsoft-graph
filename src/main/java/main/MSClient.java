package main;

import api.MSGraphRequestExecutor;
import api.Office365Apis;
import objects.JsonArrayRequest;
import objects.MSGraphConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.constructor.Constructor;

import javax.naming.AuthenticationException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;

public class MSClient {

    private static final Logger logger = LoggerFactory.getLogger(MSClient.class.getName());
    private final String configFile;
    private MSGraphConfiguration configuration;

    public MSClient(String configFile) {
        this.configFile = configFile;
        loadConfig();
    }

    public void start() {

        if (getConfiguration() == null) {
            return;
        }
        MSGraphRequestExecutor client;
        try {
            client = new MSGraphRequestExecutor(configuration.getAzureADClient());
        } catch (AuthenticationException e) {
            logger.error(e.getMessage(), e);
            return;
        }
        Office365Apis officeApis = new Office365Apis(client);

        ArrayList<JsonArrayRequest> requests = new ArrayList<>();
        requests.add(officeApis::getSignIns);
        requests.add(officeApis::getDirectoryAudits);
        FetchSendManager manager = new FetchSendManager(requests, configuration.getLogzioSenderParameters(), configuration.getAzureADClient().getPullInterval());
        manager.start();
    }

    public MSGraphConfiguration getConfiguration() {
        return this.configuration;
    }

    private void loadConfig() {
        try {
            configuration = loadMSGraphConfig(configFile);
        } catch (FileNotFoundException e) {
            logger.error("error loading config file: {}", e.getMessage(), e);
            return;
        }
        if (configuration == null) {
            logger.error("error loading configuration, yaml config file malformed or missing required fields");
        }
    }

    private MSGraphConfiguration loadMSGraphConfig(String yamlFile) throws FileNotFoundException {
        Yaml yaml = new Yaml(new Constructor(MSGraphConfiguration.class));
        InputStream inputStream = new FileInputStream(new File(yamlFile));
        return (MSGraphConfiguration) yaml.load(inputStream);
    }
}
