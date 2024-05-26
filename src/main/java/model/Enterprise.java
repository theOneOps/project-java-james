package model;

import java.io.Serializable;
import java.util.HashMap;

public class Enterprise implements Serializable {
    private static final long serialVersionUID = 1L;
    private HashMap<String, Employee> AllEmployees = new HashMap<>();

    public Enterprise()
    {

    }

    public HashMap<String, Employee> getAllEmployees() {
        return AllEmployees;
    }

    public void setAllEmployees(HashMap<String, Employee> allEmployees) {
        AllEmployees = allEmployees;
    }
}
