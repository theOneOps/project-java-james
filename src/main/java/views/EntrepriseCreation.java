package views;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import pointeuse.controllers.CreationEntrepriseController;

public class EntrepriseCreation extends Application {
    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Enterprise Creation App");

        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25, 25, 25, 25));

        // Enterprise's name
        Label enterpriseLabel = new Label("Enterprise’s name:");
        grid.add(enterpriseLabel, 0, 1);

        TextField enterpriseNameField = new TextField();
        grid.add(enterpriseNameField, 1, 1);

        // IP
        Label ipLabel = new Label("IP:");
        grid.add(ipLabel, 0, 2);

        TextField ipTextField = new TextField();
        grid.add(ipTextField, 1, 2);

        // Port
        Label portLabel = new Label("Port:");
        grid.add(portLabel, 0, 3);

        TextField portTextField = new TextField();
        grid.add(portTextField, 1, 3);

        // Instantiate the controller
        CreationEntrepriseController controller = new CreationEntrepriseController(
                enterpriseNameField,
                ipTextField,
                portTextField
        );

        // Buttons
        Button quitButton = new Button("Quit");
        Button createButton = new Button("Create");

        grid.add(quitButton, 0, 4);
        grid.add(createButton, 1, 4);

        quitButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Stage stage = (Stage) quitButton.getScene().getWindow();
                stage.close();
            }
        });

        createButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                controller.addNewEnterprise();
            }
        });

        Scene scene = new Scene(grid, 400, 300);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}

