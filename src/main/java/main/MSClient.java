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
import java.io.InputStream;
import java.util.ArrayList;

public class MSClient {

    private static final Logger logger = LoggerFactory.getLogger(MSClient.class.getName());
    private final String configFile;

    public MSClient(String configFile) {
        this.configFile = configFile;
    }

    public void start() {
        MSGraphConfiguration configuration = loadMSGraphConfig(configFile);
        if (configuration == null) {
            logger.error("error loading configuration, yaml config file malformed or missing required fields");
            return;
        }
        MSGraphRequestExecutor client = null;
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

    private MSGraphConfiguration loadMSGraphConfig(String yamlFile) {
        Yaml yaml = new Yaml(new Constructor(MSGraphConfiguration.class));
        InputStream inputStream = this.getClass()
                .getClassLoader()
                .getResourceAsStream(yamlFile);
        return (MSGraphConfiguration) yaml.load(inputStream);
    }
}
