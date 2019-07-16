package main;

public class Main {
    public static void main(String[] args) throws Exception {
//        ManagementActivityApiWrapper managementActivity = new ManagementActivityApiWrapper();
//        managementActivity.startSubscription();
//        String startTime = managementActivity.getStartTime();
//        AtomicReference<Date> from = new AtomicReference<>(new SimpleDateFormat("YYYY-MM-DDTHH:MM:SS").parse(startTime));
        //todo if args > 0 ...
        MSClient msClient = new MSClient(args[0]);
        msClient.start();
    }


}