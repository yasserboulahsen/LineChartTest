package com.example.linecharttest;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

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
        super.stop();
    }

    public static void main(String[] args) {
        launch();
    }
}