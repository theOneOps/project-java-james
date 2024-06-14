package views;

import controllers.EntrepriseController;
import controllers.HomePageController;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Popup;
import javafx.stage.Stage;
import model.JobClasses.Enterprise;


import javafx.scene.text.Font;

import model.JobClasses.Employee;

import controllers.EmployeeController;
import model.JobClasses.WorkHour;
import model.JobClasses.WorkHourEntry;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Map;
import java.util.Objects;

/**
 * @author Alexandre
 * Enterprise view for mainApp
 * Used to show all the employees of an enterperise
 *
 */
public class EntrepriseView extends HomePageView {

    private Enterprise enterprise;
    private EntrepriseController entrepriseController;
    private final EmployeeController employeeController;

    private TableView<Employee> tableView;


    public EntrepriseView(Stage stage, Enterprise enterprise) {
        super(stage);
        this.enterprise = enterprise;
        this.employeeController = new EmployeeController(enterprise);
        this.entrepriseController = new EntrepriseController();
        stage.setTitle(enterprise.getEntname() + " View");

        // init view
        tableView = new TableView<>();
        initializeMainContent();
    }



    public void initializeMainContent() {
        //changed events on sidebar's buttons
        comboBox.setValue(enterprise.getEntname());
        paramButton.setOnAction(e -> {
            ParameterView parameterView = new ParameterView(stage);
            VBox vBox = parameterView.mainContent;
            switchToView(vBox);
        });
        homeButton.setOnAction(e -> {
            HomePageView homePageView = new HomePageView(stage);
            if( homePageController.getEmployees().isEmpty()) System.out.println("vide ");
            VBox vBox = homePageView.mainContent;
            homePageView.switchToView(vBox);
            System.out.println("homeButton dans entreprise view");
        });

        // Font Style
        Font titleFront = new Font(26);
        Font text = new Font(15);
        Font btnFont = new Font(16);

        // Top Element
        VBox topBox = new VBox(10);
        topBox.setAlignment(Pos.CENTER);
        //topBox.setPadding();
        // Create Elements
        Label title = new Label(enterprise.getEntname());
        title.setFont(titleFront);
        Separator separator1 = new Separator();

        topBox.getChildren().addAll(title, separator1);

        /*--- MID Element ---*/
        GridPane midGrid = new GridPane();
        midGrid.setStyle(" -fx-background-color: white;");
        RowConstraints rowMid = new RowConstraints();
        ColumnConstraints columnMid1 = new ColumnConstraints();
        ColumnConstraints columnMid2 = new ColumnConstraints();
        rowMid.setPercentHeight(100);
        columnMid1.setPercentWidth(50);
        columnMid2.setPercentWidth(50);
        midGrid.getColumnConstraints().addAll(columnMid1, columnMid2);
        midGrid.getRowConstraints().add(rowMid);
        // Table View
        initializeTableView();

        // Person Detail
        GridPane detailBox = new GridPane();

        double rowDetailSize = (double) 70/6;
        //System.out.println(rowDetailSize);
        RowConstraints rowDetail1 = new RowConstraints();
        rowDetail1.setPercentHeight(rowDetailSize);
        RowConstraints rowDetail2 = new RowConstraints();
        rowDetail2.setPercentHeight(rowDetailSize);
        RowConstraints rowDetail3 = new RowConstraints();
        rowDetail3.setPercentHeight(rowDetailSize);
        RowConstraints rowDetail4 = new RowConstraints();
        rowDetail4.setPercentHeight(rowDetailSize);
        RowConstraints rowDetail5 = new RowConstraints();
        rowDetail5.setPercentHeight(rowDetailSize);
        RowConstraints rowDetail6 = new RowConstraints();
        rowDetail6.setPercentHeight(rowDetailSize);
        RowConstraints rowDetail7 = new RowConstraints();
        rowDetail7.setPercentHeight(10);
        RowConstraints rowDetail8 = new RowConstraints();
        rowDetail7.setPercentHeight(20);
        //Column
        ColumnConstraints columnDetail1 = new ColumnConstraints();
        columnDetail1.setPercentWidth(35);
        ColumnConstraints columnDetail2 = new ColumnConstraints();
        columnDetail2.setPercentWidth(65);
        // Add Row & Column
        detailBox.getColumnConstraints().addAll(columnDetail1, columnDetail2);
        detailBox.getRowConstraints().addAll(rowDetail1, rowDetail2, rowDetail3, rowDetail4,
                rowDetail5, rowDetail6, rowDetail7, rowDetail8);

        // Create Element: Label & Btn
        Label subTitle = new Label("Person Details");
        subTitle.setFont(titleFront);

        Label uuid = new Label("UUID");
        Label uuidVarText = new Label();
        uuid.setFont(text);
        uuidVarText.setFont(text);

        Label name = new Label("Name");
        name.setFont(text);
        TextField nameVarText = new TextField();
        nameVarText.setFont(text);
        nameVarText.setMaxWidth(180);

        Label prename = new Label("Prename");
        TextField prenameVarText = new TextField();
        prename.setFont(text);
        prenameVarText.setFont(text);
        prenameVarText.setMaxWidth(180);

        Label workHourStart = new Label("Work Hour (Start):");
        workHourStart.setFont(text);
        TextField workHourStartVarText = new TextField();
        workHourStartVarText.setFont(text);
        workHourStartVarText.setMaxWidth(180);

        Label workHourEnd = new Label("Work Hour (End):");
        workHourEnd.setFont(text);
        TextField workHourEndVarText = new TextField();
        workHourEndVarText.setFont(text);
        workHourEndVarText.setMaxWidth(180);

        Label reponseLabel = new Label("");
        reponseLabel.setFont(text);

        // Btn manip employee
        Button workHourBtn = new Button("View Work Hour");
        Button editBtn = new Button("Edit");
        Button addBtn = new Button("Add employee");
        Button suppBtn = new Button("Remove employee");
        Button cpyBtn = new Button("Copy UUID");

        // Put Element into Grid
        GridPane.setRowIndex(subTitle, 0);
        GridPane.setColumnIndex(subTitle, 0);
        GridPane.setHalignment(subTitle, HPos.LEFT);

        GridPane.setRowIndex(uuid, 1);
        GridPane.setColumnIndex(uuid, 0);
        GridPane.setHalignment(uuid, HPos.LEFT);

        GridPane.setRowIndex(uuidVarText, 1);
        GridPane.setColumnIndex(uuidVarText, 1);
        GridPane.setHalignment(uuidVarText, HPos.LEFT);

        GridPane.setRowIndex(name, 2);
        GridPane.setColumnIndex(name, 0);
        GridPane.setHalignment(name, HPos.LEFT);

        GridPane.setRowIndex(nameVarText, 2);
        GridPane.setColumnIndex(nameVarText, 1);
        GridPane.setHalignment(nameVarText, HPos.LEFT);

        GridPane.setRowIndex(prename, 3);
        GridPane.setColumnIndex(prename, 0);
        GridPane.setHalignment(prename, HPos.LEFT);

        GridPane.setRowIndex(prenameVarText, 3);
        GridPane.setColumnIndex(prenameVarText, 1);
        GridPane.setHalignment(prenameVarText, HPos.LEFT);

        GridPane.setRowIndex(workHourStart, 4);
        GridPane.setColumnIndex(workHourStart, 0);
        GridPane.setHalignment(workHourStart, HPos.LEFT);

        GridPane.setRowIndex(workHourStartVarText, 4);
        GridPane.setColumnIndex(workHourStartVarText, 1);
        GridPane.setHalignment(workHourStartVarText, HPos.LEFT);

        GridPane.setRowIndex(workHourEnd, 5);
        GridPane.setColumnIndex(workHourEnd, 0);
        GridPane.setHalignment(workHourEnd, HPos.LEFT);

        GridPane.setRowIndex(workHourEndVarText, 5);
        GridPane.setColumnIndex(workHourEndVarText, 1);
        GridPane.setHalignment(workHourEndVarText, HPos.LEFT);

        //GridPane.setRowIndex(reponseLabel, 6);
        //GridPane.setColumnIndex(reponseLabel, 0);
        //GridPane.setHalignment(reponseLabel, HPos.LEFT);

        GridPane.setRowIndex(workHourBtn, 7);
        GridPane.setColumnIndex(workHourBtn, 0);
        GridPane.setHalignment(workHourBtn, HPos.LEFT);

        GridPane.setRowIndex(editBtn, 7);
        GridPane.setColumnIndex(editBtn, 1);
        GridPane.setHalignment(editBtn, HPos.RIGHT);

        GridPane.setRowIndex(addBtn, 8);
        GridPane.setColumnIndex(addBtn, 0);
        GridPane.setHalignment(addBtn, HPos.LEFT);
        GridPane.setRowIndex(suppBtn, 8);
        GridPane.setColumnIndex(suppBtn, 1);
        GridPane.setHalignment(suppBtn, HPos.LEFT);

        GridPane.setRowIndex(cpyBtn, 8);
        GridPane.setColumnIndex(cpyBtn, 1);
        GridPane.setHalignment(cpyBtn, HPos.RIGHT);

        detailBox.add(reponseLabel, 0, 6, 2, 1);

        detailBox.getChildren().addAll(uuid, uuidVarText, name, nameVarText, prename,
                prenameVarText, workHourStart, workHourStartVarText,
                workHourEnd, workHourEndVarText, workHourBtn, editBtn, addBtn, suppBtn, cpyBtn);

        // Add grid into midBox
        GridPane.setRowIndex(tableView, 0);
        GridPane.setColumnIndex(tableView, 0);
        GridPane.setHalignment(tableView, HPos.LEFT);

        GridPane.setRowIndex(detailBox, 0);
        GridPane.setColumnIndex(detailBox, 1);
        GridPane.setHalignment(detailBox, HPos.RIGHT);

        midGrid.setHgap(8);
        midGrid.setMaxHeight(Double.MAX_VALUE);
        VBox.setVgrow(midGrid, Priority.ALWAYS);
        midGrid.getChildren().addAll(tableView, detailBox);

        /*
        * Popup avec tableau pour afficher tous les pointages d'un employé
        * */
        Popup popup = new Popup();
        VBox boxPopup = new VBox(6);
        boxPopup.setAlignment(Pos.CENTER);
        boxPopup.setStyle(" -fx-background-color: white;");
        boxPopup.setMinWidth(550);
        boxPopup.setMinHeight(250);

        // Table view
        TableView<LocalTime> table = new TableView<>();
        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY_ALL_COLUMNS);

