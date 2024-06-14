package controllers;

import model.DataSerialize;
import model.JobClasses.Employee;
import model.JobClasses.Enterprise;

import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Martin
 * Connection between the back-end and front-end of the application
 */
public class ParameterController {
    public ParameterController() {
    }

    /**
     * Create an enterprise with a given number of employee
     * @param companyName
     * @param port
     * @param empName
     * @param empPrename
     * @param startingHour
     * @param endingHour
     * @param nbEmployee
     * @return true if created, false else
     */
    public boolean createEnterprise(String companyName, String port, String empName, String empPrename, String startingHour, String endingHour, int nbEmployee) {
        boolean result = true;
        //surrounded with try catch because model function throws exception.
        try {
            DataSerialize.getInstance().addNewEnterprise(companyName, port);
            //create employees
            for (int i = 0; i < nbEmployee; i++) {
                Employee emp = new Employee(empName + i, empPrename + i, startingHour, endingHour, "dept1");
                DataSerialize.getInstance().addNewEmployeeToEnterprise(companyName, emp);
            }
        } catch (Exception e) {
            result = false;
        }

        System.out.println(DataSerialize.getInstance().toString());
        return result;
    }

    /**
     * Pattern matching for localTime
     * @param input
     * @return true if matched, else false
     */
    public boolean checkLocalTimeRegex(String input) {
        Pattern pattern = Pattern.compile("^([01]?[0-9]|2[0-3]):[0-5][0-9]:[0-5][0-9]$");
        Matcher matcher = pattern.matcher(input);
        return matcher.matches();
    }

    /**
     * Get all enterprises from DataSerialize instance
     *
     * @return
     */
    public HashMap<String, Enterprise> getEnterprises() {
        return DataSerialize.getInstance().getAllEnterprises();
    }
}