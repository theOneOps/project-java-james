package controllers;

import javafx.scene.control.TextField;
import model.JobClasses.Enterprise;

public class CreationEntrepriseController {
    private TextField enterpriseNameField;
    private TextField ipTextField;
    private TextField portTextField;

    public CreationEntrepriseController(TextField enterpriseNameField, TextField ipTextField, TextField portTextField) {
        this.enterpriseNameField = enterpriseNameField;
        this.ipTextField = ipTextField;
        this.portTextField = portTextField;
    }

    public void addNewEnterprise() {
        String name = enterpriseNameField.getText();
        String ip = ipTextField.getText();
        String port = portTextField.getText();

        Enterprise entreprise = new Enterprise(name, ip, port);

        // Here you can add your logic to handle the new enterprise data
        System.out.println("New Enterprise Added:");
        System.out.println(entreprise);
    }
}
