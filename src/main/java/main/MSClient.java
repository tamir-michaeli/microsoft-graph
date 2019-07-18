package main;

import api.MSGraphRequestExecutor;
import api.Office365Apis;
import objects.JsonArrayRequest;
import objects.MSGraphConfiguration;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.constructor.Constructor;

import java.io.InputStream;
import java.util.ArrayList;

public class MSClient {

    private final String configFile;

    public MSClient(String configFile) {
        this.configFile = configFile;
    }

    public void start() {
        MSGraphConfiguration configuration = loadMSGraphConfig(configFile);
        MSGraphRequestExecutor client = new MSGraphRequestExecutor(configuration.getAzureADClient());
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
