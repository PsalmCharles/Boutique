module com.example.supermarket {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires mysql.connector.java;


    opens com.example.supermarket.Controller to javafx.fxml;
    exports com.example.supermarket;
}