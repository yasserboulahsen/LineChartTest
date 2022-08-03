package yasser.linechart.features;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.chart.Axis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import yasser.linechart.ChartLine;

public class ZoomChart<X,Y> {

    private ObservableList<X> listSeries = FXCollections.observableArrayList();
    private double xPlot , yPlot = 0;
    private double xPlotTranslate,yPlotTranslate;
    private final Rectangle rectangle1 = new Rectangle();
    private final ChartLine<X,Y> chartLine;

    private final XYChart.Series<X, Y> series ;
    public ZoomChart( ChartLine<X, Y> chartLine, XYChart.Series<X, Y> series) {

        this.chartLine = chartLine;
        this.series = series;
    }

    public void Zoom(Axis<X> axis, Axis<Y> axis1){
        this.chartLine.getChartChild().get(0).setOnMousePressed(e->{
            xPlot = e.getSceneX();
            yPlot = e.getSceneY();
            xPlotTranslate = ((Node) (e.getSource())).getTranslateX();
            yPlotTranslate = ((Node) (e.getSource())).getTranslateY();


        });
        this.chartLine.getChartChild().get(0).setOnMouseDragged(e->{

            double offsetX = e.getSceneX() - xPlot;
            double offsetY = e.getSceneY() -  yPlot;
            double newTranslateX = xPlotTranslate + offsetX;
            double newTranslateY = yPlotTranslate + offsetY;

            rectangle1.setX(xPlot-20);
            rectangle1.setY(-1);
            rectangle1.setWidth(newTranslateX);
            if(newTranslateY > this.chartLine.getHeight()-45){
                newTranslateY = this.chartLine.getHeight()-45;
            }
            rectangle1.setHeight(newTranslateY);
            rectangle1.setFill(Paint.valueOf("transparent"));
            rectangle1.setStroke(Color.BLACK);
            rectangle1.setStyle("-fx-stroke-dash-array:3.0,7.0,3.0,7.0 ");
            this.chartLine.autoResize(false);
            for (XYChart.Data<X, Y> data : this.series.getData()) {
                if(rectangle1.getBoundsInParent().contains(data.getNode().getBoundsInParent())){
                    // System.out.println("intersect ");
                    listSeries.add(data.getXValue());
                }
                else {
                    listSeries.remove(data);
                    System.out.println(listSeries.isEmpty());
                }
            };

        });
        this.chartLine.getChartChild().get(0).setOnMouseReleased(e->{
            final NumberAxis xAxis = (NumberAxis) axis;
            final NumberAxis yAxis = (NumberAxis) axis1;

            if(!listSeries.isEmpty()) {
                listSeries.forEach(System.out::println);
                xAxis.setLowerBound((Double) listSeries.get(0));
                xAxis.setUpperBound((Double) listSeries.get(listSeries.size() - 1));
            }


        });
        this.chartLine.getChartChild().add(rectangle1);


        rectangle1.toBack();
    }


}
