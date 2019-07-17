package main;

import api.MSGraphRequestExecutor;
import api.Office365Apis;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.constructor.Constructor;

import java.io.InputStream;
import java.util.ArrayList;

public class MSClient {

    private String configFile;

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
        FetchSendManager manager = new FetchSendManager(requests , configuration.getLogzioSenderParameters(), configuration.getAzureADClient().getPullInterval());
        manager.start();

//        Runnable runnable = () -> {
//            while (true) {
//                try {
//                    for (JsonArrayRequest request : requests) {
//                        System.out.println(request.getData(0,0));
//                    }
//                    Thread.sleep(interval);
//
////                client.listSubscriptions();
//                } catch (InterruptedException e) {
//                    //FIXME add solution here
//                }
//            }
//        };
//        Thread thread = new Thread(runnable);
//        thread.start();
    }

    private MSGraphConfiguration loadMSGraphConfig(String yamlFile) {
        Yaml yaml = new Yaml(new Constructor(MSGraphConfiguration.class));
        InputStream inputStream = this.getClass()
                .getClassLoader()
                .getResourceAsStream(yamlFile);
        return (MSGraphConfiguration) yaml.load(inputStream);
    }
}
