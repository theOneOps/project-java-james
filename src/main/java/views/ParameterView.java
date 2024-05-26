package views;

import javafx.scene.layout.Background;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class ParameterView extends HomePageView{

    public ParameterView(Stage stage) {
        super(stage);
        stage.setTitle("Parameter View");
        initializeMainContent();
    }

    public void initializeMainContent() {
        VBox vbox = new VBox();
        vbox.backgroundProperty().setValue(Background.fill(Color.YELLOW));
        switchToView(vbox);

        paramButton.setOnAction(e ->{
            HomePageView homePageView = new HomePageView(stage);
            VBox vBox = homePageView.mainContent;
            homePageView.switchToView(vBox);
            System.out.println("homeButton dans parameter view : paramButton");
        });
        homeButton.setOnAction(e -> {
            HomePageView homePageView = new HomePageView(stage);
            VBox vBox = homePageView.mainContent;
            homePageView.switchToView(vBox);
            System.out.println("homeButton dans parameter view: homeButton");
        });
    }
}
