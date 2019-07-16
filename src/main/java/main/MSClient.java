package main;

import api.MSGraphHttpRequests;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.constructor.Constructor;

import java.io.InputStream;
import java.util.ArrayList;

public class MSClient {

    private String configFile;

    public MSClient(String configFile) {
        this.configFile = configFile;
        start();
    }

    public void start() {
        MSGraphConfiguration configuration = loadMSGraphConfig(configFile);
        MSGraphHttpRequests client = new MSGraphHttpRequests(configuration.getAzureADClient());
        int interval = 10*60*1000;

        ArrayList<JsonArrayRequest> requests = new ArrayList<>();
        requests.add(client::getSignIns);
        FetchSendManager manager = new FetchSendManager(requests , configuration.getLogzioSenderParameters());
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
