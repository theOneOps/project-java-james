package mainPoint;


import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
//import pointeuse.controllers.PointerController;
import views.Pointer;
import controllers.PointerController;


import java.io.IOException;


/**
 * The PointerMain class serves as the entry point for the JavaFX application.
 * It initializes the application components and starts the JavaFX stage.
 */
public class PointerMain extends Application {


    /**
     * The main method launches the JavaFX application.
     *
     * @param args command-line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }


    /**
     * The start method is called after the JavaFX application is launched.
     * It initializes the pointer view and controller, sets up the scene, and displays the primary stage.
     *
     * @param primaryStage the primary stage for this application
     * @throws IOException            if an I/O error occurs during initialization
     * @throws ClassNotFoundException if the class for the serialized object cannot be found
     * @throws InterruptedException   if the initialization is interrupted
     */
    @Override
    public void start(Stage primaryStage) throws IOException, ClassNotFoundException, InterruptedException {
        PointerController pointerController = new PointerController();
        // Initialize the pointer view
        Pointer pointer = new Pointer(pointerController);


        // Create a scene with a specific width and height
        Scene scene = new Scene(pointer, 450, 200);
        // Stop connection when primaryStage is closing
        primaryStage.setOnCloseRequest(e -> {
            pointerController.stopScheduledConnection();
        });
        // Set up the primary stage
        primaryStage.setTitle("Time Tracker Emulator");
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.show();
    }
}
