package mainPoint;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import model.Employee;
import views.HomePageView;

import java.io.IOException;
import java.util.Objects;

import static java.lang.Thread.sleep;

public class HelloApplication extends Application {
    private Button paramButton;
    @Override
    public void start(Stage stage) throws IOException {
        stage.setMinWidth(800d);
        stage.setMinHeight(600d);
        stage.setResizable(false);
        stage.setTitle("HOME PAGE VIEW");
        stage.setOnCloseRequest(e -> Platform.exit());
        HomePageView homePageView = new HomePageView(stage);
        homePageView.initializeView();
    }

    public static void main(String[] args) {
        launch();
    }


}

