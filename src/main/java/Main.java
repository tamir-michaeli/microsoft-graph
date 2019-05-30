public class Main {
    public static void main(String[] args) {
        try {
            int x = 0;
            while (x != 1) {//x!=end // should add a statement to stop the loop (the user enter ctrl + c)

                Office365Apis.getInput();
                Office365Apis.createConnectionToAzurePortal();
                Office365Apis.createConnectionToLogzIo();
                Office365Apis.startSubscription();
                Thread.sleep(Office365Apis.interval);
            }

            Office365Apis.stopSubscription();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


}
