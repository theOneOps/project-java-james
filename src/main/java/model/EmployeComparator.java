package model;

import model.JobClasses.Employee;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;

/**
 * @author Martin
 * @summary This class implements a comparator to have a way to compare employees with each other
 */
public class EmployeComparator implements Comparator<Employee> {
    /**
     * compares two employees based on their most recent scores.
     * @param o1 the first object to be compared.
     * @param o2 the second object to be compared.
     * @return -1 : o1 older than o2, 1 : o1 younger than o2, 0 equals.
     */
    @Override
    public int compare(Employee o1, Employee o2) {
        if(o1.getWorkHour().getLastPointing().isAfter(o2.getWorkHour().getLastPointing())){
            return 1;
        }else if (o1.getWorkHour().getLastPointing().isBefore(o2.getWorkHour().getLastPointing())){
            return -1;
        }else return 0;
    }
}
