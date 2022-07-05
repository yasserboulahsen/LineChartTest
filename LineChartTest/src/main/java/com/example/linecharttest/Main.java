package com.example.linecharttest;

import ESP.EspData;
import ESP.EspSerialPort;
import com.fazecast.jSerialComm.SerialPort;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

import static com.example.linecharttest.Controller.closedesp32;


public class Main extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("hello-view.fxml"));

        Scene scene = new Scene(fxmlLoader.load(), 1400, 800);
        String css = Objects.requireNonNull(Main.class.getResource("style.css")).toExternalForm();
        scene.getStylesheets().add(css);
        stage.setTitle("Synthése!");
        stage.setScene(scene);
        Image image = new Image("file:phyIcon.png");
        stage.getIcons().add(image);
        stage.show();


    }
    @Override
    public void stop() throws Exception {

        Runnable runnable = () -> {
            try {
                SerialPort closedesp32 = Controller.closedesp32;
                String command = "sleep";
                closedesp32.writeBytes(command.getBytes(), command.length());
                closedesp32.closePort();
            }catch (Exception e){
//                try {
//                    SerialPort esp32 = EspSerialPort.getSerialPort();
//                    String command = "test";
//                    esp32.writeBytes(command.getBytes(), command.length());
//                    esp32.closePort();
//                }catch (Exception exception){
//                    System.out.println("no device!");
//                }
        }
    };
        runnable.run();


//        EspSerialPort.getSerialPort().openPort();
//        String command = "test";
//        EspSerialPort.getSerialPort().writeBytes(command.getBytes(),command.length());
//        EspSerialPort.getSerialPort().closePort();
        super.stop();

        System.exit(0);
    }

    public static void main(String[] args) {
        launch();
    }
}