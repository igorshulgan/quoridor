package game;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;
import java.util.logging.Level;

/**
 * Created by ihar_shulhan on 01/04/2017.
 */
public class SavedCalculations implements Serializable{
    private static SavedCalculations ourInstance = new SavedCalculations();

    private HashMap<SavedMap, Integer> map = new HashMap<>();

    public static SavedCalculations getInstance() {
        return ourInstance;
    }

    @Override
    public String toString() {
        return "SavedCalculations{" +
                "map=" + map +
                '}';
    }

    private SavedCalculations() {
    }

    public static void readFromFile() {
        try {
            InputStream file = new FileInputStream("savedCalculations.ser");
            InputStream buffer = new BufferedInputStream(file);
            ObjectInput input = new ObjectInputStream (buffer);
            ourInstance = (SavedCalculations)input.readObject();
            input.close();
        } catch(ClassNotFoundException | IOException ex){
            System.out.println("Cannot perform input. " + ex);
        }
    }

    public void addMap(Map map, int playerNumber, int result) {
        this.map.put(new SavedMap(map, playerNumber), result);
    }

    public Integer getMapResult(Map map, int playerNumber) {
        return this.map.get(new SavedMap(map, playerNumber));
    }

    public static void saveObject() {
        try {
            OutputStream file = new FileOutputStream("savedCalculations.ser");
            OutputStream buffer = new BufferedOutputStream(file);
            ObjectOutput output = new ObjectOutputStream(buffer);
            output.writeObject(ourInstance);
            output.close();
        } catch(IOException ex){
            System.out.println("Cannot perform output. " + ex);
            ex.printStackTrace();
        }
    }
}

