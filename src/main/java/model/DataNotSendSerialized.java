package model;
import java.io.*;
import java.util.ArrayList;

public class DataNotSendSerialized {

    /**
     * Singleton pattern for Dataserialize for 2 main reasons
     * First, need to acesses easily this object form everywhere
     * Second, to avoid loading of hard-drive content on memory each time object is creater
     */
    private static DataNotSendSerialized singleton;

    /**
     * Use try catch, load can throw errors.
     * @return singleton
     */
    public static DataNotSendSerialized getInstance() {
        if (singleton == null) {
            singleton = new DataNotSendSerialized();
            try {
                singleton.loadData();
            } catch (IOException | ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        }
        return singleton;
    }

    private final String fileText = "workhoursNotSended.ser";

    public void saveData(ArrayList<String> data) throws IOException {
        // save all enterprises
        FileOutputStream fileOut = new FileOutputStream(fileText);
        ObjectOutputStream out = new ObjectOutputStream(fileOut);
        out.writeObject(data);
        System.out.printf(String.format("Data saved to file %s \n", fileText));
        out.close();
    }

    public ArrayList<String> loadData() throws IOException, ClassNotFoundException {
        // load all enterprises
        File file = new File(fileText);
        if (!file.exists()) {
            System.out.printf("Le fichier  + %s +  n'existe pas. \n", fileText);
            return new ArrayList<>();
        }
        FileInputStream fileIn = new FileInputStream(fileText);
        ObjectInputStream in = new ObjectInputStream(fileIn);
        return (ArrayList<String>) in.readObject();
    }

    public boolean deleteData() {
        File file = new File(fileText);
        if (!file.exists()) {
            System.out.println("Delete Data");
            return file.delete();
        }
        return false;
    }

    /**
     * Use to print data of the "workhoursNotSended.ser" File
     * For test
     */
    public void printData() {
        File file = new File(fileText);
        if (file.exists()) {
            try {
                ArrayList<String> tmp = loadData();
                System.out.println("Print Data Serialize");
                for (String s : tmp ) {
                    System.out.println(s);
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            }

        }
    }
}
