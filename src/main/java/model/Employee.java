package model;

import java.io.Serializable;
import java.time.LocalTime;

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

    public Employee(String name, String prename, String StartingHour, String EndingHour)
    {
        empName = name;
        empPrename = prename;
        startingHour = String.valueOf(LocalTime.parse(StartingHour));
        endingHour = String.valueOf(LocalTime.parse(EndingHour));
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

    @Override
    public String toString()
    {
        return String.format(
                "employeeUUID %s "+
                        "employeeName %s " +
                        "employeePrename %s " +
                        "employeeStartingHour %s " +
                        "employeeEndingHour %s " +
                        "emloyeeWorkhour %s \n",uuid, empName, empPrename, startingHour, endingHour, workHour);
    }
}
