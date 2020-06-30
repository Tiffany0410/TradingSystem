package bookTradeSystem;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import bookTradeSystem.*;


/**
 * Manages the saving and loading of objects.
 */
public class FilesReaderWriter implements Serializable {

    /**
     * Constructor of the FilesReaderWriter
     *
     * @throws IOException
     * @throws ClassNotFoundException
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
        }
        else {throw new FileNotFoundException();}
    }


    /**
     * Return the all Items in the map of item ids to Items from the file at path filePath.
     *
     * @param filePath the path of the data file
     */
    public Map<String, Item> readItemsFromFile(String filePath) throws FileNotFoundException{

        File new_file = new File(filePath);
        if (new_file.exists()) {
            //Create a new empty hashmap which key is the id and value is the object
            Map<String, Item> itemMap = new HashMap<>();
            try {
                InputStream file = new FileInputStream(filePath);
                InputStream buffer = new BufferedInputStream(file);
                ObjectInput input = new ObjectInputStream(buffer);

                // deserialize the Map
                itemMap = (Map<String, Item>) input.readObject();
                input.close();
            } catch (IOException | ClassNotFoundException ex) {
                //TODO;
            }
            return itemMap;
        } else {
            throw new FileNotFoundException();
        }
    }


    /**
     * Return the all users in the map of user ids to Users from the file at path filePath.
     *
     * @param filePath the path of the data file
     */
    public Map<String, User> readUsersFromFile(String filePath) throws FileNotFoundException{

        File new_file = new File(filePath);
        if (new_file.exists()) {
            //Create a new empty hashmap which key is the id and value is the object
            Map<String, User> userMap = new HashMap<>();
            try {
                InputStream file = new FileInputStream(filePath);
                InputStream buffer = new BufferedInputStream(file);
                ObjectInput input = new ObjectInputStream(buffer);

                // deserialize the Map
                userMap = (Map<String, User>) input.readObject();
                input.close();
            } catch (IOException | ClassNotFoundException ex) {
                //TODO;
            }
            return userMap;
        } else {
            throw new FileNotFoundException();
        }
    }


    /**
     * Return the all AdminUser in the map of adminUser ids to AdminUsers from the file at path filePath.
     *
     * @param filePath the path of the data file
     */
    public Map<String, AdminUser> readAdminUserFromFile(String filePath) throws FileNotFoundException{

        File new_file = new File(filePath);
        if (new_file.exists()) {
            //Create a new empty hashmap which key is the id and value is the object
            Map<String, AdminUser> adminUserMap = new HashMap<>();
            try {
                InputStream file = new FileInputStream(filePath);
                InputStream buffer = new BufferedInputStream(file);
                ObjectInput input = new ObjectInputStream(buffer);

                // deserialize the Map
                adminUserMap = (Map<String, AdminUser>) input.readObject();
                input.close();
            } catch (IOException | ClassNotFoundException ex) {
                //TODO;
            }
            return adminUserMap;
        } else {
            throw new FileNotFoundException();
        }
    }


    /**
     * Writes the objects to file at filePath.
     *
     * @param filePath the file to write the records to
     * @param userMap the mapping of user ids to Users
     * @throws IOException
     */
    public void saveUserToFile(Map<String, User> userMap, String filePath) throws IOException {
        //If the file does not exist, create the file with the name of filePath first
        File new_file = new File(filePath);
        if (!new_file.exists()) {
            new_file.createNewFile();
        }

        OutputStream file = new FileOutputStream(filePath);
        OutputStream buffer = new BufferedOutputStream(file);
        ObjectOutput output = new ObjectOutputStream(buffer);

        // serialize the Map
        output.writeObject(userMap);
        output.close();
    }

    /**
     * Writes the objects to file at filePath.
     *
     * @param filePath the file to write the records to
     * @param adminUserMap the mapping of admin user ids to AdminUsers
     * @throws IOException
     */
    public void saveAdminUserToFile(Map<String, AdminUser> adminUserMap, String filePath) throws IOException {
        //If the file does not exist, create the file with the name of filePath first
        File new_file = new File(filePath);
        if (!new_file.exists()) {
            new_file.createNewFile();
        }

        OutputStream file = new FileOutputStream(filePath);
        OutputStream buffer = new BufferedOutputStream(file);
        ObjectOutput output = new ObjectOutputStream(buffer);

        // serialize the Map
        output.writeObject(adminUserMap);
        output.close();
    }

    /**
     * Writes the objects to file at filePath.
     *
     * @param filePath the file to write the records to
     * @param itemMap the mapping of item ids to Items
     * @throws IOException
     */
    public void saveItemsToFile(Map<String, Item> itemMap, String filePath) throws IOException {
        //If the file does not exist, create the file with the name of filePath first
        File new_file = new File(filePath);
        if (!new_file.exists()) {
            new_file.createNewFile();
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
     * @throws FileNotFoundException
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


    /**
     * Serialize the User into the file at filePath.
     *
     * @param filePath the file to write the records to
     * @param new_user the object need to be saved to the file at filePath
     */
    public void SerializeUser(String filePath, User new_user){
        try {
            Map<String, User> new_user_map = new HashMap<>();
            new_user_map.put(Integer.toString(new_user.getId()), new_user);
            saveUserToFile(new_user_map, filePath);
        } catch (IOException ex){
            //TODO:}

        }
    }


    /**
     * Serialize the AdminUser into the file at filePath.
     *
     * @param filePath the file to write the records to
     * @param new_adminUser the object need to be saved to the file at filePath
     */
    public void SerializeAdminUser(String filePath, AdminUser new_adminUser){
        try {
            Map<String, AdminUser> new_adminUser_map = new HashMap<>();
            new_adminUser_map.put(Integer.toString(new_adminUser.getId()), new_adminUser);
            saveAdminUserToFile(new_adminUser_map, filePath);
        } catch (IOException ex){
            //TODO:}

        }
    }

    /**
     * Serialize the arraylist of Items into the file at filePath.
     *
     * @param filePath the file to write the records to
     * @param itemArrayList list of Items need to be saved to the file at filePath
     */
    public void setItemListtoFile(String filePath, ArrayList<Item> itemArrayList) throws IOException {
        Map<String, Item> itemHashMap = new HashMap<String, Item>();
        for (Item item: itemArrayList) {
            itemHashMap.put(Integer.toString(item.getItemId()), item);
        }
        saveItemsToFile(itemHashMap, filePath);
    }


    /**
     * Serialize the Item into the file at filePath.
     *
     * @param filePath the file to write the records to
     * @param new_item Item need to be saved to the file at filePath
     */
    public void addItemtoFile(String filePath, Item new_item) throws IOException {
        Map<String, Item> itemHashMap = new HashMap<String, Item>();
        itemHashMap.put(Integer.toString(new_item.getItemId()), new_item);
        saveItemsToFile(itemHashMap, filePath);
    }


















    public void deleteItemfromfile(String filePath, String name_for_search) throws FileNotFoundException {
        //Read all item from the file
        Map<String, Item> itemMap = readItemsFromFile(filePath);
        //Convert the values of map to list
        ArrayList<Item> itemList = new ArrayList<Item>(itemMap.values());
        //Get the name of all item in the list
        ArrayList<String> itemNameList = null;
        for (Item item: itemList) {
            itemNameList.add(item.getName());
        }
        //Search the name list to find if item exist in the file
        for (String itemName: itemNameList) {
            if (name_for_search.equals(itemName)) {

            }
        }

    }
}

