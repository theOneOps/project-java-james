package views;

import controllers.EntrepriseController;
import controllers.HomePageController;
import javafx.application.Platform;
import javafx.beans.Observable;
import javafx.beans.property.SimpleListProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import model.DataSerialize;
import model.JobClasses.Employee;
import model.JobClasses.Enterprise;

import javax.swing.*;
import java.io.IOException;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Objects;

import static java.lang.Thread.sleep;

/**
 * @author Martin
 * Main view for mainApp.
 * it's composed of a SideBar and a mainContent, see {@link #createSideBar()} and {@link #createSideBar()}
 * To see how elements are placed look {@link #initializeView()}
 * when a view change is needed, only the main content is replaced {@link #switchToView(VBox)}
 */
public class HomePageView {
    protected Stage stage;                              //stage of the app.
    protected Button paramButton;                       //custom parameter button
    protected Button homeButton;                        //custom home button
    protected TableView<Employee> tableView;            //table view changing content depending on the view instantiation
    protected ComboBox<String> comboBox;                //all entreprises
    protected GridPane mainPane;                        //use to separate app in 2, first part for sideBar(1/5), second for mainContent(4/5)
    protected GridPane sideBar;                         //custom sideBar
    protected VBox mainContent;                         //custom mainContent
    protected TextField searchField;                    //custom search field
    protected HomePageController homePageController;    //controller for home^Page
    private ObservableList<Employee> observableEmployee;//all employees in an observable list to put in the tableView
    private ObservableList<Enterprise> observableEnterprise;

    public HomePageView(Stage stage) {
        this.stage = stage;
        this.paramButton = new Button();
        this.homeButton = new Button();
        this.tableView = new TableView<>();
        this.comboBox = new ComboBox<>();
        this.mainPane = new GridPane();
        this.sideBar = new GridPane();
        this.mainContent = new VBox();
        this.searchField = new TextField();
        this.homePageController = new HomePageController();
        this.observableEmployee = homePageController.getEmployees();
        this.observableEnterprise = homePageController.getEntreprises();
        for(Employee emp : this.observableEmployee){
            System.out.println(emp);
        }
        tableView.setItems(observableEmployee);
        initializeView();
    }

