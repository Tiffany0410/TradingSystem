package bookTradeSystem;

import java.io.*;
import java.util.*;

import bookTradeSystem.*;


/**
 * Manages the saving and loading of objects.
 */
public class FilesReaderWriter implements Serializable {

    /**
     * Constructor of the FilesReaderWriter
     *
     * @throws IOException all possible input/output errors
     * @throws ClassNotFoundException the specified class cannot be found
     */
    public FilesReaderWriter() throws ClassNotFoundException, IOException {}


    /**
     * Return the all menu in string from the file at path filePath.
     *
     * @param filePath the path of the data file
     * @throws FileNotFoundException if filePath is not a valid path
     */
    public String readFromMenu(String filePath) throws IOException {
        File new_file = new File(filePath);
        if (new_file.exists()) {
            BufferedReader br = new BufferedReader(new FileReader(filePath));
            String line = "";
            StringBuilder record = new StringBuilder();
            while ((line = br.readLine()) != null) {
                record.append(line);
                record.append("\n");
            }

            return record.toString();
        }
        else {
            throw new FileNotFoundException();
        }
    }


    /**
     * Write new user account info(including username, password, email) into files at filePath
     *
     * @param filePath the path of the data file
     * @param username the username of the new user
     * @param password the password of the new user
     * @param email the email address of the new user
     */
    public void saveUserInfoToCSVFile(String filePath, String username, String password, String email)
            throws FileNotFoundException {
        File new_file = new File(filePath);
        if (new_file.exists()) {
            PrintWriter writer = new PrintWriter(new File(filePath));

            StringBuilder sb = new StringBuilder();
            sb.append(username);
            sb.append(',');
            sb.append(password);
            sb.append(',');
            sb.append(email);
            sb.append('\n');

            writer.write(sb.toString());
            writer.close();
        }
        else {throw new FileNotFoundException();}
    }


    /**
     * Return a map read from file at filePath, which key is the username and value is associated password
     *
     * @param filePath the path of the data file
     * @throws FileNotFoundException if filePath is not a valid path
     */
    public Map<String, String> readUserInfoFromCSVFile(String filePath) throws FileNotFoundException {
        File new_file = new File(filePath);
        if (new_file.exists()) {
            // FileInputStream can be used for reading raw bytes, like an image.
            Scanner scanner = new Scanner(new FileInputStream(filePath));
            Map<String, String> users = new HashMap<>();
            String[] record;

            while (scanner.hasNextLine()) {
                record = scanner.nextLine().split(",");
                users.put(record[0], record[1]);
            }
            scanner.close();
            return users;
        } else {throw new FileNotFoundException();}
    }

