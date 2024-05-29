package model.JobClasses;
import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
public class Enterprise implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    private String Entname;
    private HashMap<String, Employee> employees = new HashMap<>();
    private String Entpasswd;
    private String EntPort;

    public Enterprise()
    {}

    public void setEntname(String entname) {
        Entname = entname;
    }

    public void setEntpasswd(String entpasswd) {
        Entpasswd = entpasswd;
    }

    // to create new enterprise
    public Enterprise(String name, String passwd, String port)
    {
        if (port.matches("\\d+"))
        {
            this.Entname = name;
            this.Entpasswd = passwd;
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
        //ArrayList<String> defaultEntreprises = new ArrayList<>(List.of(new String[]{"Entreprise1", "Entreprise2"}));
//        if (defaultEntreprises.contains(getEntname())) {
//            AllEmployeesName.add("No employees");
//        }
//        else
//        {
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

    public String getEntpasswd()
    {
        return Entpasswd;
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
}
