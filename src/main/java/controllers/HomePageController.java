package controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.DataSerialize;
import model.EmployeComparator;
import model.JobClasses.Employee;
import model.JobClasses.Enterprise;
import views.HomePageView;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;



public class HomePageController {

    private ObservableList<Employee> employees;

    public HomePageController(){}

    public void setEmployees() {
        this.employees  = FXCollections.observableArrayList(generateEmployees());
    }

    public ObservableList<Employee> getEmployees() {
        return employees;
    }

    private ArrayList<Employee> generateEmployees(){
        DataSerialize ds = new DataSerialize();
         HashMap<String, Enterprise> enterprise = new HashMap<>();
        for (int i = 0; i < 3; i++) {
            Enterprise e = new Enterprise();
            for (int j = 0; j < 30; j++) {
                if(i == 0){
                    Employee emp = new Employee("nom"+i+j, "prenom"+i+j, "10:15:45", "17:15:45", "dept"+i);
                    emp.getWorkHour().addWorkHour(LocalDate.now(), LocalTime.now());
                    e.addEmployee(emp);

                }else {
                    Employee emp = new Employee("nom"+i*j, "prenom"+i*j, "10:15:45", "17:15:45", "dept"+i);
                    emp.getWorkHour().addWorkHour(LocalDate.now(), LocalTime.now());
                    e.addEmployee(emp);

                }
            }
            enterprise.put(String.valueOf(i), e);
        }
        //TODO : change to backend function instead of static content.
        // HashMap<String, Enterprise> enterprise = ds.getAllEnterprises();
        ArrayList<Employee> recentRegistersEmployee = new ArrayList<>();
        for(Enterprise e : enterprise.values()){
            HashMap<String, Employee> employees = e.getEmployees();
            recentRegistersEmployee.addAll(employees.values());
        }
        recentRegistersEmployee.sort(new EmployeComparator());
        return recentRegistersEmployee;
    }
}