    private static boolean helper_check_file_empty_or_not(String filePath) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader(filePath));
        if (br.readLine() == null) {
            return true;
        }
        else {return false;}
    }


    /**
     * Return the UserManager from the file at path filePath.
     *
     * @param filePath the path of the data file
     */
    public static UserManager readUserManagerFromFile(String filePath) throws IOException, ClassNotFoundException {
        //check if the file at filePath exist or not
        File new_file = new File(filePath);
        if (new_file.exists()) {
            //check if the file is empty or not, if empty, return an new UserManager
            if (helper_check_file_empty_or_not(filePath)) {
                return new UserManager();
            }

            InputStream file = new FileInputStream(filePath);
            InputStream buffer = new BufferedInputStream(file);
            ObjectInput input = new ObjectInputStream(buffer);

            // read the object from the file and cast the object to UserManager
            UserManager usermanager = (UserManager) input.readObject();
            input.close();
            return usermanager;
        } else {
            throw new FileNotFoundException();
        }
    }


    /**
     * Return the TradeManager from the file at path filePath.
     *
     * @param filePath the path of the data file
     */
    public static TradeManager readTradeManagerFromFile(String filePath) throws IOException, ClassNotFoundException {

        File new_file = new File(filePath);
        if (new_file.exists()) {
            //check if the file is empty or not, if empty, return an new TradeManager
            if (helper_check_file_empty_or_not(filePath)) {
                return new TradeManager();
            }

            InputStream file = new FileInputStream(filePath);
            InputStream buffer = new BufferedInputStream(file);
            ObjectInput input = new ObjectInputStream(buffer);

            // read the object from the file and cast the object to TradeManager
            TradeManager trademanager = (TradeManager) input.readObject();
            input.close();
            return trademanager;
        } else {
            throw new FileNotFoundException();
        }
    }


    /**
     * Return the MeetingManager from the file at path filePath.
     *
     * @param filePath the path of the data file
     */
    public static MeetingManager readMeetingManagerFromFile(String filePath) throws IOException, ClassNotFoundException {

        File new_file = new File(filePath);
        if (new_file.exists()) {
            //check if the file is empty or not, if empty, return an new TradeManager
            if (helper_check_file_empty_or_not(filePath)) {
                return new MeetingManager();
            }

            InputStream file = new FileInputStream(filePath);
            InputStream buffer = new BufferedInputStream(file);
            ObjectInput input = new ObjectInputStream(buffer);

            // read the object from the file and cast the object to TradeManager
            MeetingManager meetingmanager = (MeetingManager) input.readObject();
            input.close();
            return meetingmanager;
        } else {
            throw new FileNotFoundException();
        }
    }


    /**
     * Writes the UserManager to file at filePath.
     *
     * @param filePath the file to write the records to
     * @param userManager the UserManager
     * @throws IOException all possible input/output errors
     */
    public static void saveUserManagerToFile(UserManager userManager, String filePath) throws IOException {
        //If the file does not exist, throws FileNotFoundException
        File new_file = new File(filePath);
        if (!new_file.exists()) {
           throw new FileNotFoundException();
        }

        OutputStream file = new FileOutputStream(filePath);
        OutputStream buffer = new BufferedOutputStream(file);
        ObjectOutput output = new ObjectOutputStream(buffer);

        // serialize the UserManager
        output.writeObject(userManager);
        output.close();
    }

    /**
     * Writes the TradeManager to file at filePath.
     *
     * @param filePath the file to write the records to
     * @param tradeManager the TradeManager
     * @throws IOException all possible input/output errors
     */
    public static void saveTradeManagerToFile(TradeManager tradeManager, String filePath) throws IOException {
        //If the file does not exist, throws FileNotFoundException
        File new_file = new File(filePath);
        if (!new_file.exists()) {
            throw new FileNotFoundException();
        }

        OutputStream file = new FileOutputStream(filePath);
        OutputStream buffer = new BufferedOutputStream(file);
        ObjectOutput output = new ObjectOutputStream(buffer);

        // serialize the TradeManager
        output.writeObject(tradeManager);
        output.close();
    }

    /**
     * Writes the MeetingManager to file at filePath.
     *
     * @param filePath the file to write the records to
     * @param meetingManager the UserManager
     * @throws IOException all possible input/output errors
     */
    public static void saveMeetingManagerToFile(MeetingManager meetingManager, String filePath) throws IOException {
        //If the file does not exist, throws FileNotFoundException
        File new_file = new File(filePath);
        if (!new_file.exists()) {
            throw new FileNotFoundException();
        }

        OutputStream file = new FileOutputStream(filePath);
        OutputStream buffer = new BufferedOutputStream(file);
        ObjectOutput output = new ObjectOutputStream(buffer);

        // serialize the MeetingManager
        output.writeObject(meetingManager);
        output.close();
    }


    /**
     * Return the largest menu number of the menu file at filePath.
     *
     * @param filePath the file to write the records to
     * @throws FileNotFoundException if filePath is not a valid path
     */
    public int MenuLength(String filePath) throws FileNotFoundException {
        File file = new File(filePath);
        if (file.exists()) {
            // FileInputStream can be used for reading raw bytes, like an image.
            Scanner scanner = new Scanner(new FileInputStream(filePath));
            String[] record;
            int MenuNumber = 0;

            while (scanner.hasNextLine()) {
                record = scanner.nextLine().split("\\.");
                int TempNumber = Integer.parseInt(record[0]);
                if (TempNumber >= MenuNumber) {
                    MenuNumber = TempNumber;
                }
            }
            scanner.close();
            return MenuNumber;
        }

        else {
            throw new FileNotFoundException();
        }
    }


}

