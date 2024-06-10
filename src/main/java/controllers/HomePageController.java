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

    /**
     * Create static content to show in views because back-end not working
     * @return static enterprises
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


                }else {
                    Employee emp = new Employee("nom"+i*j, "prenom"+i*j, "10:15:45", "17:15:45", "dept"+i);
                    emp.getWorkHour().addWorkHour(LocalDate.now(), LocalTime.now());
                    emp.getWorkHour().addWorkHour(LocalDate.now(), LocalTime.of(16, 25, 3));
                    emp.getWorkHour().addWorkHour(LocalDate.now(), LocalTime.MIN);
                    e.addEmployee(emp);

                }
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
        recentRegistersEmployee.sort(new EmployeComparator());
        return recentRegistersEmployee;
    }

    /**
     * search all employee with this name  in all employee
     * @param name
     * @return employee if found else null
     */
    public ArrayList<Employee> searchEmployeeByName(String name){
        //TODO  : change to backend functions instead of static content;
        //static content :
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
    public ArrayList<Employee> searchEmployeeByPreName(String prename){
        //TODO  : change to backend functions instead of static content;
        ArrayList<Employee> result = new ArrayList<>();
        for(Employee e : employees){
            if(Objects.equals(e.getEmpPrename(), prename)) result.add(e);
        }
        return result;

    }
}
