module com.example.demo1 {
    requires javafx.controls;
    requires javafx.fxml;


    opens model to javafx.fxml;
    exports model;
}