package gateway;

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
     */
    public FilesReaderWriter() {
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
     * Read the Object from the file at path filePath.
     *
     * @param filePath    the path of the data file
     * @return return the manager which read from file
     * @throws IOException all possible input/output errors
     */
    public Object readManagerFromFile(String filePath)
            throws IOException, ClassNotFoundException {
        //check if the file at filePath exist or not
        File new_file = new File(filePath);
        if (new_file.exists()) { return ser_reader_helper(filePath);
        } else { throw new FileNotFoundException(); }
    }


    /**
     * Writes the various Manager to file at filePath.
     *
     * @param filePath the file to write the records to
     * @param manager the different Manager in the Object type
     * @throws IOException all possible input/output errors
     *
     * @serial serialize Manager
     */
    public void saveManagerToFile(Object manager, String filePath) throws IOException {
        //If the file does not exist, throws FileNotFoundException
        File new_file = new File(filePath);
        if (!new_file.exists()) { throw new FileNotFoundException(); }
        // Serialize Manager into the file at filePath
        OutputStream file = new FileOutputStream(filePath);
        OutputStream buffer = new BufferedOutputStream(file);
        ObjectOutput output = new ObjectOutputStream(buffer);

        // serialize the object
        output.writeObject(manager);
        output.close();
    }

    /**
     * Check the file at filePath is empty or not.
     *
     * @param filePath the file to write the records to
     * @return True if file is empty and False if file is not empty
     * @throws IOException all possible input/output errors
     */
    public boolean check_file_empty_or_not(String filePath) throws IOException {
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

}