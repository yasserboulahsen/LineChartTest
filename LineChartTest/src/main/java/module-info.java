module com.example.linecharttest {
    requires javafx.controls;
    requires javafx.fxml;
    requires com.fazecast.jSerialComm;
    requires java.desktop;


    opens yasser.linechart to javafx.fxml;
    exports yasser.linechart;
}