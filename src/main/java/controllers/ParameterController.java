package controllers;

import model.DataSerialize;
import model.JobClasses.Employee;
import model.JobClasses.Enterprise;

import java.io.IOException;

public class ParameterController {
    public ParameterController() {
    }

    public boolean createEnterprise(String companyName, String port, String empName, String empPrename, String startingHour, String endingHour, int nbEmployee) {
        boolean result = true;
        try {
            DataSerialize serializer = new DataSerialize();
            serializer.addNewEnterprise(companyName, port, "0000");
            for (int i = 0; i < nbEmployee; i++) {
                Employee emp = new Employee(empName + i, empPrename + i, startingHour, endingHour, "dept1");
                serializer.addNewEmployeeToEnterprise(companyName, emp);
            }
        } catch (Exception e) {
            result = false;
        }
        return result;
    }

    public boolean checkLocalTimeRegex(String regex) {
        return regex.equals("^([01]?[0-9]|2[0-3]):[0-5][0-9]:[0-5][0-9]$");
    }

}
