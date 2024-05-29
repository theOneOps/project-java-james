package views;

import controllers.EntrepriseController;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import model.JobClasses.Enterprise;

import java.util.ArrayList;
import java.util.Objects;

import static java.lang.Thread.sleep;


public class HomePageView {
    protected Stage stage;                          //stage of the app.
    protected Button paramButton;                   //custom parameter button
    protected Button homeButton;                    //custom home button
    protected TableView<Objects> tableView;         //table view changing content depending on the view instantiation
    protected ComboBox<String> comboBox;            //all entreprises
    protected GridPane mainPane;                    //use to separate app in 2, first part for sideBar(1/5), second for mainContent(4/5)
    protected GridPane sideBar;                     //custom sideBar
    protected VBox mainContent;                     //custom mainContent

    private final EntrepriseController entrepriseController;

    public HomePageView(Stage stage) {
        this.stage = stage;
        this.paramButton = new Button();
        this.homeButton = new Button();
        this.tableView = new TableView<>();
        this.comboBox = new ComboBox<>();
        this.mainPane = new GridPane();
        this.sideBar = new GridPane();
        this.mainContent = new VBox();
        this.entrepriseController = new EntrepriseController();
        initializeView();
    }

    public void initializeView() {
        //use stack pane to have multiple scene in the same stage.
        StackPane stackPane = new StackPane();
        stackPane.getChildren().add(mainPane);
        //create scene and add stackPane to it
        Scene scene = new Scene(stackPane, 800d, 600d);


        stage.setScene(scene);
        stage.setTitle("HomePage view");
        sideBar = createSideBar();
        mainContent = createMainContent();

        createParameterButton();
        createHomeButton();

        mainPane.setHgap(10);
        mainPane.setVgap(10);
        mainPane.setStyle("-fx-padding: 10;");

        //constraint column gridePane
        GridPane.clearConstraints(mainPane);
        ColumnConstraints col1 = new ColumnConstraints();
        ColumnConstraints col2 = new ColumnConstraints();
        col1.setPercentWidth(20); // 1/5 of stage
        col2.setPercentWidth(80); // 4/5 of stage
        col1.setHgrow(Priority.ALWAYS);
        col2.setHgrow(Priority.ALWAYS);

        mainPane.backgroundProperty().setValue(Background.fill(Color.GREEN));

        mainPane.getColumnConstraints().addAll(col1, col2);
        mainPane.setHgap(10);
        mainPane.setVgap(10);

        mainPane.add(sideBar, 0, 0);
        mainPane.add(mainContent, 1, 0);

        mainPane.backgroundProperty().setValue(Background.fill(Color.RED));

        GridPane.setHgrow(sideBar, Priority.ALWAYS);
        GridPane.setVgrow(sideBar, Priority.ALWAYS);
        GridPane.setHgrow(mainContent, Priority.ALWAYS);
        GridPane.setVgrow(mainContent, Priority.ALWAYS);
        VBox.setVgrow(tableView, Priority.ALWAYS);
        switchToView(mainContent);

        stage.show();

        //events
        //on click paramButton to change pane to parameter pane
        paramButton.setOnAction(e -> {
            ParameterView parameterView = new ParameterView(stage);
            VBox parameterContent = parameterView.mainContent;
            switchToView(parameterContent);
        });



        homeButton.setOnAction(e -> {
            System.out.println("homeButton clicked");
        });
    }

    /**
     * Create VBox for the main page with table view
     *
     * @return VBox with main content
     */
    public VBox createMainContent() {

        //create main VBox
        VBox mainBox = new VBox();
        mainBox.minWidth(600);
        VBox.setVgrow(tableView, Priority.ALWAYS);
        Label label = new Label("Main");
        mainBox.getChildren().addAll(label, tableView);
        VBox.setVgrow(mainBox, Priority.ALWAYS);
        //margin
        mainBox.setPadding(new Insets(10));
        mainBox.setSpacing(10);
        mainBox.backgroundProperty().setValue(Background.fill(Color.BLUE));

        return mainBox;
    }

    /**
     * Create parameter button
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

    }

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

        // Add action
        this.homeButton.setOnAction(e -> {
            // Define what should happen when homeButton is clicked
            System.out.println("Home button clicked!");
        });
    }

    /**
     * Create sideBar
     *
     * @return
     */
    public GridPane createSideBar() {
        //def SideBar
        GridPane sideBar = new GridPane();
        //home button
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


    public void switchToView(VBox newContent) {
        mainPane.getChildren().remove(mainContent);
        mainPane.add(newContent, 1, 0);

        GridPane.setHgrow(mainContent, Priority.ALWAYS);
        GridPane.setVgrow(mainContent, Priority.ALWAYS);
    }


    public void fillComboBox() {
        ArrayList<Enterprise> enterprises = getAllEnterprises();
        for(Enterprise ent : enterprises){
            comboBox.getItems().add(ent.getEntname());
        }
        //set value to first emp name
        if(!enterprises.isEmpty()) comboBox.setValue(enterprises.getFirst().getEntname());

        //Event on click
        comboBox.setOnAction(e -> {
            String enterpriseName = (String)comboBox.getValue();
            //check all enterprises name in array
            for(Enterprise ent : enterprises){
                if(ent.getEntname().equals(enterpriseName)){
                    EntrepriseView entrepriseView = new EntrepriseView(stage, ent);
                    VBox entrepriseContent = entrepriseView.mainContent;
                    switchToView(entrepriseContent);
                    System.out.println("selected enterprise : " + ent);
                    break;
                }
            }

        });


        // Créer une VBox pour contenir le ComboBox
        VBox vBox = new VBox(comboBox);
        vBox.setSpacing(10);
    }

    public ArrayList<Enterprise> getAllEnterprises(){
        return entrepriseController.getAllEntreprises();
    }
}
