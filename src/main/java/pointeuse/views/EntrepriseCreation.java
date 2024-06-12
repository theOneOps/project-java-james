package pointeuse.views;

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
import model.JobClasses.Enterprise;


/**
 * @author madih
 * Entreprise Creation View for MainPage
 * used to create and add new entreprise.
 */

public class EntrepriseCreation {
    private Stage stage;
    private GridPane grid;
    private Label enterpriseLabel;
    private Label ipLabel;
    private Label portLabel;
    private TextField portTextField;
    private TextField ipTextField;
    private TextField enterpriseNameField;
    private Button quitButton;
    private Button createButton;


    public EntrepriseCreation(Stage stage){
        this.stage = stage;
        this.grid = new GridPane();
        this.quitButton = new Button("quit");
        this.createButton= new Button("Create");
        this.enterpriseLabel =new Label("Enterprise’s name:");
        this.ipLabel = new Label("IP:");
        this.portLabel = new Label("PORT:");
        this.portTextField = new TextField();
        this.ipTextField= new TextField();
        this.enterpriseNameField = new TextField();

    }
    public void start(Stage stage) {

        stage.setTitle("Enterprise Creation App");
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25, 25, 25, 25));


        grid.add(enterpriseLabel, 0, 1);
        grid.add(enterpriseNameField, 1, 1);
        grid.add(ipLabel, 0, 2);
        grid.add(ipTextField, 1, 2);
        grid.add(portLabel, 0, 3);
        grid.add(portTextField, 1, 3);
        grid.add(quitButton, 0, 4);
        grid.add(createButton, 1, 4);

        quitButton.setOnAction(event -> {
            Stage stage1 = (Stage) quitButton.getScene().getWindow();
            stage1.close();
        });
        createButton.setOnAction(event ->addNewEnterprise());
        Scene scene = new Scene(grid, 400, 300);
        stage.setScene(scene);
        stage.show();
    }
    public void addNewEnterprise() {
        String name = enterpriseNameField.getText();
        String port = portTextField.getText();

        Enterprise entreprise = new Enterprise(name, port);
        System.out.println("New Enterprise Added:");
        System.out.println(entreprise);
    }
}