module com.mycompany.puntosfxv2 {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.base;
    requires com.google.gson;

    opens com.mycompany.puntosfxv2 to javafx.fxml;
    exports com.mycompany.puntosfxv2;
}
