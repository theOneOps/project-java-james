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
import controllers.EmployeeController;
import javafx.stage.Popup;

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
    private EntrepriseCreation entrepriseCreation;
    private Popup popup;


    public EmployeeManagement(Stage stage){
        this.stage= stage;
        this.grid = new GridPane();
        this.entreprisecomboBox = new ComboBox<>();
        this.connectButtonCheck = new Button("Connexion");
        this.createEnterpriseButton = new Button("Create enterprise");
        this.QuitButtonClick = new Button("Quit");
        this.enterpriseLabel = new Label("Entreprise's name");
        this.popup = new Popup();
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


        QuitButtonClick.setOnAction(event -> {
            Stage stage = (Stage) QuitButtonClick.getScene().getWindow();
            stage.close();
        });

        connectButtonCheck.setOnAction(event -> connectEmployee());
        createEnterpriseButton.setOnAction(event -> openWindowEnterpriseCreation());

        Scene scene = new Scene(grid, 400, 300);
        stage.setScene(scene);
        stage.show();
    }

    public void connectEmployee() {
        //open EntrepriseView.
    }

    public void openWindowEnterpriseCreation() {
        if (!popup.isShowing()) {
            entrepriseCreation.start(new Stage());
        } else {
            popup.hide();
        }
    }

    public Stage getStage() {
        return stage;
    }

    public ComboBox<String> getEntreprisecomboBox() {
        return entreprisecomboBox;
    }

    public Button getConnectButtonCheck() {
        return connectButtonCheck;
    }

    public Button getCreateEnterpriseButton() {
        return createEnterpriseButton;
    }

    public Button getQuitButtonClick() {
        return QuitButtonClick;
    }

    public GridPane getGrid() {
        return grid;
    }

    public Label getEnterpriseLabel() {
        return enterpriseLabel;
    }
}





