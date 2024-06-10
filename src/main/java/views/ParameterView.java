package views;

import controllers.HomePageController;
import controllers.ParameterController;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.scene.control.Slider;

import javax.swing.*;

/**
 * @author Martin
 * Parameter view for mainApp
 * Used to create enterprises.
 * {@link #initializeMainContent()}
 */
public class ParameterView extends HomePageView {
    private final ParameterController parameterController;

    public ParameterView(Stage stage, HomePageController controller) {
        super(stage, controller);
        this.parameterController = new ParameterController();
        stage.setTitle("Parameter View");
        initializeMainContent();
    }

    /**
     * Creates new content like a form to create new Entreprises.
     * The employee information is used as a template to create the fictitious employees
     */
    public void initializeMainContent() {

        VBox vbox = new VBox();
        Label errorLabel = new Label();
        GridPane grid = new GridPane();
        grid.setPadding(new Insets(10, 10, 10, 10));
        grid.setVgap(8);
        grid.setHgap(10);

        // Company Name
        Label companyNameLabel = new Label("Company Name:");
        GridPane.setConstraints(companyNameLabel, 0, 0);
        TextField companyNameInput = new TextField();
        companyNameInput.setPromptText("Company name");
        GridPane.setConstraints(companyNameInput, 1, 0);

        // Company port
        Label portLabel = new Label("Company port:");
        GridPane.setConstraints(portLabel, 0, 1);
        TextField portImput = new TextField();
        portImput.setPromptText("Company port ( e.g., 80)");
        GridPane.setConstraints(portImput, 1, 1);

        //Title
        Label titleEmployee = new Label("Employee desc :");
        GridPane.setConstraints(titleEmployee, 0, 2);
        // Employee Name
        Label employeeNameLabel = new Label("Employee Name:");
        GridPane.setConstraints(employeeNameLabel, 0, 3);
        TextField employeeNameInput = new TextField();
        employeeNameInput.setPromptText("Employee name");
        GridPane.setConstraints(employeeNameInput, 1, 3);

        // Employee Prename
        Label employeePrenameLabel = new Label("Employee Prename:");
        GridPane.setConstraints(employeePrenameLabel, 0, 4);
        TextField employeePrenameInput = new TextField();
        employeePrenameInput.setPromptText("Employee prename");
        GridPane.setConstraints(employeePrenameInput, 1, 4);


        // Employee Starting Hour
        Label employeeStartingHourLabel = new Label("Starting Hour:");
        GridPane.setConstraints(employeeStartingHourLabel, 0, 5);
        TextField employeeStartingHourInput = new TextField();
        employeeStartingHourInput.setPromptText("Starting hour (e.g., 09:00:14)");
        GridPane.setConstraints(employeeStartingHourInput, 1, 5);

        // Employee Ending Hour
        Label employeeEndingHourLabel = new Label("Ending Hour:");
        GridPane.setConstraints(employeeEndingHourLabel, 0, 6);
        TextField employeeEndingHourInput = new TextField();
        employeeEndingHourInput.setPromptText("Ending hour (e.g., 17:00:14)");
        GridPane.setConstraints(employeeEndingHourInput, 1, 6);


        // Number of Employees
        Label numberOfEmployeesLabel = new Label("Number of Employees:");
        GridPane.setConstraints(numberOfEmployeesLabel, 0, 7);

        Slider numberOfEmployees = new Slider(1, 100, 1);
        GridPane.setConstraints(numberOfEmployees, 1, 7);
        //Cast value of slider into integer
        numberOfEmployees.valueProperty().addListener((obs, oldVal, newVal) -> {
            numberOfEmployees.setValue(newVal.intValue());
        });

        Label selectedLabel = new Label(String.valueOf(numberOfEmployees.getValue()));
        GridPane.setConstraints(selectedLabel, 2, 7);
        //event on the SlideBar to get its value at any time
        numberOfEmployees.valueProperty().addListener(
                new ChangeListener<Number>() {
                    @Override
                    public void changed(ObservableValue<? extends Number> observableValue, Number number, Number t1) {
                        selectedLabel.setText(t1.toString());
                    }
                }
        );

        // Create Button
        Button createButton = new Button("Create Company and Employees");
        GridPane.setConstraints(createButton, 1, 8);

        //event on create button
        createButton.setOnAction(e -> {
            String companyName = companyNameInput.getText();
            String companyPort = portImput.getText();
            String employeeName = employeeNameInput.getText();
            String employeePrename = employeePrenameInput.getText();
            String startingHour = employeeStartingHourInput.getText();
            String endingHour = employeeEndingHourInput.getText();
            int numberEmployees = (int) numberOfEmployees.getValue();


            if (companyName.isEmpty() || employeeName.isEmpty() || employeePrename.isEmpty()
                    || startingHour.isEmpty() || endingHour.isEmpty() || companyPort.isEmpty()) {
                //check if any field is empty
                errorLabel.setText("Please fill all the fields");

            } else if ((!parameterController.checkLocalTimeRegex(startingHour) || !parameterController.checkLocalTimeRegex(endingHour))) {
                //check if regex on starting/ending hour
                System.out.println("ERROR PATTERN MATCHING");
                errorLabel.setText("Please enter a valid time range. (e.g., 17:00:14) ");
            } else {
                //Controller function use try catch -> inform the user if bug occurred
                if (!parameterController.createEnterprise(companyName, companyPort, employeeName, employeePrename, startingHour, endingHour, numberEmployees)) {
                    errorLabel.setText("ERROR CREATING EMPLOYEES RETRY");
                }
            }
        });

        //add all the elements onto the grid
        grid.getChildren().addAll(companyNameLabel, companyNameInput, portLabel, portImput, titleEmployee,
                employeeNameLabel, employeeNameInput, employeePrenameLabel, employeePrenameInput,
                employeeStartingHourLabel, employeeStartingHourInput,
                employeeEndingHourLabel, employeeEndingHourInput, numberOfEmployeesLabel, numberOfEmployees, selectedLabel, errorLabel, createButton);

        //add grid inside a VBox ( == mainContent)
        vbox.getChildren().addAll(grid, errorLabel);
        switchToView(vbox);

        //Changed events on the sideBar buttons
        paramButton.setOnAction(e -> {
            HomePageView homePageView = new HomePageView(stage, homePageController);
            VBox vBox = homePageView.mainContent;
            homePageView.switchToView(vBox);
        });
        homeButton.setOnAction(e -> {
            HomePageView homePageView = new HomePageView(stage, homePageController);
            VBox vBox = homePageView.mainContent;
            homePageView.switchToView(vBox);
        });
    }


}
