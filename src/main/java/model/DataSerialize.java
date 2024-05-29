package model;

import model.JobClasses.Employee;
import model.JobClasses.Enterprise;
import java.io.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashMap;


public class DataSerialize {

    private HashMap<String, Enterprise> allEnterprises = new HashMap<>();
    private final String fileText = "data.ser";

    public void saveData() throws IOException {
        // save all enterprises
        FileOutputStream fileOut = new FileOutputStream(fileText);
        ObjectOutputStream out = new ObjectOutputStream(fileOut);
        out.writeObject(this.allEnterprises);
        System.out.printf(String.format("Data saved to file %s \n", fileText));
        out.close();
    }

    public void loadData() throws IOException, ClassNotFoundException {
        // load all enterprises
        File file = new File(fileText);
        if (!file.exists()) {
            System.out.printf("Le fichier  + %s +  n'existe pas. \n", fileText);
            return;
        }
        FileInputStream fileIn = new FileInputStream(fileText);
        ObjectInputStream in = new ObjectInputStream(fileIn);
        this.allEnterprises = (HashMap<String, Enterprise>) in.readObject();
        System.out.printf(String.format("Data loaded from file %s \n", fileText));
        in.close();
    }

    public HashMap<String, Enterprise> getAllEnterprises() {
        return this.allEnterprises;
    }

    public void addNewEmployeeToEnterprise(String entName, String empName, String empPrename,
                                           String HourStart, String HourEnd) throws IOException {
        // add employee to enterprise
        this.allEnterprises.get(entName).addEmployee(empName, empPrename, HourStart, HourEnd);
        saveData();
    }

    public void addNewEmployeeToEnterprise(String entName, Employee emp) throws IOException {
        // add employee to enterprise
        this.allEnterprises.get(entName).addEmployee(emp);
        saveData();
    }

    public void removeEmployeeFromEnterprise(String entName, Employee emp) throws IOException
    {
        this.allEnterprises.get(entName).removeEmployee(emp);
        saveData();
    }

    public void addNewEnterprise(String name, String passwd, String port) throws IOException {
        // add new enterprise
        if (!this.allEnterprises.containsKey(name))
        {
            this.allEnterprises.put(name, new Enterprise(name, passwd, port));
            saveData();
        }
    }

    public void modifyEmpName(String ent, String uuid, String newName) throws IOException {
        this.allEnterprises.get(ent).getEmployees().get(uuid).setEmpName(newName);
        saveData();
    }

    public void modifyEmpPrename(String ent, String uuid, String newPrename) throws IOException {
        this.allEnterprises.get(ent).getEmployees().get(uuid).setEmpPrename(newPrename);
        saveData();
    }

    public void modifyEmpStartingHour(String ent, String uuid, String startingHour) throws IOException {
        this.allEnterprises.get(ent).getEmployees().get(uuid).setStartingHour(startingHour);
        saveData();
    }

    public void modifyEmpEndingHour(String ent, String uuid, String endingHour) throws IOException {
        this.allEnterprises.get(ent).getEmployees().get(uuid).setEndingHour(endingHour);
        saveData();
    }

    public void modifyEmpDepartement(String ent, String uuid, String endingHour) throws IOException {
        this.allEnterprises.get(ent).getEmployees().get(uuid).setEmDep(endingHour);
        saveData();
    }

    public void addNewWorkHour(String entName, String empId,String dateDay
                                ,  String hourStart) throws IOException
    {
        this.allEnterprises.get(entName).getEmployees()
                .get(empId).getWorkHour().addWorkHour(LocalDate.parse(dateDay), LocalTime.parse(hourStart));
        saveData();
    }

    // todo : test this function which allows to modify the time of a workhour for an employee
    public void modifyTimeWorkHour(String entName, String empId, String dateDay,
                                   String olderHour, String newHour) throws IOException
    {
        this.allEnterprises.get(entName).getEmployees().get(empId)
                .getWorkHour().changeLocalTime(dateDay, olderHour, newHour);

        saveData();
    }

    // todo : test this function which allows to modify the date of a workhour for an employee
    public void modifyDateWorkHour(String entName, String empId, String olderDate,
                                   String newDate,String newHour)throws IOException
    {
        this.allEnterprises.get(entName).getEmployees().get(empId)
                .getWorkHour().changeDateWorkHour(olderDate, newDate, newHour);

        saveData();
    }

    // todo : add to one of the fonctionnality of the appManageView (for the employees' pointer view)
    public void removeWorkHour(String entName, String empId, String date, String hour) throws IOException
    {
        this.allEnterprises.get(entName).getEmployees()
                .get(empId).getWorkHour().removeLocalTime(date, hour);
        saveData();
    }

    public Enterprise getEnterpriseClassByPort(String port)
    {
        Enterprise res = null;
        for(Enterprise ent : this.allEnterprises.values())
        {
            if (ent.getEntPort().equals(port))
                return ent;
        }

        return null;
    }

    @Override
    public String toString() {
        StringBuilder res = new StringBuilder(" allEnterprises from dataSerialize : \n");
        for (String s : allEnterprises.keySet())
        {
            res.append(String.format("entName %s -> %s \n", s, allEnterprises.get(s)));
        }
        return res.toString();
    }
}
