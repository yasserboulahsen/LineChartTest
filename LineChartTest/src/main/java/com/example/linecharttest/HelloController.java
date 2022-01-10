package com.example.linecharttest;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class HelloController {

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
    private boolean sizeAuto =false;

    private NumberAxis xaxis = new NumberAxis();
    private NumberAxis yaxis =  new NumberAxis();
    private NumberAxis xaxis1 = new NumberAxis(0,100,5);
    private NumberAxis yaxis1 =  new NumberAxis(0,100,5);
//    private LineChart<Number,Number> newchart = new LineChart<>(xaxis,yaxis);
    private final Label label1 = new Label();
    private final Label label2 = new Label();
//    private Rectangle rec =
    private XYChart.Series<Number,Number> series1 = new XYChart.Series<>();
    private XYChart.Series<Number,Number> series2 = new XYChart.Series<>();
    private chartTest<Number,Number> chartTest = new chartTest<>(xaxis,yaxis,label1,series1);
    private chartTest<Number,Number> chartTest1 = new chartTest<>(xaxis1,yaxis1,label2,series2);




 public void initialize(){
//     borderPane.setLeft(newchart);
//     chart.setAnimated(false);
//     newchart.setAnimated(false);
//     rec.setFill(Color.rgb(0,0,255,0.72));
//    borderPane.getChildren().add(chartTest);

//     xaxis.setLabel("X");
     borderPane.setCenter(vbox);
     vbox.getChildren().addAll(chartTest,chartTest1);

//    chartTest.showRecTangle(false);
    series1.setName("Force");
    series2.setName("Speed");

 }

    public void onStart(ActionEvent actionEvent) {

//        chart.getData().clear();
//        newchart.getData().clear();
            chartTest.getData().clear();
            chartTest1.getData().clear();

//         chartTest = new chartTest<>(xaxis,yaxis,rec,label1);
//        chartTest.setTitle("chartTest");
//        borderPane.setLeft(chartTest);



         //serie1
        series1.getData().add( new XYChart.Data<>(1,300));
        series1.getData().add( new XYChart.Data<>(14,60));
        series1.getData().add( new XYChart.Data<>(44,40));
        series1.getData().add( new XYChart.Data<>(47,70));
        series1.getData().add( new XYChart.Data<>(50,15));
        series1.getData().add( new XYChart.Data<>(60,25));
        series1.getData().add( new XYChart.Data<>(75,56));
        series1.getData().add( new XYChart.Data<>(80,98));
        series1.getData().add( new XYChart.Data<>(95,47));
        //serie2
        series2.getData().add( new XYChart.Data<>(1,-1));
        series2.getData().add( new XYChart.Data<>(14,60));
        series2.getData().add( new XYChart.Data<>(44,40));
        series2.getData().add( new XYChart.Data<>(47,70));
        series2.getData().add( new XYChart.Data<>(50,15));
        series2.getData().add( new XYChart.Data<>(60,25));
        series2.getData().add( new XYChart.Data<>(75,56));
        series2.getData().add( new XYChart.Data<>(80,98));
        series2.getData().add( new XYChart.Data<>(95,47));

         chartTest.toFront();
         chartTest1.toFront();
         chartTest.getDataTest();
         chartTest1.getDataTest();
//         chartTest.getData().add(series1);
//         chart.getData().add(series);



//        label.setText("Starting");







    }

    public void showRectangle(ActionEvent actionEvent) {
     chartTest.addnewRectangle();
     chartTest1.addnewRectangle();
//     chartTest1.showRecTangle(true);
//     chartTest.showRecTangle(true);

        yValue.textProperty().bind(chartTest.getLabel().textProperty());


    }

    public void onAutoSize(ActionEvent actionEvent) {
      sizeAuto = !sizeAuto;

        chartTest1.autoResize(sizeAuto);
        chartTest.autoResize(sizeAuto);


    }
}