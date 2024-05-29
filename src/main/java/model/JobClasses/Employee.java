package model.JobClasses;

import java.io.Serializable;
import java.time.LocalTime;
import java.util.UUID;

public class Employee implements Serializable {

    private static final long serialVersionUID = 1L;

    private String uuid;
    private String empName;
    private String empPrename;
    private String emDep = "";
    private String startingHour;
    private String endingHour;
    private WorkHour workHour = new WorkHour();

    public Employee(String name, String prename, String hourStart, String hourEnd, String department)
    {
        this.uuid = UUID.randomUUID().toString();
        this.empName = name;
        this.empPrename = prename;
        this.startingHour = LocalTime.parse(hourStart).toString();
        this.endingHour = LocalTime.parse(hourEnd).toString();
        this.emDep = department;
    }

    public Employee(String uuid, String name, String prename, String hourStart, String hourEnd, String department)
    {
        this.uuid = uuid;
        this.empName = name;
        this.empPrename = prename;
        this.startingHour = LocalTime.parse(hourStart).toString();
        this.endingHour = LocalTime.parse(hourEnd).toString();
        this.emDep = department;
    }

    public Employee(String uuid, String name, String prename, String hourStart,
                    String hourEnd, String department, WorkHour wh)
    {
        this.uuid = uuid;
        this.empName = name;
        this.empPrename = prename;
        this.startingHour = LocalTime.parse(hourStart).toString();
        this.endingHour = LocalTime.parse(hourEnd).toString();
        this.emDep = department;
        this.workHour = wh;
    }

    public void setEmpName(String empName) {
        this.empName = empName;
    }

    public void setEmpPrename(String empPrename) {
        this.empPrename = empPrename;
    }

    public String getEmpName() {
        return empName;
    }

    public String getEmpPrename() {
        return empPrename;
    }

    public String getUuid() {
        return uuid;
    }

    public String getStartingHour() {
        return startingHour;
    }

    public String getEndingHour() {
        return endingHour;
    }

    public String getEmDep() {
        return emDep;
    }

    public void setEmDep(String emDep) {
        this.emDep = emDep;
    }

    public WorkHour getWorkHour() {
        return workHour;
    }

    public void setWorkHour(WorkHour workHour) {
        this.workHour = workHour;
    }

    public void setStartingHour(String startingHour) {
        this.startingHour = startingHour;
    }

    public void setEndingHour(String endingHour) {
        this.endingHour = endingHour;
    }


    @Override
    public String toString()
    {
        return String.format("name : %s prename : %s workhour : %s \n", empName, empPrename, workHour);
    }

}
