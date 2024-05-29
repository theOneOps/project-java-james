module com.example.demo1 {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;
    requires java.xml.crypto;

    opens model to javafx.fxml;
    exports model;


}