module com.example.pcbanalyserrepeat {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;


    opens com.example.pcbanalyserrepeat to javafx.fxml;
    exports com.example.pcbanalyserrepeat;
}