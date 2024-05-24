module com.example.demo1 {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;
    requires java.xml.crypto;

    opens mainPoint to javafx.fxml;
    exports mainPoint;

    opens model to javafx.fxml;
    exports model;


}