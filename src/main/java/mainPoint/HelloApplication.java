package mainPoint;

import controllers.HomePageController;
import javafx.application.Application;
import javafx.application.Platform;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;

import javafx.scene.control.Button;
import javafx.stage.Stage;

import model.JobClasses.Employee;
import views.HomePageView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;

import views.HomePageView;

import java.io.IOException;

import static java.lang.Thread.sleep;

public class HelloApplication extends Application {
    private Button paramButton;
    @Override
    public void start(Stage stage) throws IOException {
        stage.setMinWidth(1000d);
        stage.setMinHeight(700d);
        stage.setResizable(false);
        stage.setTitle("HOME PAGE VIEW");
        stage.setOnCloseRequest(e -> Platform.exit());
        HomePageController homePageController = new HomePageController();
        //homePageController.setEmployees();
        //get most recent register employees here to not refresh each time we switch views
        HomePageView homePageView = new HomePageView(stage);
    }

    public static void main(String[] args) {
        launch();
    }

}

