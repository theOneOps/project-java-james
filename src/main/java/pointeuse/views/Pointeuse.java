package pointeuse.views;

import controllers.EmployeeController;
import controllers.EntrepriseController;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import model.JobClasses.Employee;
import model.JobClasses.Enterprise;




public class Pointeuse{
    private Stage stage;
    private GridPane grid;
    private Label date1label;
    private Label date2label;
    private Label timelabel;
    private Label time2label;
    private Label networklabel;
    private Label iplabel;
    private Label portlabel;
    private TextField ipTextField;
    private TextField portTextField;
    private TextField idTextField;
    private Button QuitButton;
    private Button checkInOutButtonClick;
    private ComboBox<String> userComboBox;
    private ComboBox<String> networkComboBox;
    private Enterprise enterprise;
    private Employee employee;

    private EntrepriseController entrepriseController;
    private  EmployeeController employeeController;

    public Pointeuse(Stage stage){
        this.stage = stage;
        this.grid = new GridPane();
        this.date1label = new Label("Date :");
        this.date2label= new Label();
        this.timelabel = new Label("Heure:");
        this.time2label = new Label();
        this.networklabel = new Label("Connection network :");
        this.iplabel =new Label("Ip:");
        this.portlabel = new Label("Port:");
        this.ipTextField= new TextField();
        this.idTextField= new TextField();
        this.portTextField=new TextField();
        this.QuitButton= new Button("Quit");
        this.checkInOutButtonClick=new Button("Check in / Check out");
        this.userComboBox=new ComboBox<>();
        this.networkComboBox = new ComboBox<>();

        this.employeeController = new EmployeeController();
        this.entrepriseController = new EntrepriseController();
    }
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Pointeuse Application");

        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25, 25, 25, 25));

        // Date and Time
        grid.add(date1label, 0, 0);
        grid.add(date2label, 0, 1);
        date2label.setText(LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
        grid.add(timelabel, 1, 0);
        grid.add(time2label, 1, 1);

        LocalTime time = LocalTime.now();
        // Arrondir à la prochaine minute multiple de 5
        int minute = time.getMinute();
        int roundedMinute = (minute + 4) / 5 * 5;
        LocalTime roundedTime = time.withMinute(roundedMinute);
        time2label.setText(LocalDateTime.now().format(DateTimeFormatter.ofPattern("HH:mm"))+" (Arrondi:" +roundedTime.format(DateTimeFormatter.ofPattern("HH:mm"))+')');//arrondi


        // Connection Network
        grid.add(networklabel, 0, 2);
        grid.add(networkComboBox, 1, 2);

        networkComboBox.setValue(enterprise.getEntname());


        // IP and Port
        grid.add(iplabel, 0, 3);
        ipTextField.setText("1");
        grid.add(ipTextField, 0, 4);
        grid.add(portlabel, 1, 3);
        grid.add(portTextField, 1, 4);

        // User Selection
        userComboBox.setValue(employee.getEmpName());
        grid.add(userComboBox, 1, 5);

        portTextField.setText("1");//handle data
        grid.add(idTextField, 1, 6);
        grid.add(QuitButton, 0, 7);
        grid.add(checkInOutButtonClick, 1, 7);

        QuitButton.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {
                Stage stage = (Stage) QuitButton.getScene().getWindow();
                stage.close();
            }
        });
        checkInOutButtonClick.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {
                Stage stage = (Stage) checkInOutButtonClick.getScene().getWindow();
                //send au controller....
            }
        });

        Scene scene = new Scene(grid, 400, 300);//might delete later
        stage.setScene(scene);
        stage.show();
    }


}