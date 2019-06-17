package main;

import api.ManagementActivityApiWrapper;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.atomic.AtomicReference;

public class Main {
    public static void main(String[] args) throws Exception {
        ManagementActivityApiWrapper managementActivity = new ManagementActivityApiWrapper();
        managementActivity.startSubscription();
        String startTime = managementActivity.getStartTime();
        AtomicReference<Date> from = new AtomicReference<>(new SimpleDateFormat("YYYY-MM-DDTHH:MM:SS").parse(startTime));
        int interval = managementActivity.getInterval();

        Runnable runnable = () -> {
            Date to = Date.from(from.get().toInstant().plusSeconds(interval));
            managementActivity.listAvailableContent(from.get(), to);
            from.set(to);
            try {
                Thread.sleep(interval);
            } catch (InterruptedException e) {
                //FIXME add solution here
            }
        };
        Thread thread = new Thread(runnable);
        thread.start();

        managementActivity.stopSubscription();
    }
}