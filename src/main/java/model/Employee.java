package model;

import java.io.Serializable;

public class Employee implements Serializable {

    private static final long serialVersionUID = 1L;
    private String uuid;
    private String empName;
    private String empPrename;
    private String startingHour;
    private String endingHour;
    private WorkHour workHour;

    Employee()
    {}

    Employee(String name, String prename, String StartingHour, String EndingHour)
    {}

    public Employee(String FirstName, String LastName) {
        this.empName = FirstName;
        this.empPrename = LastName;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getEmpName() {
        return empName;
    }

    public void setEmpName(String empName) {
        this.empName = empName;
    }

    public String getEmpPrename() {
        return empPrename;
    }

    public void setEmpPrename(String empPrename) {
        this.empPrename = empPrename;
    }

    public String getStartingHour() {
        return startingHour;
    }

    public void setStartingHour(String startingHour) {
        this.startingHour = startingHour;
    }

    public String getEndingHour() {
        return endingHour;
    }

    public void setEndingHour(String endingHour) {
        this.endingHour = endingHour;
    }

    public WorkHour getWorkHour() {
        return workHour;
    }

    public void setWorkHour(WorkHour workHour) {
        this.workHour = workHour;
    }
}
