package pointeuse.views;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import model.JobClasses.Employee;
import model.JobClasses.Enterprise;
import pointeuse.controllers.EmployeeManagementController;
import controllers.EmployeeController;

/**
 * @author madih
 * Employee Management View for Pointeuse
 * used to sign in or create a company if not created.
 */

public class EmployeeManagement {
    private Stage stage;
    private Button createEnterpriseButton;
    private Button connectButtonCheck;
    private Button QuitButtonClick;
    private ComboBox<String> entreprisecomboBox;
    private  GridPane grid;
    private Label enterpriseLabel;
    private Enterprise enterprise;
    private EmployeeController employeeController;
    private EmployeeManagementController employeeManagementController;

    public EmployeeManagement(Stage stage){
        this.stage= stage;
        this.grid = new GridPane();
        this.entreprisecomboBox = new ComboBox<>();
        this.connectButtonCheck = new Button("Connexion");
        this.createEnterpriseButton = new Button("Create enterprise");
        this.QuitButtonClick = new Button("Quit");
        this.enterpriseLabel = new Label("Entreprise's name");
        this.enterprise=enterprise;
        //this.employeeManagementController = new EmployeeManagementController();

    }

    public void content(){
        stage.setTitle("Employee Management App");

        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25, 25, 25, 25));


        grid.add(enterpriseLabel, 0, 1);

        entreprisecomboBox.setValue(enterprise.getEntname());;

        //position Buttons
        grid.add(createEnterpriseButton, 1, 4);
        grid.add(connectButtonCheck, 1, 3);
        grid.add(QuitButtonClick, 0, 6);


        QuitButtonClick.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {
                Stage stage = (Stage) QuitButtonClick.getScene().getWindow();
                stage.close();
            }
        });

        connectButtonCheck.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {
                employeeManagementController.connectEmployee();
            }
        });

        createEnterpriseButton.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {
                employeeManagementController.openWindowEnterpriseCreation();
            }
        });
        Scene scene = new Scene(grid, 400, 300);
        stage.setScene(scene);
        stage.show();
    }

}




