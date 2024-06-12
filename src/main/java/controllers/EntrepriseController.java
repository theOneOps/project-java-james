package controllers;

import java.util.ArrayList;
import model.JobClasses.Enterprise;

/**
 * @author Martin
 * Connection between the back-end and front-end of the application
 */
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
