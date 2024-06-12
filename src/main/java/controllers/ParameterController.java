package controllers;

import model.DataSerialize;
import model.JobClasses.Employee;
import model.JobClasses.Enterprise;

import java.io.IOException;

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
            //TODO : add the new enterprise to all the enterprises for reload when user go to homePage view.
            DataSerialize serializer = new DataSerialize();
            serializer.addNewEnterprise(companyName, port);
            //create employees
            for (int i = 0; i < nbEmployee; i++) {
                Employee emp = new Employee(empName + i, empPrename + i, startingHour, endingHour, "dept1");
                serializer.addNewEmployeeToEnterprise(companyName, emp);
            }
        } catch (Exception e) {
            result = false;
        }
        return result;
    }

    /**
     * Pattern matching for localTime
     * @param regex
     * @return true if matched, else false
     */
    public boolean checkLocalTimeRegex(String regex) {
        return regex.equals("^([01]?[0-9]|2[0-3]):[0-5][0-9]:[0-5][0-9]$");
    }

}
