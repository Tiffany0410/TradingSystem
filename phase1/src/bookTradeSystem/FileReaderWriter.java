package bookTradeSystem;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.ConsoleHandler;
import java.util.logging.Logger;

import bookTradeSystem.*;

public class FileReaderWriter implements Serializable {

    /** A mapping of Object ids to Objects. */
    private Map<String, Object> ObjectMap;

    /**
     * Creates a new empty StudentManager.
     *
     * @throws IOException
     * @throws ClassNotFoundException
     */
    public FileReaderWriter(Map<String, Object> ObjectMap, String filePath) throws ClassNotFoundException, IOException {
        this.ObjectMap = ObjectMap;

        // Reads serializable objects from file.
        // Populates the record list using stored data, if it exists.
        File file = new File(filePath);
        if (file.exists()) {
          readFromFile(filePath);
        } else {
          file.createNewFile();
        }
    }

    /**
     * Return the all menu in string from the file at path filePath.
     *
     * @param filePath the path of the data file
     * @throws FileNotFoundException if filePath is not a valid path
     */
    public String readFromMenu(String filePath) throws FileNotFoundException {

        // FileInputStream can be used for reading raw bytes, like an image.
        Scanner scanner = new Scanner(new FileInputStream(filePath));
        String record;

        while (scanner.hasNextLine()) {
            record += scanner.nextLine();
            record += "\n"
        }
        scanner.close();
        return record;
    }


    /**
     * Writes the objects to file at filePath.
     *
     * @param filePath the file to write the records to
     * @param ObjectMap the mapping of onject ids to Objects
     * @throws IOException
     */
    public void saveToFile(String filePath) throws IOException {

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

        // FileInputStream can be used for reading raw bytes, like an image.
        Scanner scanner = new Scanner(new FileInputStream(filePath));
        String[] record;
        int MenuNumber = 0;

        while (scanner.hasNextLine()) {
            record = scanner.nextLine().split(".");
            if (record[0] >= MenuNumber){
                MenuNumber = record[0]
            }
        }
        scanner.close();

        return MenuNumber;
    }
}