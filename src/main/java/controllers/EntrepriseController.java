package controllers;

import model.DataSerialize;
import model.JobClasses.Employee;
import views.EntrepriseView;
import java.util.ArrayList;
import model.JobClasses.Enterprise;

/**
 * @author Martin
 * Connection between the back-end and front-end of the application
 */
public class EntrepriseController {
     public EntrepriseController(){}

    public boolean updateEmployee(String enterprise, String uuid, String name, String prename,
                                  String workHourStart, String workHourEnd){
         try{
             DataSerialize.getInstance().modifyEmpPrename(enterprise,uuid, prename);
             DataSerialize.getInstance().modifyEmpName(enterprise, uuid, name);
             DataSerialize.getInstance().modifyEmpStartingHour(enterprise, uuid, workHourStart);
             DataSerialize.getInstance().modifyEmpEndingHour(enterprise, uuid, workHourEnd);
         }catch(Exception exception){
            return false;
        }
       return true;
    }
}
