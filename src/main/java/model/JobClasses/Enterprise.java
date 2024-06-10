package model.JobClasses;
import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;

public class Enterprise implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    private String Entname;
    private HashMap<String, Employee> employees = new HashMap<>();
    private String EntPort;

    public Enterprise()
    {}

    public Enterprise(String name, String port)
    {
        if (port.matches("\\d+"))
        {
            this.Entname = name;
            this.EntPort = port;
        }
    }

    public String getEntname() {
        return Entname;
    }

    public HashMap<String, Employee> getEmployees() {
        return employees;
    }

    public void addEmployee(String empName, String empPrename, String HourStart, String HourEnd)
    {
        // add employee to enterprise
        Employee emp = new Employee(empName, empPrename, HourStart, HourEnd, "");
        this.getEmployees().put(emp.getUuid(), emp);
    }

    public void addEmployee(String empName, String empPrename, String HourStart,
                            String HourEnd, String department)
    {
        // add employee to enterprise
        Employee emp = new Employee(empName, empPrename, HourStart, HourEnd, department);
        this.getEmployees().put(emp.getUuid(), emp);
    }

    public void addEmployee(Employee emp)
    {
        // add employee to enterprise
        this.getEmployees().put(emp.getUuid(), emp);
    }

    public void removeEmployee(Employee emp)
    {
        this.getEmployees().remove(emp.getUuid());
    }

    public ArrayList<String> getAllEmployeesName()
    {
        ArrayList<String> AllEmployeesName = new ArrayList<>();
        if (employees.isEmpty())
            AllEmployeesName.add("No employees");
        else
        {
            for (String id : employees.keySet())
                AllEmployeesName.add(String.format("%s %s (%s) ", employees.get(id).getEmpName(),
                        employees.get(id).getEmpPrename(), id));
        }

        return AllEmployeesName;
    }


    @Override
    public String toString()
    {
        return String.format("enterprise : %s | employees : %s ",
                Entname, employees);
    }

    public String getEntPort() {
        return EntPort;
    }

    public void setEntPort(String entPort) {
        EntPort = entPort;
    }

    public void setEntname(String entname) {
        Entname = entname;
    }

    // Override equals and hashCode for the test in the test Modules
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Enterprise that = (Enterprise) o;
        return Objects.equals(Entname, that.getEntname()) &&
                Objects.equals(EntPort, that.getEntPort()) &&
                Objects.equals(employees, that.employees);
    }

    @Override
    public int hashCode() {
        return Objects.hash(Entname, EntPort, employees);
    }
}