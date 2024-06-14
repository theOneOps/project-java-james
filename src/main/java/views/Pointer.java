package views;


import controllers.PointerController;
import javafx.geometry.Insets;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
//import pointeuse.model.ParameterSerialize;


import java.io.IOException;
import java.time.LocalTime;


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


    /**
     * Constructor for the Pointer class.
     */
    public Pointer(PointerController pointerController) {
        super();
        HBox buttons = new HBox();
        // Initialize the pointer controller
        this.pointerController = pointerController;


        // Create a combo box with all employees of ent e1
        //ComboBox combo_box = new ComboBox(pointerController.getEmployee());


        Button checkBtn = new Button("Check");
        Quit = new Button("Quit");


        Region spacerTwo = new Region();
        HBox.setHgrow(spacerTwo, Priority.ALWAYS);
        buttons.getChildren().addAll(Quit, spacerTwo, checkBtn);


        Region spacerThree = new Region();
        VBox.setVgrow(spacerThree, Priority.ALWAYS);


        this.getChildren().addAll(spacerThree, buttons);


        this.setPadding(new Insets(10));
        this.setSpacing(10);


        /*- Event -*/
        checkBtn.setOnAction(e -> {
            // Send local time to main app
            //ObservableList<Employee> emp = pointerController.getEmployee();
            //int index = combo_box.getSelectionModel().getSelectedIndex();
            try {
                pointerController.sendPendingData(pointerController.roundTime(LocalTime.now()).concat(";")
                        .concat("1"));
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            } catch (ClassNotFoundException ex) {
                throw new RuntimeException(ex);
            }
        });
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
     * Saves the new configuration pointer settings.
     *
     * @throws IOException if there is an issue with IO operations
     *
    public void saveNewConfigPointer() throws IOException {
    // Update the new IP and port values from the configuration input fields
    config.getNewIp().setLTFTextFieldValue(config.getNewIp().getLTFTextFieldValue());
    config.getNewPort().setLTFTextFieldValue(config.getNewPort().getLTFTextFieldValue());


    ParameterSerialize p = new ParameterSerialize();


    // Collect the new parameters
    ArrayList<String> newParams = new ArrayList<>();
    newParams.add(config.getNewIp().getLTFTextFieldValue());
    newParams.add(config.getNewPort().getLTFTextFieldValue());


    // Save the new parameters
    p.saveData(newParams);
    }
    /
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
