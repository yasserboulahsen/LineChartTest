package com.example.linecharttest;

import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Bounds;
import javafx.scene.Cursor;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.chart.Axis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;

public class chartTest<X, Y> extends LineChart<X,Y> {

    private ObservableList<Rectangle> recs = FXCollections.observableArrayList();
    private Rectangle rec;

    private Group plotArea;

    private final Label yValue;
    private final Label xValue;
    private final XYChart.Series<X, Y> series;
    private Line line;
    private  double  x =0  ;
    private  double y =0;
    private final Line hLine = new Line();
    private final Line vLine = new Line();

    private ObservableList<Node> points = FXCollections.observableArrayList();




    public chartTest(Axis<X> axis, Axis<Y> axis1, Label yvalue, Label xvalue, XYChart.Series<X, Y> series) {
        super(axis, axis1);
        this.xValue = xvalue;
        this.yValue = yvalue;
        this.series = series;
        this.setAnimated(false);
       this.line = new Line();
       this.setCreateSymbols(true);


//        this.series.getNode().getStyleClass().add("lineColor");

    }

//    public void showRecTangle(boolean show) {
//        this.rec.setVisible(show);
//    }

    public void addnewRectangle() {
//        this.rec = new Rectangle(200,50,Color.rgb(0,0,255,0.72));

        crosshair();
        Platform.runLater(()->{
            rectangleAdded();
        });

    }

    private void rectangleAdded() {
        Rectangle rectangle = new Rectangle(250, 100, Color.valueOf("A9DCDAB8"));
        ResizeRegion.makeResizable(rectangle, null);
        rectangle.setOnMouseClicked(e -> {


            for (Data<X, Y> data : this.series.getData()) {


                if (rectangle.getBoundsInParent().contains(data.getNode().getBoundsInParent())) {
//                    System.out.println("X " + data.getXValue() + " Y = " + data.getYValue());
                    data.getNode().getStyleClass().add("recNodeColor");

                }else {
                    data.getNode().getStyleClass().remove("recNodeColor");
                }
            }

        });
        getPlotChildren().add(rectangle);
    }

    public void getDataTest() {

        this.getData().add(this.series);
//        this.rec.toFront();

    }

    @Override
    protected void layoutPlotChildren() {

    final NumberAxis xAxis = (NumberAxis) this.getXAxis();
    final NumberAxis yAxis =(NumberAxis) this.getYAxis();


        super.layoutPlotChildren();
        if (plotArea == null && !getPlotChildren().isEmpty()) {
            Group plotContent = (Group) getPlotChildren().get(0).getParent();
            plotArea = (Group) plotContent.getParent();
        }
        Platform.runLater(() -> {
            dataManage();
        });
        this.getXAxis().setOnMouseEntered(e->{

            x=e.getX();
             setCursor(Cursor.E_RESIZE);


//            System.out.println(this.getScene().getWidth());
        });
        this.getXAxis().setOnMouseExited(e->{
            setCursor(Cursor.DEFAULT);
        });
        this.getYAxis().setOnMouseEntered(e->{

           y=e.getY();
//            System.out.println(e.getY());
            setCursor(Cursor.V_RESIZE);
//            this.getYAxis().setAutoRanging(true);
        });
        this.getYAxis().setOnMouseExited(e->{
            setCursor(Cursor.DEFAULT);
        });

        this.getYAxis().setOnMouseDragged(e->{
//           System.out.println(e.getY());
//            this.getYAxis().setAutoRanging(true);
            double yUpper = yAxis.getUpperBound();
            if(e.getY()<y){
//                System.out.println("<- up " + y);
                yUpper +=1;
                yAxis.setUpperBound(yUpper);
            }else {
//                System.out.println("down ->" +y);
                yUpper -=1;
                yAxis.setUpperBound(yUpper);
            }
            y =e.getY();
        });
        this.getXAxis().setOnMouseDragged(e->{


//            System.out.println("e.getX() = "+e.getX());
            double xLower = xAxis.getLowerBound();
            double xUpper = xAxis.getUpperBound();
            if(e.getX()<x){
//                System.out.println("<- left " + x);
                xUpper +=1;
                xAxis.setUpperBound(xUpper);
            }else {
//                System.out.println("right ->" +x);
                xUpper -=1;
                xAxis.setUpperBound(xUpper);
            }
            x =e.getX();





//            double x = xAxis.getUpperBound();
//            x+=10;
//            xAxis.setUpperBound(x);
//            xAxis.setLowerBound(xAxis.getLowerBound()+10);
        });



        if ( !getPlotChildren().contains(line)) {

            getPlotChildren().addAll(line);
        }




    }