    /**
     *  Initialize all elements and put them in a specific order on the stage
     */
    public void initializeView() {
        //use stack pane to have multiple scene in the same stage.
        StackPane stackPane = new StackPane();
        stackPane.getChildren().add(mainPane);
        //create scene and add stackPane to it
        Scene scene = new Scene(stackPane, 1000d, 700d);
        stage.setScene(scene);
        stage.setTitle("HomePage view");
        //event on quit button, forced save of the data.
        stage.setOnCloseRequest(e -> {
            try {
                DataSerialize.getInstance().saveData();
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
            Platform.exit();
        });

        sideBar = createSideBar();
        mainContent = createMainContent();

        createParameterButton();
        createHomeButton();

        //add padding and margin to the mainPain
        mainPane.setHgap(10);
        mainPane.setVgap(10);
        mainPane.setStyle("-fx-padding: 10;");

        //constraint column gridePane
        GridPane.clearConstraints(mainPane);
        ColumnConstraints col1 = new ColumnConstraints();
        ColumnConstraints col2 = new ColumnConstraints();
        col1.setPercentWidth(15); // 15% of stage
        col2.setPercentWidth(85); // 85% of stage
        col1.setHgrow(Priority.ALWAYS);
        col2.setHgrow(Priority.ALWAYS);


        mainPane.getColumnConstraints().addAll(col1, col2);

        //add sideBar on the first column of the grid
        mainPane.add(sideBar, 0, 0);
        //add mainContent on the second column of the grid
        mainPane.add(mainContent, 1, 0);

        //Configures interface layout to allow maximum expansion of elements inside the grid.
        GridPane.setHgrow(sideBar, Priority.ALWAYS);
        GridPane.setVgrow(sideBar, Priority.ALWAYS);
        GridPane.setHgrow(mainContent, Priority.ALWAYS);
        GridPane.setVgrow(mainContent, Priority.ALWAYS);
        VBox.setVgrow(tableView, Priority.ALWAYS);

        //show the interface
        switchToView(mainContent);
        stage.show();
    }

    /**
     * Create VBox for the main page with table view
     * @return VBox with main content
     */
    public VBox createMainContent() {

        //create main VBox
        VBox mainBox = new VBox();
        mainBox.minWidth(600);
        VBox.setVgrow(tableView, Priority.ALWAYS);
        VBox.setVgrow(mainBox, Priority.ALWAYS);

        createSearchField();
        initializeTableView();
        mainBox.getChildren().addAll(searchField, tableView);

        //margin and padding
        mainBox.setPadding(new Insets(10));
        mainBox.setSpacing(10);
        return mainBox;
    }

    /**
     * Initialize the tableVIew and its content
     */
    private void initializeTableView() {
        // set ColumnResizePolicy to allow maximum column width expansion
        tableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY_ALL_COLUMNS);
        // no edition of the colummn
        tableView.setEditable(false);

        TableColumn<Employee, String> uuidColumn = new TableColumn<>("UUID");
        uuidColumn.setCellValueFactory(param -> {
            final Employee employee = param.getValue();
            return new SimpleStringProperty(employee.getUuid());
        });

        TableColumn<Employee, String> firstNameCol = new TableColumn<>("First Name");
        firstNameCol.setCellValueFactory(param -> {
            final Employee temp = param.getValue();
            return new SimpleStringProperty(temp.getEmpPrename());
        });

        TableColumn<Employee, String> lastNameCol = new TableColumn<>("Last Name");
        lastNameCol.setCellValueFactory(param -> {
            final Employee temp = param.getValue();
            return new SimpleStringProperty(temp.getEmpName());
        });

        TableColumn<Employee, String> arrivalTime = new TableColumn<>("arrival time");
        arrivalTime.setCellValueFactory(param -> {
            final Employee employee = param.getValue();
            return new SimpleStringProperty(employee.getStartingHour());
        });

        TableColumn<Employee, String> departureTime = new TableColumn<>("departure time");
        departureTime.setCellValueFactory(param -> {
            final Employee employee = param.getValue();
            return new SimpleStringProperty(employee.getEndingHour());
        });

        TableColumn<Employee, String> lastRegisterColumn = new TableColumn<>("last register");
        lastRegisterColumn.setCellValueFactory(param -> {
            final Employee employee = param.getValue();
            return new SimpleStringProperty(String.valueOf(employee.getWorkHour().getLastPointing()));
        });

        //add all columns to the tableVIew
        tableView.getColumns().addAll(uuidColumn, firstNameCol, lastNameCol, arrivalTime, departureTime, lastRegisterColumn);

    }

    /**
     * Create parameter button and its events
     */
    public void createParameterButton() {
        //padding to button
        this.paramButton.setPadding(new javafx.geometry.Insets(10, 10, 10, 10));
        //adding image to button
        javafx.scene.image.Image parameterPNG = new Image(Objects.requireNonNull(getClass().getResource("/icons/parameter-icon.png")).toString());
        ImageView imageView = new ImageView(parameterPNG);
        this.paramButton.setGraphic(imageView);

        //remove button frame
        this.paramButton.setPadding(Insets.EMPTY);
        this.paramButton.setStyle("-fx-background-color: transparent; -fx-border-color: transparent;");
        //on click paramButton to change pane to parameter pane
        paramButton.setOnAction(e -> {
            ParameterView parameterView = new ParameterView(stage);
            VBox parameterContent = parameterView.mainContent;
            switchToView(parameterContent);
        });

    }

    /**
     * Create homeButton and its events
     */
    private void createHomeButton() {
        //padding to button
        this.homeButton.setPadding(new javafx.geometry.Insets(10, 10, 10, 10));
        //adding image to button
        javafx.scene.image.Image parameterPNG = new Image(Objects.requireNonNull(getClass().getResource("/icons/home-icon.png")).toString());
        ImageView imageView = new ImageView(parameterPNG);
        this.homeButton.setGraphic(imageView);

        //remove button frame
        this.homeButton.setPadding(Insets.EMPTY);
        this.homeButton.setStyle("-fx-background-color: transparent; -fx-border-color: transparent;");

        //event of button
        homeButton.setOnAction(e -> {
            ArrayList<Employee> emps = new ArrayList<>(observableEmployee);
            reloadTableview(emps);
        });
    }

