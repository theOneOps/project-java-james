package model;

import java.io.*;
import java.util.HashMap;

public class DataSerialize {

    private HashMap<String, Enterprise> AllEnterprises;
    private final String fileText = "data.ser";

    DataSerialize()
    {

    }

    public void saveData() throws IOException {
        // save all enterprises
        FileOutputStream fileOut = new FileOutputStream(fileText);
        ObjectOutputStream out = new ObjectOutputStream(fileOut);
        out.writeObject(AllEnterprises);
        System.out.println(String.format("Data saved to file %s", fileText));
        out.close();
    }

    public void loadData() throws IOException, ClassNotFoundException {
        // load all enterprises
        FileInputStream fileIn = new FileInputStream(fileText);
        ObjectInputStream in = new ObjectInputStream(fileIn);
        AllEnterprises = (HashMap<String, Enterprise>) in.readObject();
        System.out.println(String.format("Data loaded from file %s", fileText));
        //System.out.println(this);
        in.close();
    }

    public void addEnterprise(String entName)
    {

    }

    public HashMap<String, Enterprise> getAllEnterprises() {
        return AllEnterprises;
    }

    public void setAllEnterprises(HashMap<String, Enterprise> allEnterprises) {
        AllEnterprises = allEnterprises;
    }
}
