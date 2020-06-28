package bookTradeSystem;

import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

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
     * Return the all object in the map of object ids to Objects from the file at path filePath.
     *
     * @param filePath the path of the data file
     */
    public Map<String, Object> readFromFile(String filePath) throws FileNotFoundException{

        File new_file = new File(filePath);
        if (new_file.exists()) {
            //Create a new empty hashmap which key is the id and value is the object
            Map<String, Object> ObjectMap = new HashMap<String, Object>();
            try {
                InputStream file = new FileInputStream(filePath);
                InputStream buffer = new BufferedInputStream(file);
                ObjectInput input = new ObjectInputStream(buffer);

                // deserialize the Map
                ObjectMap = (Map<String, Object>) input.readObject();
                input.close();
            } catch (IOException | ClassNotFoundException ex) {
                //TODO;
            }
            return ObjectMap;
        } else {
            throw new FileNotFoundException();
        }
    }

    /**
     * Writes the objects to file at filePath.
     *
     * @param filePath the file to write the records to
     * @param ObjectMap the mapping of object ids to Objects
     * @throws IOException
     */
    public void saveToFile(Map<String, Object> ObjectMap, String filePath) throws IOException {
        //If the file does not exist, create the file with the name of filePath first
        File new_file = new File(filePath);
        if (!new_file.exists()) {
            new_file.createNewFile();
        }

        OutputStream file = new FileOutputStream(filePath);
        OutputStream buffer = new BufferedOutputStream(file);
        ObjectOutput output = new ObjectOutputStream(buffer);

        // serialize the Map
        output.writeObject(ObjectMap);
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
     * Serialize the object into the file at filePath.
     *
     * @param filePath the file to write the records to
     * @param new_object the object need to be saved to the file at filePath
     */
    public void SerializeObject(String filePath, Object new_object){
        try {
            Map<String, Object> new_object_map = new HashMap<String, Object>();
            new_object_map.put("new_object.getID", new_object);
            saveToFile(new_object_map, filePath);
        } catch (IOException ex){
            //TODO:}

        }
    }
}

