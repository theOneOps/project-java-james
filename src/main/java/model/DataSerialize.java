package model;

import java.io.*;
import java.util.HashMap;

public class DataSerialize {

    private HashMap<String, Enterprise> AllEnterprises;
    private final String fileText = "data.ser";

    public DataSerialize()
    {

    }

    /**
     * this method allows to save data directly in the file put in the 'fileText'
     * @Return void
     * */
    public void saveData() throws IOException {
        // save all enterprises
        FileOutputStream fileOut = new FileOutputStream(fileText);
        ObjectOutputStream out = new ObjectOutputStream(fileOut);
        out.writeObject(AllEnterprises);
        System.out.println(String.format("Data saved to file %s", fileText));
        out.close();
    }

    /**
     * this method allows to load data directly from the file put in the
     * 'fileText's value' to the HashMap<String, Enterprise> AllEnterprises
     * @Return void
     * */
    public void loadData() throws IOException, ClassNotFoundException {
        // load all enterprises
        FileInputStream fileIn = new FileInputStream(fileText);
        ObjectInputStream in = new ObjectInputStream(fileIn);
        AllEnterprises = (HashMap<String, Enterprise>) in.readObject();
        System.out.println(String.format("Data loaded from file %s", fileText));
        in.close();
    }

    /**
     * this method allows to add a new enterprise to the database
     * @Param String entName
     * @Return void
     * */
    public void addEnterprise(String entName)
    {
        Enterprise ent = new Enterprise(entName);
        AllEnterprises.put(entName, ent);
    }


    public HashMap<String, Enterprise> getAllEnterprises() {
        return AllEnterprises;
    }

    public void setAllEnterprises(HashMap<String, Enterprise> allEnterprises) {
        AllEnterprises = allEnterprises;
    }
}
