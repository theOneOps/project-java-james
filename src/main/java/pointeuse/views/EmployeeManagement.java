package pointeuse.views;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import pointeuse.controllers.EmployeeManagementController;

public class EmployeeManagement {
    private Stage primaryStage;
    public EmployeeManagement(Stage stage){
        this.primaryStage= stage;
    }

    public void content(){
        primaryStage.setTitle("Employee Management App");

        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25, 25, 25, 25));


        // Enterprise's name
        Label enterpriseLabel = new Label("Enterprise’s name :");
        grid.add(enterpriseLabel, 0, 1);

        /*ComboBox<String> enterpriseComboBox = new ComboBox<>();
        enterpriseComboBox.getItems().addAll();
        grid.add(enterpriseComboBox, 1, 1);*/ //gerer par le controller

        // Password
        Label passwordLabel = new Label("Password :");
        grid.add(passwordLabel, 0, 2);

        /*PasswordField passwordField = new PasswordField();
        passwordField.getText();
        grid.add(passwordField, 1, 2);*/ //gerer par le controller
        EmployeeManagementController employeeManagementcontroller = new EmployeeManagementController(
                // enterpriseComboBox
        );
        // Buttons
        Button connectButtonCheck = new Button("Connexion");
        grid.add(connectButtonCheck, 1, 3);

        Button createEnterpriseButton = new Button("Create enterprise");
        grid.add(createEnterpriseButton, 1, 4);

        Button QuitButtonClick = new Button("Quit");
        grid.add(QuitButtonClick, 0, 6);


        QuitButtonClick.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {
                Stage stage = (Stage) quitButton.getScene().getWindow();
                stage.close();
            }
        });

        connectButtonCheck.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {
                controller.connectEmployee();
            }
        });
        createEnterpriseButton.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {
                controller.openWindowEnterpriseCreation();
            }
        });


    });
    Scene scene = new Scene(grid, 400, 300);
        primaryStage.setScene(scene);
        primaryStage.show();

}


}

