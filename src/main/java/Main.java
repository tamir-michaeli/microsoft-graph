public class Main {

    public static void main(String[] args) {
        Office365Apis.getInput();
        try {
            Office365Apis.createConnectionToAzurePortal();
            Office365Apis.createConnectionToLogzIo();
            Office365Apis.startSubscription();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


}
