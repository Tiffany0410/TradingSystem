package bookTradeSystem;


import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class DisplaySystem {
    private FilesReaderWriter fileReader;
    private int option;

    public DisplaySystem() throws IOException, ClassNotFoundException {
        fileReader = new FilesReaderWriter();
    }

    /**
     * print the put in sentence to the screen
     * convinces to change from txt to window
     * @param str the string need to be print out
     */
    public void printOut(String str){
        System.out.println(str);
    }


    /**
     * Print menu and get the option user put in
     * @param filePath the path of file need to be read
     * @return option
     */

    public int getMenuAnswer(String filePath) throws IOException {

        // get valid option user typed in
        while (true) {

            Scanner sc = new Scanner(System.in);

            printMenu(filePath);  // print all options
            this.printOut("Please enter the number of your option:");

            String typeIn = sc.nextLine();  // get number user typed in

            try {
                option = Integer.parseInt(typeIn);

                // check the number user typed in
                int menuLength = getMenuLength(filePath);
                if (0 <=option && option <= menuLength){
                    break;
                }
                else{
                    this.printOut("Please enter a number provide in the menu");
                }
            } catch (Exception InputMismatchException) {
                // If user type in not int
                this.printOut("Please enter a number!");

            }

        }
        return option;
    }

    /**
     * Get the number of options
     * @param filePath the path of file need to be read
     * @return int the length of menu
     */

    private int getMenuLength(String filePath) throws FileNotFoundException {
        return fileReader.MenuLength(filePath);
    }


    /**
     * Show the menu on the screen by taking the file name and show the options in the file
     * to the screen
     * In the future, this will change to another UI not just text
     * @param filePath the path of file need to be read
     */

    private void printMenu(String filePath) throws IOException {
        this.printOut(fileReader.readFromMenu(filePath));
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

    /**
     * Get email address user put in
     */

    public String getEmail(){
        String emailAddress;
        Scanner sc = new Scanner(System.in);
        this.printOut("Please enter your email address");
        emailAddress = sc.nextLine();  // get email address user typed in
        return emailAddress;
    }


    /**
     * When log in fail shows this
     */
    public void failLogin(){
        this.printOut("Wrong username or password, please check again.");
    }


    /**
     * Print out the result of action with boolean type
     * @param result the result of user's operation
     */
    public void printResult(boolean result){
        if (result){
            printOut("Success");
        }else{
            printOut("Fail");
        }
        printOut("\n");
    }

    /**
     * print out the result of action with object type
     * @param obj the list of objects need to be printed
     */
    public void printResult(ArrayList<Object> obj) {
        int count = 1;
        for (Object o : obj) {
            // if o is not a string[]
            if (!(o instanceof String[])) {
                this.printOut("#" + count + ". " + o.toString() + "\n");
            }
            // if o is a string[]
            else {
                String[] strings = (String[])o;
                this.printOut("#" + count + ". " + "\n" + "Username: " + strings[0]);
                this.printOut("Message: " + strings[1] + "\n");
            }
            count++;
        }
    }

    /**
     * print out the result of action with string and boolean type
     * @param str the sentence want to present
     * @param result the result of the action
     */

    public void printResult(String str, boolean result){
        if (result){
            printOut(str + " is sent successfully");
            printOut("\n");
        }else{
            printOut(str + " fails to be sent");
            printOut("\n");
        }
    }
}
