package model;

import java.io.Serializable;
import java.util.HashMap;
import java.util.UUID;

public class Enterprise implements Serializable {
    private static final long serialVersionUID = 1L;
    private String entName;
    private HashMap<String, Employee> AllEmployees = new HashMap<>();

    public Enterprise(String name)
    {
        entName = name;
    }

    /**
     * @Return the enterprise's name
     */
    public String getEntName() {
        return entName;
    }

    /**
     * @Param String entName corresponding to the name of the enterprise
     * @Return void
     */
    public void setEntName(String entName) {
        this.entName = entName;
    }

    // to use for the project
    /**
     * @Param String name : the name of the employee [e.g: "john"]
     * @Param String prename : the prename of the employee [e.g: "doe"]
     * @Param String startingHour : the start working hour [e.g:13:00]
     * @Return void
     */
    public void addEmployee(String name, String prename, String startingHour, String endingHour)
    {
        UUID uuid = UUID.randomUUID();
        String generateUUID = uuid.toString();
        Employee emp = new Employee(name, prename, startingHour, endingHour);
        emp.setUuid(generateUUID);
        AllEmployees.put(generateUUID, emp);
    }

    /**
     * This method allows to add an Employee object directly to this enterprise
     * @Param Employee emp : an instance object of the employee's class
     * @Return void
     */
    // for tests
    public void addEmployee(Employee emp)
    {
        String uuid = UUID.randomUUID().toString();
        emp.setUuid(uuid);
        AllEmployees.put(emp.getUuid(), emp);
    }

    /**
     * this method allows to remove from the enteprise the employee with the uuid given in parameter
     * @Param String uuidEmp : the uuid of the employee
     * @Return void
     */
    public void removeEmployee(String uuidEmp)
    {
        AllEmployees.remove(uuidEmp);
    }

    /**
     * this method allows to modify an employee's attributs like
     * name, prename by giving the uuid of the employee
     * @Param Employee emp : an instance object of the employee's class
     * @Param String prename : the prename of the employee [e.g: "doe"]
     * @Param String startingHour : the start working hour [e.g:13:00]
     * @Return void
     */
    public void modifyEmpAttributs(String uuidEmp, String newName, String newPrename)
    {
        AllEmployees.get(uuidEmp).setEmpName(newName);
        AllEmployees.get(uuidEmp).setEmpPrename(newPrename);
    }

    public HashMap<String, Employee> getAllEmployees() {
        return AllEmployees;
    }

    public void setAllEmployees(HashMap<String, Employee> allEmployees) {
        AllEmployees = allEmployees;
    }

    @Override
    public String toString()
    {
        StringBuilder res = new StringBuilder();
        System.out.printf("enterprise's name -> %s \n", this.entName);
        for(String currentUuid : AllEmployees.keySet())
        {
            res.append(String.format(currentUuid + " -> " + AllEmployees.get(currentUuid)));
            res.append("\n");
        }
        return res.toString();
    }
}