    private void dataManage() {

        for (Data<X, Y> data : this.series.getData()) {
            data.getNode().setOnMouseEntered(e->{
                setCursor(Cursor.HAND);
            });
            data.getNode().setOnMouseExited(e->{
                setCursor(Cursor.DEFAULT);
            });
            data.getNode().setOnMouseClicked(event -> {
                points.add(data.getNode());



                data.getNode().getStyleClass().add("lineNode");
                if (points.size() == 2) {

//                    System.out.println(points.get(0).getLayoutX() + data.getNode().getBoundsInLocal().getCenterX());
                    line.setStartX(points.get(0).getBoundsInParent().getCenterX());
                    line.setStartY(points.get(0).getBoundsInParent().getCenterY());
                    line.setEndX(points.get(1).getBoundsInParent().getCenterX());
                    line.setEndY(points.get(1).getBoundsInParent().getCenterY());
                    line.setScaleX(1.5);
                    line.setScaleY(1.5);
                }
                if (points.size() > 2) {
                    points.forEach(n->{
                         n.getStyleClass().remove("lineNode");
                            });


                    points.clear();
                }

                ChangeListener<Number> stageChageListener = ((observable, oldValue, newValue) -> {

//                   System.out.println(this.getWidth() +"/"+ this.getHeight());
//                    System.out.println(points.get(0).getBoundsInParent().getCenterX()+data.getNode().getBoundsInLocal().getCenterX());
                    line.setStartX(points.get(0).getBoundsInParent().getCenterX());
                    line.setStartY(points.get(0).getBoundsInParent().getCenterY());
                    line.setEndX(points.get(1).getBoundsInParent().getCenterX());
                    line.setEndY(points.get(1).getBoundsInParent().getCenterY());
                });

                this.widthProperty().addListener(stageChageListener);

                this.heightProperty().addListener(stageChageListener);
            });

        }


    }
    public void autoResize(boolean size){
        this.getYAxis().setAutoRanging(size);
        this.getXAxis().setAutoRanging(size);

    }
    public void crosshair(){

//        final NumberAxis xAxis = (NumberAxis) this.getXAxis();
//        final NumberAxis yAxis =(NumberAxis) this.getYAxis();
//         Crosshair<X,Y> cross =  new Crosshair<X,Y>((Axis<X>) xAxis, (Axis<Y>) yAxis,plotArea,series,label);
//
//         cross.getCrossHair(vLine,hLine);
        getPlotChildren().removeAll(vLine,hLine);

        vLine.setStrokeWidth(2);
        hLine.setStrokeWidth(2);

            hLine.setStartX(0);
            hLine.setStartY(getBoundsInLocal().getHeight() / 2);
            hLine.setEndX(getBoundsInLocal().getWidth());
            hLine.setEndY(getBoundsInLocal().getHeight() / 2);

            vLine.setStartX(getBoundsInLocal().getWidth() / 2);
            vLine.setStartY(0);
            vLine.setEndY(getBoundsInLocal().getHeight());
            vLine.setEndX(getBoundsInLocal().getWidth() / 2);


            vLine.setOnMouseEntered(e->{
                setCursor(Cursor.CROSSHAIR);
            });
            vLine.setOnMouseExited(e->{
                setCursor(Cursor.DEFAULT);
            });
  vLine.setOnMouseDragged(e->{
     Bounds b = plotArea.getBoundsInLocal();
      // If the mouse cursor is within the plot area bounds
      if (b.getMinX() < e.getX() && e.getX() < b.getMaxX() && b.getMinY() < e.getY() && e.getY() < b.getMaxY()) {


          vLine.setStartX(e.getX() - b.getMinX() - 5);
          vLine.setEndX(e.getX()- b.getMinX()-5);
      }
      for (Data<X, Y> data : this.series.getData()) {
          if (vLine.getBoundsInParent().intersects(data.getNode().getBoundsInParent())) {
//              System.out.println(data.getXValue());
                   getxValue().setText((data.getXValue().toString()));
          }
      }


  });
        hLine.setOnMouseDragged(e->{
            Bounds b = plotArea.getBoundsInLocal();
            if (b.getMinX() < e.getX() && e.getX() < b.getMaxX() && b.getMinY() < e.getY() && e.getY() < b.getMaxY()) {
                hLine.setStartY(e.getY() - b.getMinY() - 5);
                hLine.setEndY(e.getY() - b.getMinY() - 5);

            }
            for (Data<X, Y> data : this.series.getData()) {
                if (hLine.getBoundsInParent().intersects(data.getNode().getBoundsInParent())){
//                System.out.println(data.getYValue().toString());
                    getyVlaue().setText(data.getYValue().toString());

                }
            }


        });
        hLine.setOnMouseEntered(e->{
            hLine.setStyle("-fx-stroke:red;");
            setCursor(Cursor.CROSSHAIR);
        });
        hLine.setOnMouseExited(e->{
            setCursor(Cursor.DEFAULT);
        });



        getPlotChildren().addAll(hLine,vLine);
    }


    public Label getyVlaue() {
        return yValue;
    }
    public Label getxValue() {
        return xValue;
    }

    public void Xaxis(){
        final NumberAxis xAxis = (NumberAxis) this.getXAxis();
        final NumberAxis yAxis =(NumberAxis) this.getYAxis();
        this.setOnMouseClicked(e->{
            xAxis.setUpperBound(1000);
            yAxis.setUpperBound(1000);
        });


    }
}

