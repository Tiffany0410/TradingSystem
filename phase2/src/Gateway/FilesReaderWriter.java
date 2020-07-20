package Gateway;

import Managers.MeetingManager.MeetingManager;
import Managers.TradeManager.TradeManager;
import Managers.UserManager.UserManager;
import Managers.FeedbackManager.*;
import Managers.ItemManager.ItemManager;


import java.io.*;
import java.util.*;


/**
 * Manages the saving and loading of objects.
 *
 * @author Chengle Yang
 * @version IntelliJ IDEA 2020.1
 *
 */
public class FilesReaderWriter implements Serializable {

    /**
     * Constructor of the FilesReaderWriter
     *
     * @throws IOException            all possible input/output errors
     * @throws ClassNotFoundException the specified class cannot be found
     */
    public FilesReaderWriter() throws ClassNotFoundException, IOException {
    }


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
        } else {
            throw new FileNotFoundException();
        }
    }

    /**
     * Return a List contain 4 integer read from file at filePath, where first number is maxNumTransactionAllowedAWeek,
     * second number is maxNumTransactionIncomplete, third number is numLendBeforeBorrow,
     * and last number is maxMeetingDateTimeEdits.
     *
     * @param filePath the path of the data file
     * @throws FileNotFoundException if filePath is not a valid path
     */
    public List<Integer> readThresholdValuesFromCSVFile(String filePath) throws FileNotFoundException {
        File new_file = new File(filePath);
        if (new_file.exists()) {
            // FileInputStream can be used for reading raw bytes, like an image.
            Scanner scanner = new Scanner(new FileInputStream(filePath));
            List<Integer> thresholdValues = new ArrayList<>();
            String[] record;

            while (scanner.hasNextLine()) {
                record = scanner.nextLine().split(":");
                String eachThresholdValue = Character.toString(record[1].charAt(0));
                thresholdValues.add(Integer.parseInt(eachThresholdValue));
            }
            scanner.close();
            return thresholdValues;
        } else {
            throw new FileNotFoundException();
        }
    }


    /**
     * Write new user account info(including username, password, email) into files at filePath
     *
     * @param filePath the path of the data file
     * @param username the username of the new user
     * @param password the password of the new user
     * @param email    the email address of the new user
     */
    public void saveUserInfoToCSVFile(String filePath, String username, String password, String email)
            throws IOException {
        File new_file = new File(filePath);
        if (new_file.exists()) {
            List<String> dataList = new ArrayList<>();
            BufferedReader br = new BufferedReader(new FileReader(filePath));
            String line = "";
            while ((line = br.readLine()) != null) {
                dataList.add(line + "\n");
            }

            PrintWriter writer = new PrintWriter(new File(filePath));

            StringBuilder sb = new StringBuilder();
            sb.append(username);
            sb.append(',');
            sb.append(password);
            sb.append(',');
            sb.append(email);
            sb.append('\n');
            dataList.add(sb.toString());

            //Write each User into csv file
            for (String singleUser : dataList) {
                writer.write(singleUser);
            }

            writer.close();
        } else {
            throw new FileNotFoundException();
        }
    }


    /**
     * Rewrite the file at filePath through replacing the value by the integer provided in given list
     *
     * @param thresholdValues the list 4 integer, where first number is maxNumTransactionAllowedAWeek,
     *                        second number is maxNumTransactionIncomplete, third number is numLendBeforeBorrow,
     *                        and last number is maxMeetingDateTimeEdits.
     * @param filePath        the path of the data file
     * @throws FileNotFoundException if filePath is not a valid path
     */
    public void saveThresholdValuesToCSVFile(List<Integer> thresholdValues, String filePath)
            throws IOException {
        File new_file = new File(filePath);
        if (new_file.exists()) {
            List<String> thresholdValuesList = new ArrayList<>();
            BufferedReader br = new BufferedReader(new FileReader(filePath));
            String line = "";
            while ((line = br.readLine()) != null) {
                thresholdValuesList.add(line + "\n");
            }

            //create a integer to track the location in thresholdValuesList and thresholdValues
            int location = thresholdValuesList.size() - 1;
            while (location >= 0) {
                StringBuilder sb = new StringBuilder(thresholdValuesList.get(location));
                sb.deleteCharAt(sb.length() - 1);
                sb.deleteCharAt(sb.length() - 1);
                sb.append(thresholdValues.get(location));
                if (location != thresholdValuesList.size() - 1) {
                    sb.append("\n");
                }
                thresholdValuesList.set(location, sb.toString());
                location--;
            }

            PrintWriter writer = new PrintWriter(new File(filePath));
            //Rewrite the csv file with new threshold value
            for (String singleThresholdValueString : thresholdValuesList) {
                writer.write(singleThresholdValueString);
            }

            writer.close();


        } else {
            throw new FileNotFoundException();
        }
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
        } else {
            throw new FileNotFoundException();
        }
    }

    /**
     * Read the various Managers from the file at path filePath.
     *
     * @param filePath    the path of the data file
     * @param managerType the type of the Manager
     * @return return the manager which read from file
     * @throws IOException all possible input/output errors
     */
    public Object readManagerFromFile(String filePath, String managerType)
            throws IOException, ClassNotFoundException {
        //check if the file at filePath exist or not
        File new_file = new File(filePath);
        if (new_file.exists()) {
            //check if the file is empty or not, if empty, return an new UserManager
            if (helper_check_file_empty_or_not(filePath)) {
                switch (managerType) {
                    case "userManager":
                        return new UserManager();
                    case "meetingManger":
                        return new MeetingManager();
                    case "tradeManager":
                        return new TradeManager();
                    case "itemManager":
                        return new ItemManager();
                    case "feedbackManager":
                        return new FeedbackManager();
                }
            }
            switch (managerType) {
                case "userManager":
                    // read the object from the file and cast the object to UserManager by helper function
                    UserManager usermanager = (UserManager) ser_reader_helper(filePath);
                    return usermanager;
                case "meetingManger":
                    // read the object from the file and cast the object to MeetingManager by helper function
                    MeetingManager meetingmanager = (MeetingManager) ser_reader_helper(filePath);
                    return meetingmanager;
                case "tradeManager":
                    // read the object from the file and cast the object to TradeManager by helper function
                    TradeManager trademanager = (TradeManager) ser_reader_helper(filePath);
                    return trademanager;
                case "itemManager":
                    // read the object from the file and cast the object to ItemManager by helper function
                    ItemManager itemManager = (ItemManager) ser_reader_helper(filePath);
                    return itemManager;
                case "feedbackManager":
                    // read the object from the file and cast the object to FeedbackManager by helper function
                    FeedbackManager feedbackManager = (FeedbackManager) ser_reader_helper(filePath);
                    return feedbackManager;
            }
        } else { throw new FileNotFoundException(); }
        return null;
    }


    /**
     * Writes the UserManager to file at filePath.
     *
     * @param filePath the file to write the records to
     * @param userManager the UserManager
     * @throws IOException all possible input/output errors
     *
     * @serial serialize UserManager
     */
    public void saveUserManagerToFile(UserManager userManager, String filePath) throws IOException {
        //If the file does not exist, throws FileNotFoundException
        File new_file = new File(filePath);
        if (!new_file.exists()) { throw new FileNotFoundException(); }
        // Serialize ItemManager into the file at filePath
        ser_writer_helper(userManager,filePath);
    }

    /**
     * Writes the TradeManager to file at filePath.
     *
     * @param filePath the file to write the records to
     * @param tradeManager the TradeManager
     * @throws IOException all possible input/output errors
     *
     * @serial serialize TradeManager
     */
    public void saveTradeManagerToFile(TradeManager tradeManager, String filePath) throws IOException {
        //If the file does not exist, throws FileNotFoundException
        File new_file = new File(filePath);
        if (!new_file.exists()) { throw new FileNotFoundException(); }
        // Serialize ItemManager into the file at filePath
        ser_writer_helper(tradeManager,filePath);
    }

    /**
     * Writes the MeetingManager to file at filePath.
     *
     * @param filePath the file to write the records to
     * @param meetingManager the UserManager
     * @throws IOException all possible input/output errors
     *
     * @serial serialize MeetingManager
     */
    public void saveMeetingManagerToFile(MeetingManager meetingManager, String filePath) throws IOException {
        // If the file does not exist, throws FileNotFoundException
        File new_file = new File(filePath);
        if (!new_file.exists()) { throw new FileNotFoundException(); }
        // Serialize MeetingManager into the file at filePath
        ser_writer_helper(meetingManager,filePath);
    }


    /**
     * Writes the UserManager to file at filePath.
     *
     * @param filePath the file to write the records to
     * @param feedbackManager the FeedbackManager
     * @throws IOException all possible input/output errors
     *
     * @serial serialize UserManager
     */
    public void saveFeedbackManagerToFile(FeedbackManager feedbackManager, String filePath) throws IOException {
        // If the file does not exist, throws FileNotFoundException
        File new_file = new File(filePath);
        if (!new_file.exists()) { throw new FileNotFoundException(); }

        // Serialize FeedbackManager into the file at filePath
        ser_writer_helper(feedbackManager,filePath);
    }

    /**
     * Writes the UserManager to file at filePath.
     *
     * @param filePath the file to write the records to
     * @param itemManager the ItemManager
     * @throws IOException all possible input/output errors
     *
     * @serial serialize UserManager
     */
    public void saveItemManagerToFile(ItemManager itemManager, String filePath) throws IOException {
        // If the file does not exist, throws FileNotFoundException
        File new_file = new File(filePath);
        if (!new_file.exists()) { throw new FileNotFoundException(); }
        // Serialize ItemManager into the file at filePath
        ser_writer_helper(itemManager,filePath);
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

    /**
     * Read the Object from file at filePath.
     *
     * @param filePath the file to write the records to
     * @return True if file is empty and False if file is not empty
     * @throws IOException all possible input/output errors
     */
    private boolean helper_check_file_empty_or_not(String filePath) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader(filePath));
        return br.readLine() == null;
    }

    /**
     * Read the Object from file at filePath.
     *
     * @param filePath the file to write the records to
     * @return Object which is read from file
     * @throws IOException all possible input/output errors
     *
     * @serial serialize Object
     */
    private Object ser_reader_helper(String filePath) throws IOException, ClassNotFoundException {

        InputStream file = new FileInputStream(filePath);
        InputStream buffer = new BufferedInputStream(file);
        ObjectInput input = new ObjectInputStream(buffer);

        Object output = input.readObject();
        input.close();
        return output;
    }

    /**
     * Writes the Object to file at filePath.
     *
     * @param filePath the file to write the records to
     * @param new_object the Object which to be serialized into file
     * @throws IOException all possible input/output errors
     *
     * @serial serialize Object
     */
    private void ser_writer_helper (Object new_object, String filePath) throws IOException {

        OutputStream file = new FileOutputStream(filePath);
        OutputStream buffer = new BufferedOutputStream(file);
        ObjectOutput output = new ObjectOutputStream(buffer);

        // serialize the object
        output.writeObject(new_object);
        output.close();
    }
}