module com.example.linecharttest {
    requires javafx.controls;
    requires javafx.fxml;
    requires com.fazecast.jSerialComm;


    opens com.example.linecharttest to javafx.fxml;
    exports com.example.linecharttest;
}