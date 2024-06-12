package controllers;

import model.JobClasses.Employee;
import model.JobClasses.Enterprise;
import model.JobClasses.WorkHour;
import model.DataSerialize;

import java.io.IOException;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Map;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;


public class EmployeeController {
    private Enterprise enterprise;
    public EmployeeController(Enterprise enterprise) {
        this.enterprise = enterprise;
    }

    public ObservableList<Employee> getEmployee() {
        ArrayList<Employee> tmp = new ArrayList<>();
        for (Map.Entry entry : enterprise.getEmployees().entrySet()) {
            tmp.add((Employee) entry.getValue());
        }
        return FXCollections.observableArrayList(tmp);
    }

    public void addEmployee(String nameVarText, String prenameVarText,
                            String workHourStartVarText, String workHourEndVarText, String derparture) {
        if (checkLocalTimeRegex(workHourStartVarText) && checkLocalTimeRegex(workHourEndVarText) &&
                !Objects.equals(nameVarText, "") && !Objects.equals(prenameVarText, "")) {
            Employee emp = new Employee(nameVarText, prenameVarText, workHourStartVarText, workHourEndVarText,
                    derparture);
            try {
                DataSerialize.getInstance().addNewEmployeeToEnterprise(enterprise.getEntname(), emp);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }

    public boolean removeEmployee(String uuid) {
        Employee emp = enterprise.getEmployees().get(uuid);
        if (emp != null) {
            try {
                DataSerialize.getInstance().removeEmployeeFromEnterprise(enterprise.getEntname(), emp);
                return true;
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
        return false;
    }

    //TODO Verif que 2nd heure de travail soit supp à la première

    /**
     * Description: Vérifie si les valeurs des champs sont valide
     * @return
     */
    public String updateEmployee(Employee emp, String nameVarText, String prenameVarText,
                                  String workHourStartVarText, String workHourEndVarText) {
        if (checkLocalTimeRegex(workHourStartVarText) && checkLocalTimeRegex(workHourEndVarText) &&
                !Objects.equals(nameVarText, "") && !Objects.equals(prenameVarText, "")) {
            // Récupérer employee par son uuid
            Employee tmp = enterprise.getEmployees().get(emp.getUuid());
            String tmpNameEnterprise = enterprise.getEntname();
            String tmpEmployeeUuid = tmp.getUuid();
            // Modification employee dans fichier binaire
            try {
                DataSerialize.getInstance().modifyEmpName(tmpNameEnterprise, tmpEmployeeUuid, nameVarText);
                DataSerialize.getInstance().modifyEmpPrename(tmpNameEnterprise, tmpEmployeeUuid, prenameVarText);
                DataSerialize.getInstance().modifyEmpStartingHour(tmpNameEnterprise, tmpEmployeeUuid, workHourStartVarText);
                DataSerialize.getInstance().modifyEmpEndingHour(tmpNameEnterprise, tmpEmployeeUuid, workHourEndVarText);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            // Modification interface
            tmp.setEmpName(nameVarText);
            tmp.setEmpPrename(prenameVarText);
            tmp.setStartingHour(workHourStartVarText);
            tmp.setEndingHour(workHourEndVarText);
            return "Les informations ont été validé.";
        }
        else {
            return "Impossible de modifier les informations. Veullez vérifier si les dates sont valides";
        }
    }

    public ObservableList getWorkHour(String uuid) {
        // Take Employees
        ObservableList<Employee> employees = getEmployee();
        // Take WorkHour
        WorkHour tmp2 = new WorkHour();
        for(Employee e : employees) {
            if(Objects.equals(e.getUuid(), uuid)) {
                tmp2 = e.getWorkHour();
            }
        }

        ArrayList<ArrayList<LocalTime>> tmp3 = new ArrayList<>();
        for (Map.Entry wh : tmp2.getPointing().entrySet()) {
            tmp3.add((ArrayList<LocalTime>) wh.getValue());
        }
        // Take Time of pinting
        ArrayList<LocalTime> pointing = new ArrayList<>();
        for (ArrayList<LocalTime> localTimes : tmp3) {
            pointing.addAll(localTimes);
        }
        return FXCollections.observableArrayList(pointing);
    }

    public boolean checkLocalTimeRegex(String regex) {
        Pattern pattern = Pattern.compile("^([01]?[0-9]|2[0-3]):[0-5][0-9]:[0-5][0-9]$");
        Matcher matcher = pattern.matcher(regex);
        return matcher.matches();
    }

    /**
     * Description : Détermine si une heure est au format (hour:minute:second) et
     * si les valeurs de l'heure sont valide
     * (Ex: une heure ne peut être supérieur à 23 (ou 24 ~> 00))
     * @return Boolean : Vrai si l'heure est au bon format Sinon faux
     *//*
    public boolean hourValide(String param, String delimitor) {
        String[] time = param.split(delimitor);
        if (time.length != 3) return false;

        int hour = Integer.parseInt(time[0]);
        int minute = Integer.parseInt(time[1]);
        int second = Integer.parseInt(time[2]);
        return (hour <= 23 && minute <= 60 && second <= 60) && (hour >= 0 && minute >= 0 && second >= 0);
    }*/

}
