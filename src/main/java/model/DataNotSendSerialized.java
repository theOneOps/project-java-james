package model;
import java.io.*;
import java.util.ArrayList;

public class DataNotSendSerialized {
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