        final TableColumn<LocalTime, String> pinting = new TableColumn<>("Pointage");
        pinting.setCellValueFactory(param -> {
            final LocalTime lt = param.getValue();
            return new SimpleStringProperty(lt.toString());
        });

        table.getColumns().setAll(pinting);

        Button quitBtn = new Button("Quit");
        boxPopup.getChildren().addAll(table, quitBtn);
        popup.getContent().add(boxPopup);

        /*- Popup Add Employee -*/
        Popup popupAdd = new Popup();
        GridPane gridPopupAdd = new GridPane();
        gridPopupAdd.setStyle(" -fx-background-color: GAINSBORO;");
        gridPopupAdd.setPadding(new Insets(15, 15, 15, 15));
        gridPopupAdd.setVgap(8);
        gridPopupAdd.setHgap(10);

        Label titlePopup = new Label("Create Employee in ".concat(enterprise.getEntname()));
        titlePopup.setFont(titleFront);

        Label newDepartment = new Label("Department");
        GridPane.setConstraints(newDepartment, 0, 1);
        TextField newDepartmentVarText = new TextField();
        GridPane.setConstraints(newDepartmentVarText, 1, 1);
        newDepartment.setFont(text);
        newDepartmentVarText.setFont(text);

        Label newName = new Label("Name");
        GridPane.setConstraints(newName, 0, 2);
        TextField newNameVarText = new TextField();
        GridPane.setConstraints(newNameVarText, 1, 2);
        newName.setFont(text);
        newNameVarText.setFont(text);
        newNameVarText.setMaxWidth(180);

