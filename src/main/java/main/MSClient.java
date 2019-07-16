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
        MSGraphHttpRequests client = new MSGraphHttpRequests(
                "c96a62e5-1e49-4187-b394-08b694e8bb0d",
                "c514bf88-cd22-4196-a955-185e663d59a4",
                "D+z+3cVlL+:bgxhXa02F799QujC0-YhI");
        int interval = 10*60*1000;

        ArrayList<JsonArrayRequest> requests = new ArrayList<>();
        requests.add(client::getSignIns);
//        FetchSendManager man = new FetchSendManager((JsonArrayRequest[]) requests.toArray(), configuration.getLogzioSenderParameters());


        Runnable runnable = () -> {
            while (true) {
                try {
                    for (JsonArrayRequest request : requests) {
                        System.out.println(request.getData(0,0));
                    }
                    Thread.sleep(interval);

//                client.listSubscriptions();
                } catch (InterruptedException e) {
                    //FIXME add solution here
                }
            }
        };
        Thread thread = new Thread(runnable);
        thread.start();
    }

    private MSGraphConfiguration loadMSGraphConfig(String yamlFile) {
        Yaml yaml = new Yaml(new Constructor(MSGraphConfiguration.class));
        InputStream inputStream = this.getClass()
                .getClassLoader()
                .getResourceAsStream(yamlFile);
        return (MSGraphConfiguration) yaml.load(inputStream);
    }
}
