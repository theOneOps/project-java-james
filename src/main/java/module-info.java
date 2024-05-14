module com.example.demo1 {
    requires javafx.controls;
    requires javafx.fxml;


    opens mainPoint to javafx.fxml;
    exports mainPoint;

    opens model to javafx.fxml;
    exports model;
}