        Label newPrename = new Label("Prename");
        GridPane.setConstraints(newPrename, 0, 3);
        TextField newPrenameVarText = new TextField();
        GridPane.setConstraints(newPrenameVarText, 1, 3);
        newPrename.setFont(text);
        newPrenameVarText.setFont(text);
        newPrenameVarText.setMaxWidth(180);

        Label newWorkHourStart = new Label("Work Hour (Start):");
        GridPane.setConstraints(newWorkHourStart, 0, 4);
        workHourStart.setFont(text);
        TextField newWorkHourStartVarText = new TextField();
        GridPane.setConstraints(newWorkHourStartVarText, 1, 4);
        workHourStartVarText.setFont(text);
        workHourStartVarText.setMaxWidth(180);

        Label response = new Label("Label response");

        Label newWorkHourEnd = new Label("Work Hour (End):");
        GridPane.setConstraints(newWorkHourEnd, 0, 5);
        workHourEnd.setFont(text);
        TextField newWorkHourEndVarText = new TextField();
        GridPane.setConstraints(newWorkHourEndVarText, 1, 5);
        workHourEndVarText.setFont(text);
        workHourEndVarText.setMaxWidth(180);

        Button create = new Button("Create");
        GridPane.setConstraints(create, 0, 7);
        //GridPane.setConstraints(quitBtn, 2, 7);

