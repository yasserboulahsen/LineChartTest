package yasser.linechart;

import javafx.scene.Group;
import javafx.scene.chart.XYChart;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;

public class RectangleCross extends Rectangle {
    //private  XYChart.Series<?, ?> series;

    public RectangleCross() {
        this.setHeight(20);
        this.setWidth(20);
        this.setStroke(Color.BLACK);
        this.setFill(Color.TRANSPARENT);
        this.setStrokeWidth(1.5);
        this.setStyle("-fx-stroke-dash-array: 2 ; ");
//            this.getStrokeDashArray().addAll(2.0,7.0,2.0,7.0);
    }
    public void intersect(XYChart.Series<?, ?> series){
        this.setOnMouseDragged(mouseEvent->{  series.getData().forEach(e->{
            if(this.getBoundsInParent().intersects(e.getNode().getBoundsInParent())){
                System.out.println(e.getXValue());
            }
        });});

    }


}
