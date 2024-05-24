package mainPoint;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import model.Employee;

import javax.xml.crypto.dsig.spec.XSLTTransformParameterSpec;
import java.io.IOException;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        stage.setTitle("HOME PAGE VIEW");
        stage.setOnCloseRequest(e -> Platform.exit());

        //def gridPane
        GridPane gridPane = new GridPane();
        gridPane.setHgap(10);
        gridPane.setVgap(10);
        gridPane.setStyle("-fx-padding: 10;");

        //constraint column gridePane
        ColumnConstraints col1 = new ColumnConstraints();
        ColumnConstraints col2 = new ColumnConstraints();

        col1.setPercentWidth(25); // 1/4 of stage
        col2.setPercentWidth(75); // 3/4 of stage
        gridPane.getColumnConstraints().addAll(col1, col2);

        //def SideBar
        VBox sideBar = new VBox();
        Button b1 = new Button("test1");
        //padding to button
        b1.setPadding(new Insets(10, 10, 10, 10));


        Button b2 = new Button("test2");
        b2.setPadding(new Insets(10, 10, 10, 10));
        Button b3 = new Button("test3");
        b3.setPadding(new Insets(10, 10, 10, 10));
        sideBar.getChildren().addAll(b1, b2, b3);
        //center content in vBox
        sideBar.setAlignment(Pos.TOP_CENTER);
        sideBar.setSpacing(15);

        //add sideBar to gridPane
        gridPane.add(sideBar, 0, 0);

        VBox mainBox = createMainContent();
        //add main content to grif pane
        gridPane.add(mainBox, 1, 0);

        // Définir le redimensionnement
        GridPane.setHgrow(mainBox, Priority.ALWAYS);
        GridPane.setVgrow(mainBox, Priority.ALWAYS);
        GridPane.setHgrow(sideBar, Priority.ALWAYS);
        GridPane.setVgrow(sideBar, Priority.ALWAYS);
        //use stack pane to have multiple scene in the same stage.
        StackPane stackPane = new StackPane();
        stackPane.getChildren().add(gridPane);

        //envent on btn1 to change scene
        b1.setOnAction(e->{
            if(!stackPane.getChildren().contains(mainBox)){
                stackPane.getChildren().clear();
                stackPane.getChildren().setAll(createSecondaryContent());
                stage.setTitle("CHANGE IN STACK PANE");
            }
        });

        b2.setOnAction(e->{
            if(!stackPane.getChildren().contains(mainBox)){
                stage.setTitle("HOME PAGE VIEW");
            }
        });

        //create scene and add stackPane to it
        Scene scene = new Scene(stackPane, 800, 600);
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }

    /**
     * Create VBox for the main page with table view an initailize data init
     * @return VBox with main content
     */
    public VBox createMainContent(){
        //main box
        VBox mainBox = new VBox();
        Label label = new Label("Main");
        VBox tableBox = new VBox();
        TableView<Employee> tableView = new TableView<>();

        //collection list
        ObservableList<Employee> data = FXCollections.observableArrayList();
        data.add(new Employee("John", "Doe"));
        data.add(new Employee("Jane", "Doe"));
        data.add(new Employee("Jack", "Doe"));

        //def column of table view
        TableColumn<Employee, String> firstNameColumn = new TableColumn<>("First Name");
        firstNameColumn.setCellValueFactory(new PropertyValueFactory<>("first name"));
        TableColumn<Employee, String> lastNameColumn = new TableColumn<>("Last Name");
        lastNameColumn.setCellValueFactory(new PropertyValueFactory<>("Last Name"));
        //adding gata to the table
        tableView.setItems(data);
        //adding column to the table
        tableView.getColumns().addAll(firstNameColumn, lastNameColumn);
        //adding table to the VBox
        mainBox.getChildren().addAll(label, tableView);
        return mainBox;
    }

    /**
     * Create VBox for the secondary page
     * @return VBox with secondary content
     */
    public VBox createSecondaryContent(){
        VBox secondaryPage = new VBox();
        secondaryPage.setSpacing(10);
        secondaryPage.setStyle("-fx-padding: 10;");

        Label secondaryLabel = new Label("Secondary Page");

        secondaryPage.getChildren().addAll(secondaryLabel);
        return secondaryPage;
    }
}

