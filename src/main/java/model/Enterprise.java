package model;

import java.util.HashMap;

public class Enterprise {
    private HashMap<String, Employee> AllEmployees = new HashMap<>();

    Enterprise()
    {

    }

    public HashMap<String, Employee> getAllEmployees() {
        return AllEmployees;
    }

    public void setAllEmployees(HashMap<String, Employee> allEmployees) {
        AllEmployees = allEmployees;
    }
}
