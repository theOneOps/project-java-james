package pointeuse.views;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;


public class Pointeuse extends Application {
    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Pointeuse Application");

        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25, 25, 25, 25));

        // Date and Time
        Label date1Label = new Label("Date :");
        grid.add(date1Label, 0, 0);
        Label date2Label = new Label();
        grid.add(date2Label, 0, 1);
        date2Label.setText(LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));


        Label timeLabel = new Label("Heure:");
        grid.add(timeLabel, 1, 0);
        Label time2Label = new Label();
        grid.add(time2Label, 1, 1);
        LocalTime time = LocalTime.now();
        // Arrondir à la prochaine minute multiple de 5
        int minute = time.getMinute();
        int roundedMinute = (minute + 4) / 5 * 5;
        LocalTime roundedTime = time.withMinute(roundedMinute);
        time2Label.setText(LocalDateTime.now().format(DateTimeFormatter.ofPattern("HH:mm"))+" (Arrondi:" +roundedTime.format(DateTimeFormatter.ofPattern("HH:mm"))+')');//arrondi


        // Connection Network
        Label networkLabel = new Label("Connection network :");
        grid.add(networkLabel, 0, 2);

        ComboBox<String> networkConnection = new ComboBox<>();
        networkConnection.getItems().addAll("polytechTours");
        networkConnection.setValue("polytechTours");
        grid.add(networkConnection, 1, 2);

        // IP and Port
        Label ipLabel = new Label("Ip:");
        grid.add(ipLabel, 0, 3);

        TextField ipTextField = new TextField();//à revoir
        ipTextField.setText("1");
        grid.add(ipTextField, 0, 4);

        Label portLabel = new Label("Port:");
        grid.add(portLabel, 1, 3);

        TextField portTextField = new TextField();
        grid.add(portTextField, 1, 4);

        // User Selection
        ComboBox<String> userComboBox = new ComboBox<>();//get hta hia for suuure!!
        userComboBox.getItems().addAll("John Doe");
        userComboBox.setValue("John Doe");
        grid.add(userComboBox, 1, 5);//center it

        TextField idTextField = new TextField();// center it
        portTextField.setText("1");//handle data
        grid.add(idTextField, 1, 6);

        // Buttons
        Button QuitButtonClick = new Button("Quit"  );
        Button checkInOutButtonClick = new Button("Check In / Check out");

        grid.add(QuitButtonClick, 0, 7);
        grid.add(checkInOutButtonClick, 1, 7);
        QuitButtonClick.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Stage stage = (Stage) QuitButtonClick.getScene().getWindow();
                stage.close();
            }
        });
        checkInOutButtonClick.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Stage stage = (Stage) checkInOutButtonClick.getScene().getWindow();
                //send au controller....
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
