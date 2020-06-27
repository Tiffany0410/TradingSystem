package bookTradeSystem;


import java.util.Scanner;

public class DisplaySystem {
    private FileReader menuOption;
    private FileReader notification;
    private FileReader length;
    private int option;

    /**
     * print the put in sentence to the screen
     * convinces to change from txt to window
     * @param str
     */
    public void printOut(String str){
        System.out.println(str);
    }


    /**
     * Print menu and get the option user put in
     * @param fileName
     * @return option
     */

    public int getMenuAnswer(String fileName) {
        boolean condition = true;

        // get valid option user typed in
        while (condition) {

            Scanner sc = new Scanner(System.in);

            this.printOut("Please enter the number of your option.");
            printMenu(fileName);  // print all options

            try {
                option = sc.nextInt();  // get number user typed in

                // check the number user typed in
                if (0 <=option && option < getMenuLength(fileName)){
                    condition = false;
                }
                else{
                    this.printOut("Please enter a number provide in the menu");
                }
            } catch (Exception InputMismatchException) {
                // If user type in not int
                this.printOut("Please enter a number.");

            }

        }
        return option;
    }

    /**
     * Get the number of options
     * @param fileName
     * @return
     */

    public int getMenuLength(String fileName){
        return length.getlength(fileName);
    }


    /**
     * Show the menu on the screen by taking the file name and show the options in the file
     * to the screen
     * In the future, this will change to another UI not just text
     * @param fileName
     */

    public void printMenu(String fileName){
        this.printOut(menuOption.getContent(fileName));
    }

    /**
     * Show the notification for this user
     * Get the username and looking the notification for this user in the file
     * In the future, this will change to another UI not just text
     * @param userName
     */

    public void printNotification(String userName){
        this.printOut(notification.getContent(userName));
    }

    /**
     * Get the username user put in
     * @return username
     */

    public String getUsername(){
        String userName;
        Scanner sc = new Scanner(System.in);
        this.printOut("Please enter user name");
        userName = sc.nextLine();  // get username user typed in
        return userName;

    }

    /**
     * Get the password user put in
     * @return password
     */

    public String getPassword(){
        String password;
        Scanner sc = new Scanner(System.in);
        this.printOut("Please enter password");
        password = sc.nextLine();  // get password user typed in
        return password;
    }

    public void failLogin(){
        this.printOut("Wrong username or password, please check again.");
    }


}
