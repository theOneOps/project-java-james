package mainPoint;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import controllers.HomePageController;
import model.DataSerialize;
import views.HomePageView;

import java.io.IOException;

/**
 * Classe principale de l'application qui lance l'interface utilisateur principale
 * et gère les événements de fermeture.
 */
public class HelloApplication extends Application {
    private Button paramButton;

    /**
     * Méthode d'initialisation de la fenêtre principale de l'application.
     *
     * @param stage La fenêtre principale de l'application.
     * @throws IOException Si une erreur d'E/S se produit lors du chargement des données.
     */
    @Override
    public void start(Stage stage) throws IOException {
        stage.setMinWidth(1000d);
        stage.setMinHeight(700d);
        stage.setResizable(false);
        stage.setTitle("HOME PAGE VIEW");
        // Controller HomePage
        HomePageController homePageController = new HomePageController();
        // Event close stage

        DataSerialize ds = new DataSerialize();
        try{
            ds.loadData();
        }catch (Exception ex) {
            throw new RuntimeException(ex);
        }
        //homePageController.setEmployees();
        //get most recent register employees here to not refresh each time we switch views
        HomePageView homePageView = new HomePageView(stage);
        stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            public void handle(WindowEvent we) {
                try {
                    System.out.println("test0");
                    homePageView.closeConnection();
                    Platform.exit();
                    System.out.println("test5");
                    DataSerialize.getInstance().saveData();
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });
        stage.show();
    }

    /**
     * Méthode principale pour lancer l'application JavaFX.
     *
     * @param args Les arguments de la ligne de commande.
     */
    public static void main(String[] args) {
        launch();
    }

}

