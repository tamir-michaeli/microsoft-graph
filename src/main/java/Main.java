public class Main {
    public static void main(String[] args) throws Exception {
        Office365Apis office365Apis = new Office365Apis();
        office365Apis.startSubscription();
        Runnable runnable = () -> {
                try {
                    office365Apis.listAvailableContent();
                    Thread.sleep(office365Apis.getInterval());
                } catch (Exception e) {
                    e.printStackTrace();
                }
        };
        Thread thread = new Thread(runnable);
        thread.start();

        office365Apis.stopSubscription();
    }
}