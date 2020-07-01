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
    public String readFromMenu(String filePath) throws FileNotFoundException {
        File new_file = new File(filePath);
        if (new_file.exists()) {
            // FileInputStream can be used for reading raw bytes, like an image.
            Scanner scanner = new Scanner(new FileInputStream(filePath));
            StringBuilder record = null;

            while (scanner.hasNextLine()) {
                record.append(scanner.nextLine());
                record.append("\n");
            }
            scanner.close();
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


    /**
     * Return the arraylist includes all Users from the file at path filePath.
     *
     * @param filePath the path of the data file
     */
    public static ArrayList<User> readUsersFromFile(String filePath) throws IOException, ClassNotFoundException {

        File new_file = new File(filePath);
        if (new_file.exists()) {
            //Create a new empty hashmap which key is the id and value is the object
            Map<String, User> userMap = new HashMap<>();
            InputStream file = new FileInputStream(filePath);
            InputStream buffer = new BufferedInputStream(file);
            ObjectInput input = new ObjectInputStream(buffer);

            // deserialize the Map
            userMap = (Map<String, User>) input.readObject();
            input.close();
            //Convert the Map to Arraylist
            ArrayList<User> listUser = new ArrayList<>(userMap.values());
            return listUser;
        } else {
            ArrayList<User> listUser = new ArrayList<>();
            return listUser;
        }
    }


    /**
     * Return the arraylist of all AdminUsers from the file at path filePath.
     *
     * @param filePath the path of the data file
     */
    public static ArrayList<AdminUser> readAdminUsersFromFile(String filePath) throws IOException, ClassNotFoundException {

        File new_file = new File(filePath);
        if (new_file.exists()) {
            //Create a new empty hashmap which key is the id and value is the object
            Map<String, AdminUser> adminMap = new HashMap<>();
            InputStream file = new FileInputStream(filePath);
            InputStream buffer = new BufferedInputStream(file);
            ObjectInput input = new ObjectInputStream(buffer);

            // deserialize the Map
            adminMap = (Map<String, AdminUser>) input.readObject();
            input.close();
            //Convert the Map to Arraylist
            ArrayList<AdminUser> listAdminUser = new ArrayList<>(adminMap.values());
            return listAdminUser;
        } else {
            ArrayList<AdminUser> listAdminUser = new ArrayList<>();
            return listAdminUser;
        }
    }


    /**
     * Return the list of all Meeting from the file at path filePath.
     *
     * @param filePath the path of the data file
     */
    public static List<Meeting> readMeetingsFromFile(String filePath) throws IOException, ClassNotFoundException {

        File new_file = new File(filePath);
        if (new_file.exists()) {
            //Create a new empty hashmap which key is the id and value is the object
            Map<String, Meeting> meetingMap = new HashMap<>();
            InputStream file = new FileInputStream(filePath);
            InputStream buffer = new BufferedInputStream(file);
            ObjectInput input = new ObjectInputStream(buffer);

            // deserialize the Map
            meetingMap = (Map<String, Meeting>) input.readObject();
            input.close();
            //Convert the Map to list
            List<Meeting> listMeeting = new ArrayList<>(meetingMap.values());
            return listMeeting;
        } else {
            ArrayList<Meeting> listMeeting = new ArrayList<>();
            return listMeeting;
        }
    }


    /**
     * Return the list of all Trade from the file at path filePath.
     *
     * @param filePath the path of the data file
     */
    public static List<Trade> readTradesFromFile(String filePath) throws IOException, ClassNotFoundException {

        File new_file = new File(filePath);
        if (new_file.exists()) {
            //Create a new empty hashmap which key is the id and value is the object
            Map<String, Trade> tradeMap = new HashMap<>();
            InputStream file = new FileInputStream(filePath);
            InputStream buffer = new BufferedInputStream(file);
            ObjectInput input = new ObjectInputStream(buffer);

            // deserialize the Map
            tradeMap = (Map<String, Trade>) input.readObject();
            input.close();
            //Convert the Map to list
            List<Trade> listTrade = new ArrayList<>(tradeMap.values());
            return listTrade;
        } else {
            ArrayList<Trade> listTrade = new ArrayList<>();
            return listTrade;
        }
    }


    /**
     * Return the all Items in the map of item ids to Items from the file at path filePath.
     *
     * @param filePath the path of the data file
     */
    public Map<String, Item> readItemsFromFile(String filePath) throws IOException, ClassNotFoundException {

        File new_file = new File(filePath);
        if (new_file.exists()) {
            //Create a new empty hashmap which key is the id and value is the object
            Map<String, Item> itemMap = new HashMap<>();
            InputStream file = new FileInputStream(filePath);
            InputStream buffer = new BufferedInputStream(file);
            ObjectInput input = new ObjectInputStream(buffer);

            // deserialize the Map
            itemMap = (Map<String, Item>) input.readObject();
            input.close();
            return itemMap;
        }

        else {throw new FileNotFoundException();}
    }


    /**
     * Writes the Users to file at filePath.
     *
     * @param filePath the file to write the records to
     * @param listUser the Arraylist of Users
     * @throws IOException all possible input/output errors
     */
    public static void saveUsersToFile(ArrayList<User> listUser, String filePath) throws IOException {
        //If the file does not exist, throws FileNotFoundException
        File new_file = new File(filePath);
        if (!new_file.exists()) {
           throw new FileNotFoundException();
        }

        OutputStream file = new FileOutputStream(filePath);
        OutputStream buffer = new BufferedOutputStream(file);
        ObjectOutput output = new ObjectOutputStream(buffer);

        //Convert the arraylist of user to the map which mapping of user ids to Users
        Map<String, User> userMap= new HashMap<>();
        for (User user: listUser) {
            userMap.put(Integer.toString(user.getId()), user);
        }
        // serialize the Map
        output.writeObject(userMap);
        output.close();

    }

    /**
     * Writes the AdminUsers to file at filePath.
     *
     * @param filePath the file to write the records to
     * @param listAdminUser the Arraylist of AdminUsers
     * @throws IOException all possible input/output errors
     */
    public static void saveAdminUsersToFile(ArrayList<AdminUser> listAdminUser, String filePath) throws IOException {
        //If the file does not exist, throws FileNotFoundException
        File new_file = new File(filePath);
        if (!new_file.exists()) {
            throw new FileNotFoundException();
        }

        OutputStream file = new FileOutputStream(filePath);
        OutputStream buffer = new BufferedOutputStream(file);
        ObjectOutput output = new ObjectOutputStream(buffer);

        //Convert the Arraylist of admin user to the map which mapping of admin user ids to AdminUsers
        Map<String, AdminUser> userMap= new HashMap<>();
        for (AdminUser adminUser: listAdminUser) {
            userMap.put(Integer.toString(adminUser.getId()), adminUser);
        }
        // serialize the Map
        output.writeObject(userMap);
        output.close();
    }


    /**
     * Writes the Meeting to file at filePath.
     *
     * @param filePath the file to write the records to
     * @param listMeeting the Arraylist of Meetings
     * @throws IOException all possible input/output errors
     */
    public static void saveMeetingsToFile(List<Meeting> listMeeting, String filePath) throws IOException {
        //If the file does not exist, throws FileNotFoundException
        File new_file = new File(filePath);
        if (!new_file.exists()) {
            throw new FileNotFoundException();
        }

        OutputStream file = new FileOutputStream(filePath);
        OutputStream buffer = new BufferedOutputStream(file);
        ObjectOutput output = new ObjectOutputStream(buffer);

        //Convert the list of meeting to the map which mapping of meeting ids to Meetings
        Map<String, Meeting> meetingMap= new HashMap<>();
        for (Meeting meeting: listMeeting) {
            meetingMap.put(Integer.toString(meeting.getMeetingNum()), meeting);
        }
        // serialize the Map
        output.writeObject(meetingMap);
        output.close();
    }


    /**
     * Writes the Trade to file at filePath.
     *
     * @param filePath the file to write the records to
     * @param listTrade the Arraylist of Trades
     * @throws IOException all possible input/output errors
     */
    public static void saveTradesToFile(List<Trade> listTrade, String filePath) throws IOException {
        //If the file does not exist, throws FileNotFoundException
        File new_file = new File(filePath);
        if (!new_file.exists()) {
            throw new FileNotFoundException();
        }

        OutputStream file = new FileOutputStream(filePath);
        OutputStream buffer = new BufferedOutputStream(file);
        ObjectOutput output = new ObjectOutputStream(buffer);

        //Convert the list of trade to the map which mapping of trade ids to Trades
        Map<String, Trade> meetingMap= new HashMap<>();
        for (Trade trade: listTrade) {
            meetingMap.put(Integer.toString(trade.getIds().get(1)), trade);
        }
        // serialize the Map
        output.writeObject(meetingMap);
        output.close();
    }



    /**
     * Writes the objects to file at filePath.
     *
     * @param filePath the file to write the records to
     * @param itemMap the mapping of item ids to Items
     * @throws IOException all possible input/output errors
     */
    public void saveItemsToFile(Map<String, Item> itemMap, String filePath) throws IOException {
        //If the file does not exist, throws FileNotFoundException
        File new_file = new File(filePath);
        if (!new_file.exists()) {
            throw new FileNotFoundException();
        }

        OutputStream file = new FileOutputStream(filePath);
        OutputStream buffer = new BufferedOutputStream(file);
        ObjectOutput output = new ObjectOutputStream(buffer);

        // serialize the Map
        output.writeObject(itemMap);
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
                record = scanner.nextLine().split(".");
                int TempNumber = Integer.valueOf(record[0]);
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

