package main;

import api.ManagementActivityApiWrapper;
import api.Office365HttpRequests;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.atomic.AtomicReference;

public class Main {
    public static void main(String[] args) throws Exception {
//        ManagementActivityApiWrapper managementActivity = new ManagementActivityApiWrapper();
//        managementActivity.startSubscription();
//        String startTime = managementActivity.getStartTime();
//        AtomicReference<Date> from = new AtomicReference<>(new SimpleDateFormat("YYYY-MM-DDTHH:MM:SS").parse(startTime));
        Office365HttpRequests client = new Office365HttpRequests(
                "015fe495-52fc-4a7c-8332-a4db3c331def",
                "4ecccc8c-8cf5-4718-a14a-cb089f64468a",
                "LYaMDS56oTe=DNpIHuhFl*4deF:*GzP8");
        int interval = 30*60*1000;

        Runnable runnable = () -> {
            client.listSubscriptions();
            try {
                Thread.sleep(interval);
            } catch (InterruptedException e) {
                //FIXME add solution here
            }
        };
        Thread thread = new Thread(runnable);
        thread.start();

    }
}