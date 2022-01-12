package com.example.linecharttest;

import ESP.EspSerialPort;
import ESP.xyGraph;
import com.fazecast.jSerialComm.SerialPort;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.text.SimpleDateFormat;

public class Controller {
    @FXML
    private Label xValue;
    @FXML
    private Button connect;
    @FXML
    private Label yValue;
    @FXML
    private BorderPane borderPane;
    @FXML
    private VBox vbox;
    @FXML
    private Button autosize;

    @FXML
    private Button start;

    private boolean sizeAuto = false;

    private NumberAxis xaxis = new NumberAxis(0, 100, 1);
    private NumberAxis yaxis = new NumberAxis(0, 100, 1);
    private NumberAxis xaxis1 = new NumberAxis(0, 100, 1);
    private NumberAxis yaxis1 = new NumberAxis(0, 100, 1);
    //    private LineChart<Number,Number> newchart = new LineChart<>(xaxis,yaxis);
    private final Label xvalueLabel = new Label();
    private final Label yvalueLabel = new Label();
    private final Label label2 = new Label();
    private final Label xvalue2 = new Label();
    //    private Rectangle rec =
    private XYChart.Series<Number, Number> series1 = new XYChart.Series<>();
    private XYChart.Series<Number, Number> series2 = new XYChart.Series<>();
    private Chart<Number, Number> chart = new Chart<>(xaxis, yaxis, yvalueLabel, xvalueLabel, series1);
    private Chart<Number, Number> chart1 = new Chart<>(xaxis1, yaxis1, label2, xvalue2, series2);
    private xyGraph graph;
    private final SerialPort[] esp32 = {null};
    final SimpleDateFormat simpleDateFormat = new SimpleDateFormat("ss.S");


    public void initialize() throws IOException, InterruptedException {

//     borderPane.setLeft(newchart);
//     chart.setAnimated(false);
//     newchart.setAnimated(false);
//     rec.setFill(Color.rgb(0,0,255,0.72));
//    borderPane.getChildren().add(chartTest);

//     xaxis.setLabel("X");
        start.setDisable(true);
        borderPane.setCenter(vbox);
        vbox.getChildren().addAll(chart, chart1);

//    chartTest.showRecTangle(false);
        series1.setName("Force");
        series2.setName("Speed");
        chart.setAnimated(false);
        chart1.setAnimated(false);
        chart1.autoResize(true);
        chart.autoResize(true);
        chart.setCreateSymbols(true);
        chart1.setCreateSymbols(true);
        chart1.setId("chart1");
        chart1.setId("chart");


    }

    public void onStart(ActionEvent actionEvent) throws IOException, InterruptedException {
        chart1.autoResize(true);
        chart.autoResize(true);
        esp32[0].openPort();

        series1.getData().removeAll(series1.getData());
        series2.getData().removeAll(series2.getData());
        chart.getData().clear();
        chart1.getData().clear();
        graph = new xyGraph(esp32, simpleDateFormat, series2, series1);
        graph.chart(1, 100);


        chart.getDataTest();
        chart1.getDataTest();
//        series1.getNode().lookup(".chart-series-line").setStyle("-fx-stroke: blue;");
//        series1.getNode().lookup(".chart-line-symbol").setStyle("-fx-background-color: blue");


//         chartTest.getData().add(series1);
//         chart.getData().add(series);


//        label.setText("Starting");


    }

    public void showRectangle(ActionEvent actionEvent) {
        chart.addnewRectangle(borderPane);
        chart1.addnewRectangle(borderPane);
//     chartTest1.showRecTangle(true);
//     chartTest.showRecTangle(true);
        xValue.textProperty().bind(chart.getxValue().textProperty());
        yValue.textProperty().bind(chart.getyVlaue().textProperty());


    }

    public void onAutoSize(ActionEvent actionEvent) {
        sizeAuto = !sizeAuto;

        chart1.autoResize(sizeAuto);
        chart.autoResize(sizeAuto);


    }

    public void onStop(ActionEvent actionEvent) {
        chart1.autoResize(false);
        chart.autoResize(false);
        chart1.getLine().toFront();
        chart.getLine().toFront();
//        chart1.setOnMouseEntered(e -> {
//            chart1.setStyle("-fx-background-color:rgba(0, 0, 0, 0.05)");
//
//        });
//        chart1.setOnMouseExited(e -> {
//
//            chart1.setStyle(null);
//        });
//        chart.setOnMouseEntered(e -> {
//            chart.setStyle("-fx-background-color:rgba(0, 0, 0, 0.05)");
//        });
//        chart.setOnMouseExited(e -> {
//            chart.setStyle(null);
//        });


        try {
            graph.shutDownService();
        } catch (Exception e) {
            System.out.println("not cennected");
        }
    }

    public void onConnexion(ActionEvent actionEvent) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
//        for(double i = 0.0 ;i <= 1.0; i += 0.1){
//            progress.setProgress(i);
//        }

        Runnable runnable = () -> {
            try {

                esp32[0] = EspSerialPort.getSerialPort();
                start.setDisable(false);

                connect.setStyle("-fx-background-color: #008000");
                connect.setDisable(true);
                //Platform.runLater(() -> progress.setProgress(1.0));
            } catch (Exception e) {
                Platform.runLater(() -> {
                    try {
                        esp32[0] = EspSerialPort.getSerialPort();
                        start.setDisable(false);
                    } catch (Exception e1) {
                        //e.printStackTrace();


                        alert.setTitle("Erreur");
                        alert.setHeaderText("Erreur de connexion");
                        String s = "La connexion a échoué verifier si le bleutooth est activer !!!";
                        alert.setContentText(s);

                        alert.show();
                    }
                });
            }

        };


        new Thread(runnable).start();
    }
}