package bookTradeSystem;


public class DisplaySystem {
    FileReader menuOption;
    FileReader notification;

    /**
     * Show the menu on the screen by taking the file name and show the options in the file
     * to the screen
     * In the feture, this will change to another UI not just text
     * @param fileName
     */

    public void printMenu(String fileName){
        System.out.println(menuOption.getMenu(fileName));
    }

    /**
     * Show the notification for this user
     * Get the username and looking the notification for this user in the file
     * In the feture, this will change to another UI not just text
     * @param userName
     */

    public void printNotification(String userName){
        System.out.println(notification.getNotification(userName));
    }

}
