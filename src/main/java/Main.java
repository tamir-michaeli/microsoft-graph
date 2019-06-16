public class Main {
    public static void main(String[] args) {
        Runnable runnable = () -> {
            while (true) {
                try {
                    Office365Apis.getInput();
                    Office365Apis.createConnectionToAzurePortal();
                    Office365Apis.startSubscription();
                    Office365Apis.listAvailableContent();
                    Thread.sleep(Office365Apis.interval);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };
        Thread thread = new Thread(runnable);
        thread.start();
    }
}