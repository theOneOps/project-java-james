package controllers;

import model.JobClasses.Employee;
import model.JobClasses.Enterprise;

import java.util.ArrayList;

public class EmployeeController {

    public EmployeeController() {}

    /**
     * Description: Vérifie si les valeurs des champs sont valide
     * @return
     */
    public String updateEmployee(Enterprise enterprise, Employee emp, String nameVarText, String prenameVarText,
                                  String workHourStartVarText, String workHourEndVarText) {
        if (hourValide(workHourStartVarText, ":") && hourValide(workHourEndVarText, ":")) {
            Employee tmp = enterprise.getEmployees().get(emp.getUuid());
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

    /**
     * Description : Détermine si une heure est au format (hour:minute:second) et
     * si les valeurs de l'heure sont valide
     * (Ex: une heure ne peut être supérieur à 23 (ou 24 ~> 00))
     * @return Boolean : Vrai si l'heure est au bon format Sinon faux
     */
    public boolean hourValide(String param, String delimitor) {
        String[] time = param.split(delimitor);
        if (time.length != 3) return false;

        int hour = Integer.parseInt(time[0]);
        int minute = Integer.parseInt(time[1]);
        int second = Integer.parseInt(time[2]);
        return (hour <= 23 && minute <= 60 && second <= 60) && (hour >= 0 && minute >= 0 && second >= 0);
    }

}