    /**
     * Create sideBar
     * @return sideBar
     */
    public GridPane createSideBar() {
        //def SideBar
        GridPane sideBar = new GridPane();
        //constraint
        RowConstraints row0 = new RowConstraints();
        row0.setVgrow(Priority.ALWAYS);
        row0.setPercentHeight(20);  // 1/5 height
        //combobox
        fillComboBox();
        RowConstraints row1 = new RowConstraints();
        row1.setVgrow(Priority.ALWAYS);
        row1.setPercentHeight(60);  // 3/5 height
        //parameterButton
        RowConstraints row2 = new RowConstraints();
        row2.setVgrow(Priority.ALWAYS);
        row2.setPercentHeight(20);  // 1/5 height

        sideBar.getRowConstraints().addAll(row0, row1, row2);
        //home button
        homeButton.setPadding(new Insets(10, 10, 10, 10));
        //center content GirdPane
        sideBar.setAlignment(Pos.TOP_CENTER);
        sideBar.setHgap(10);
        sideBar.setVgap(10);
        sideBar.add(homeButton, 0, 0);
        sideBar.add(comboBox, 0, 1);
        sideBar.add(paramButton, 0, 2);
        GridPane.setValignment(comboBox, VPos.TOP);
        return sideBar;
    }

    /**
     *  remplace the mainContent VBox to a newContent VBox. used to switch between views
     * @param newContent
     */
    public void switchToView(VBox newContent) {
        mainPane.getChildren().remove(mainContent);
        mainPane.add(newContent, 1, 0);

        GridPane.setHgrow(mainContent, Priority.ALWAYS);
        GridPane.setVgrow(mainContent, Priority.ALWAYS);
    }

    /**
     * Fill the combobox with all enterprises and set event
     */
    public void fillComboBox() {
        for(Enterprise ent : observableEnterprise){
            comboBox.getItems().add(ent.getEntname());
        }

        if(!observableEnterprise.isEmpty()) {
            comboBox.setValue("select enterprise");
        }

        //Event on click
        comboBox.setOnAction(e -> {
            String enterpriseName = comboBox.getValue();
            //check all enterprises name in array
            for(Enterprise ent : observableEnterprise){
                if(ent.getEntname().equals(enterpriseName)){
                    EntrepriseView entrepriseView = new EntrepriseView(stage, ent);
                    VBox entrepriseContent = entrepriseView.mainContent;
                    switchToView(entrepriseContent);
                    System.out.println("selected enterprise : " + ent);
                    break;
                }
            }

        });
        // create vBox to contain ComboBox
        VBox vBox = new VBox(comboBox);
        vBox.setSpacing(10);
    }

    /**
     * Create the searchField and its event
     * @return searchField
     */
    public TextField createSearchField(){
        searchField.setPromptText("search emp by name/prename");
        //event
        //search when enter button pressed
        searchField.setOnAction(event ->{
            String text = searchField.getText();
            ArrayList<Employee> emps = searchEmployeeByPreName(text, observableEmployee);
            if(! emps.isEmpty()){
                //first search by preName
                reloadTableview(emps);

            }else{
                //second search by name
                emps = searchEmployeeByName(text, observableEmployee);
                if(!emps.isEmpty()){
                    reloadTableview(emps);
                }
            }
        });
        return searchField;
    }

    /**
     * Call Controller function to search employees by name
     * @param name
     * @return found employees
     */
    public ArrayList<Employee> searchEmployeeByName(String name, ObservableList<Employee> employees){
        return homePageController.searchEmployeeByName(name, employees);
    };

    /**
     Call Controller function to search employees by pre name
     * @param prename
     * @return found employees
     */
    public ArrayList<Employee> searchEmployeeByPreName(String prename, ObservableList<Employee> employees){
        return homePageController.searchEmployeeByPreName(prename, employees);
    }

    /**
     * When the employees to show in the tableView are changed, update the content of the tableView
     * @param emps employees to put inside the tableVIew
     */
    public void reloadTableview(ArrayList<Employee>emps){
        tableView.setItems( FXCollections.observableArrayList(emps));
    }
}

