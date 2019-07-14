package main;

import api.Office365HttpRequests;
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
        Office365HttpRequests client = new Office365HttpRequests(
                "015fe495-52fc-4a7c-8332-a4db3c331def",
                "4ecccc8c-8cf5-4718-a14a-cb089f64468a",
                "LYaMDS56oTe=DNpIHuhFl*4deF:*GzP8");
        int interval = 10*60*1000;

        ArrayList<JsonArrayRequest> jsr = new ArrayList<>();
        jsr.add(client::sampleRequest);
//        FetchSendManager man = new FetchSendManager()

        Runnable runnable = () -> {
            while (true) {
                try {
                    Thread.sleep(interval);
//                client.listSubscriptions();
                    client.getsh();
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
