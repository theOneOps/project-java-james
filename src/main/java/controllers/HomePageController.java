package controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.DataSerialize;
import model.EmployeComparator;
import model.JobClasses.Employee;
import model.JobClasses.Enterprise;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;
import java.util.function.ObjDoubleConsumer;


/**
 * @author Martin
 * Connection between the back-end and front-end of the application
 */
public class HomePageController {
    //Todo : remove class's attributs and use backend load. can't do now because no backend/
    private DataSerialize ds;
    private ArrayList<Enterprise> enterprises;
    private ObservableList<Employee> employees;

    public HomePageController(){
        this.enterprises = new ArrayList<>();
    }
    public HomePageController(DataSerialize ds) {
        this.enterprises = new ArrayList<>();
        this.ds = ds;
    }

    public ArrayList<Enterprise> getEnterprises() {
        return enterprises;
    }

    public void setEmployees() {
        this.employees  = FXCollections.observableArrayList(generateEmployees());
    }

    public ObservableList<Employee> getEmployees() {
        return employees;
    }


    //======================================= new =======================================//
    /**
     * Get all info in singleton dataserialize;
     * @return ObservableList of all emp sort by most recent registered date
     * {@link #/model/EmployeeComparator.compare()}
     */
    private ArrayList<Employee> generateEmployees(){
        HashMap<String, Enterprise> enterprise = new HashMap<>();
        /*
        for (int i = 0; i < 3; i++) {
            Enterprise e = new Enterprise();
            for (int j = 0; j < 30; j++) {
                if(i == 0){
                    Employee emp = new Employee("nom"+i+j, "prenom"+i+j, "10:15:45", "17:15:45", "dept"+i);
                    emp.getWorkHour().addWorkHour(LocalDate.now(), LocalTime.now());
                    emp.getWorkHour().addWorkHour(LocalDate.now(), LocalTime.MIN);
                    emp.getWorkHour().addWorkHour(LocalDate.now(), LocalTime.of(2, 45, 28));
                    e.addEmployee(emp);
    public ObservableList<Employee> getEmployees() {
        HashMap<String, Enterprise> all = DataSerialize.getInstance().getAllEnterprises();
        ObservableList<Employee> observableEmployees = FXCollections.observableArrayList();

        for (Enterprise e : all.values()) {
            //get all employees
           for(Map.Entry entry : e.getEmployees().entrySet()){
               observableEmployees.add((Employee) entry.getValue());
            }
            enterprise.put(String.valueOf(i), e);
        }*/
        enterprise = ds.getAllEnterprises();
        //TODO : change to backend function instead of static content.
        //HashMap<String, Enterprise> enterprise = ds.getAllEnterprises();
        ArrayList<Employee> recentRegistersEmployee = new ArrayList<>();
        int i=0;
        for(Enterprise e : enterprise.values()){
            e.setEntname("Entreprise " + i );
            i++;
            enterprises.add(e);
            HashMap<String, Employee> employees = e.getEmployees();
            recentRegistersEmployee.addAll(employees.values());
        }
        //sort employees inside the observableList by their most recent register date.
        FXCollections.sort(observableEmployees, new EmployeComparator());
        return observableEmployees;
    }

    public ObservableList<Enterprise> getEntreprises(){
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

}
