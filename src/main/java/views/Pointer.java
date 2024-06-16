package views;


import controllers.PointerController;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.stage.Popup;
import javafx.stage.Stage;
//import pointeuse.model.ParameterSerialize;


import java.io.IOException;
import java.time.LocalTime;
import java.util.Objects;


/**
 * The Pointer class is a JavaFX component that includes date and time display,
 * employee selection, and buttons for configuration and check-in/out.<br>
 * Its attributes are:
 * <ul>
 *     <li>{@code DateHours}: ComponentDateHours - Component to display the current date and time</li>
 *     <li>{@code Employees}: LabeledComboHBox - Dropdown component for selecting employees</li>
 *     <li>{@code logincheckInOut}: AllBtns - Buttons for configuration and check-in/out actions</li>
 *     <li>{@code Quit}: Button - Button to quit the application</li>
 *     <li>{@code config}: ChangePointerConfig - Configuration component for changing pointer settings</li>
 * </ul>
 */
public class Pointer extends VBox {
    private Button Quit;                    // Button to quit the application
    private PointerController pointerController;

    private String port;
    private String ip;
    private TextField uuid;
    private Stage primaryStage;

    /**
     * Constructor for the Pointer class.
     * Initializes the UI components and sets up event handlers.
     *
     * @param primaryStage the primary stage of the JavaFX application.
     * @param pointerController the controller for managing pointer operations.
     */
    public Pointer(Stage primaryStage, PointerController pointerController) {
        super();
        this.primaryStage = primaryStage;
        this.port = "80";
        this.ip = "127.0.0.1";
        HBox buttons = new HBox();
        // Initialize the pointer controller
        this.pointerController = pointerController;

        //Create a combo box with all employees of ent e1
        //ComboBox combo_box = new ComboBox(pointerController.getEmployee());
        Label currentlyTimeArround = new Label();
        currentlyTimeArround.setText("Heure actuel arroundit au quart d'heure près: ".
                concat(pointerController.roundTime(LocalTime.now())));
        this.uuid = new TextField();
        this.uuid.setPromptText("Entrez l'uuid de l'employee");

        Button config = new Button("Config");
        Button checkBtn = new Button("Check");

        Region spacerTwo = new Region();
        HBox.setHgrow(spacerTwo, Priority.ALWAYS);
        buttons.getChildren().addAll(config, spacerTwo, checkBtn);


        Region spacerThree = new Region();
        VBox.setVgrow(spacerThree, Priority.ALWAYS);

        /*- Popup Config -*/
        Popup popup = new Popup();
        VBox boxPopup = new VBox(6);
        boxPopup.setAlignment(Pos.CENTER);
        boxPopup.setStyle(" -fx-background-color: white;");
        boxPopup.setMinWidth(550);
        boxPopup.setMinHeight(250);

        HBox boxBtn = new HBox(6);

        TextField portField = new TextField();
        portField.setPromptText("Port, ex: 80");
        TextField ipField = new TextField();
        ipField.setPromptText("Ip, ex: 127.0.0.1");

        Button cancel = new Button("Cancel");
        Button valid = new Button("Ok");

        boxBtn.getChildren().addAll(cancel, valid);
        boxPopup.getChildren().addAll(portField, ipField, boxBtn);
        popup.getContent().add(boxPopup);

        this.getChildren().addAll(currentlyTimeArround, uuid, spacerThree, buttons);

        this.setPadding(new Insets(10));
        this.setSpacing(10);

        /*- Event -*/
        checkBtn.setOnAction(e -> {
            // Send local time to main app
            //ObservableList<Employee> emp = pointerController.getEmployee();
            //int index = combo_box.getSelectionModel().getSelectedIndex();
            try {
                System.out.println(port.concat(ip));
                pointerController.sendPendingData(pointerController.roundTime(LocalTime.now()).concat(";")
                        .concat(getUuid()).concat(";").concat(port).concat(";").concat(ip));
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            } catch (ClassNotFoundException ex) {
                throw new RuntimeException(ex);
            }
        });

        config.setOnAction(e -> {
           if (!popup.isShowing()) popup.show(primaryStage);
        });

        valid.setOnAction(e -> {
            port = portField.getText();
            ip = ipField.getText();
            if (Objects.equals(port, "")) port = "80";
            if (Objects.equals(ip, "")) ip = "127.0.0.1";
            pointerController.setSocket(ip, port);
            popup.hide();
        });
        cancel.setOnAction(e -> { popup.hide(); });
    }


    /**
     * Gets the quit button.
     *
     * @return the quit button
     */
    public Button getQuit() {
        return Quit;
    }

    /**
     * Gets the UUID entered by the user.
     *
     * @return the UUID entered in the TextField.
     */
    public String getUuid() {
        return uuid.getText();
    }

    /**
     * Displays an alert with the specified title and content.
     *
     * @param title   the title of the alert
     * @param content the content of the alert
     */
    public static void PrintAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText("Important information!");
        alert.setTitle(title);
        alert.setContentText(content);


        alert.show();
    }




}