        gridPopupAdd.add(response, 0, 6, 2, 1);

        gridPopupAdd.getChildren().addAll(titlePopup, newDepartment, newDepartmentVarText, newName, newNameVarText, newPrename,
                newPrenameVarText, newWorkHourStart, newWorkHourStartVarText, newWorkHourEnd,
                newWorkHourEndVarText, create);
        popupAdd.getContent().add(gridPopupAdd);

        /*- Event -*/
        // Event Table View
        tableView.setOnMouseClicked(e -> {
            if (tableView.getSelectionModel().getSelectedItem() != null) {
                nameVarText.setText(tableView.getSelectionModel().getSelectedItem().getEmpName());
                prenameVarText.setText(tableView.getSelectionModel().getSelectedItem().getEmpPrename());
                uuidVarText.setText(tableView.getSelectionModel().getSelectedItem().getUuid());
                workHourStartVarText.setText(tableView.getSelectionModel().getSelectedItem().getStartingHour());
                workHourEndVarText.setText(tableView.getSelectionModel().getSelectedItem().getEndingHour());

            }
        });

        workHourBtn.setOnAction(e -> {
            if (!popup.isShowing()) {
                table.getItems().setAll(employeeController.getWorkHour(uuidVarText.getText()));
                popup.show(stage);
            }
            else popup.hide();
        });

        quitBtn.setOnAction(e -> {
            if (popupAdd.isShowing()) popupAdd.hide();
            if (popup.isShowing()) popup.hide();
        });

        addBtn.setOnAction(e -> {
            if (!popupAdd.isShowing()) {
                popupAdd.show(stage);
            } else popupAdd.hide();
        });

        editBtn.setOnAction(e -> {
            if (!uuidVarText.getText().isEmpty()) {
                Employee emp = enterprise.getEmployees().get(tableView.getSelectionModel().getSelectedItem().getUuid());
                String tmp = employeeController.updateEmployee(emp, nameVarText.getText(), prenameVarText.getText(),
                        workHourStartVarText.getText(), workHourEndVarText.getText());
                reponseLabel.setText(tmp);
                //Reload table view
                reloadTableview();
            }
            else {
                reponseLabel.setText("Veuillez séléctionner un employé dans le tableau.");
            }
        });

        create.setOnAction(e -> {
            employeeController.addEmployee(newNameVarText.getText(), newPrenameVarText.getText(),
                    newWorkHourStartVarText.getText(), newWorkHourEndVarText.getText(), "Info");
            reloadTableview();
        });

        suppBtn.setOnAction(e -> {
            boolean res = employeeController.removeEmployee(uuidVarText.getText());
            reloadTableview();
        });

        cpyBtn.setOnAction(e ->{
            employeeController.copyIdToClipBoard(uuidVarText.getText());
        });
        // Add element into grid
        VBox mainBox = new VBox();
        mainBox.setMaxHeight(Double.MAX_VALUE);
        VBox.setVgrow(mainBox, Priority.ALWAYS);
        mainBox.getChildren().addAll(topBox, midGrid);
        VBox finalBox = new VBox();
        finalBox.getChildren().add(mainBox);

        switchToView(finalBox);
    }

    public void initializeTableView() {
        // TableView
        tableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY_ALL_COLUMNS);
        tableView.setEditable(false);

        final TableColumn<Employee, String> nameCol = new TableColumn<>("Name");
        nameCol.setCellValueFactory(param -> {
            final Employee employee = param.getValue();
            return new SimpleStringProperty(employee.getEmpName());
        });

        final TableColumn<Employee, String> prenameCol = new TableColumn<>("Prename");
        prenameCol.setCellValueFactory(param -> {
            final Employee employee = param.getValue();
            return new SimpleStringProperty(employee.getEmpPrename());
        });

        final TableColumn<Employee, String> uuid = new TableColumn<>("UUID");
        uuid.setCellValueFactory(param -> {
            final Employee employee = param.getValue();
            return new SimpleStringProperty(employee.getUuid());
        });

        tableView.getColumns().setAll(nameCol, prenameCol, uuid);
        tableView.getItems().setAll(employeeController.getEmployee());
    }

    public void reloadTableview(){
        tableView.getItems().setAll(employeeController.getEmployee());
    }

}
