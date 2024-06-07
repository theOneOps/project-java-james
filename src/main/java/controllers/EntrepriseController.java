package controllers;

import model.JobClasses.Employee;
import views.EntrepriseView;
import java.util.ArrayList;
import model.JobClasses.Enterprise;

public class EntrepriseController {
     public EntrepriseController(){}


    /**
     * static for now -> get real entreprises later
     * @return all entreprises
     */
    public ArrayList<Enterprise> getAllEntreprises(){
        ArrayList<Enterprise> entreprises = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
           entreprises.add(new Enterprise("Enterprise"+i));
        }
        return entreprises;
    }

}
