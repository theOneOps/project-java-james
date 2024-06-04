package controllers;

import model.JobClasses.Employee;

import java.util.ArrayList;

public class EmployeeController {

    public EmployeeController() {}

    public ArrayList<Employee> takeEmploye() {
        ArrayList<Employee> listEmployee = new ArrayList<>();

        Employee e1 = new Employee("1", "Paul", "Jean", "09:30", "18:00", "DI");
        Employee e2 = new Employee("2", "A", "RP", "11:30", "19:00", "DI");

        listEmployee.add(e1);
        listEmployee.add(e2);
        return listEmployee;
    }

}
