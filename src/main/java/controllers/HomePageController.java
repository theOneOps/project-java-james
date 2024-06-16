package controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableArray;
import javafx.collections.ObservableList;
import model.DataSerialize;
import model.EmployeComparator;
import model.JobClasses.Employee;
import model.JobClasses.Enterprise;
import socket.ServersSocket;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.*;
import java.util.function.ObjDoubleConsumer;


/**
 * @author Martin
 * Connection between the back-end and front-end of the application
 */
public class HomePageController {

    public HomePageController() {
    }

    //======================================= new =======================================//
    /**
     * Get all info in singleton dataserialize;
     * @return ObservableList of all emp sort by most recent registered date
     * {@link #/model/EmployeeComparator.compare()}
     */
    public ObservableList<Employee> getEmployees() {
        HashMap<String, Enterprise> all = DataSerialize.getInstance().getAllEnterprises();
        ObservableList<Employee> observableEmployees = FXCollections.observableArrayList();

        for (Enterprise e : all.values()) {
            //get all employees
            for(Map.Entry entry : e.getEmployees().entrySet()){
                observableEmployees.add((Employee) entry.getValue());
            }
        }
        //sort employees inside the observableList by their most recent register date.
        FXCollections.sort(observableEmployees, new EmployeComparator());
        return observableEmployees;
    }

    public ObservableList<Enterprise> getEntreprises() {
        HashMap<String, Enterprise> all = DataSerialize.getInstance().getAllEnterprises();
        ObservableList<Enterprise> observableEnterprises = FXCollections.observableArrayList();
        for (Map.Entry entry : all.entrySet()) {
            observableEnterprises.add((Enterprise) entry.getValue());
        }
        return observableEnterprises;
    }

    /**
     * search all employee with this name  in all employee
     * @param name
     * @return employee if found else null
     */
    public ArrayList<Employee> searchEmployeeByName(String name, ObservableList<Employee> employees){
        ArrayList<Employee> result = new ArrayList<>();
        for(Employee e : employees){
            if(Objects.equals(e.getEmpName(), name)) result.add(e);
        }
        return result;
    }

    /**
     * search all employee with this preName in all employee
     * @param prename
     * @return employee if found else null
     */
    public ArrayList<Employee> searchEmployeeByPreName(String prename, ObservableList<Employee> employees){
        ArrayList<Employee> result = new ArrayList<>();
        for(Employee e : employees){
            if(Objects.equals(e.getEmpPrename(), prename)) result.add(e);
        }
        return result;

    }

    /**
     * Get last pointing of all employees
     * @param employees list of employees
     * @return result
     */
    public ObservableList<Employee> todaysEmployees(ObservableList<Employee> employees){
        ObservableList<Employee> result = FXCollections.observableArrayList();
        LocalDate today = LocalDate.now();
        for(Employee e : employees){
            LocalDateTime compareDate = LocalDateTime.of(today, e.getWorkHour().getLastPointing());
            LocalDateTime midNight = today.atStartOfDay();
            if(compareDate.isAfter(midNight)) result.add(e);
        }
        return result;
    }

}