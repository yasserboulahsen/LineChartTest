package com.example.linecharttest;

import javafx.geometry.Bounds;
import javafx.scene.Cursor;
import javafx.scene.Group;
import javafx.scene.chart.Axis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;
import javafx.scene.shape.Line;

public  class Crosshair<X,Y> extends LineChart<X,Y> {

    private Group plotArea;
    private final XYChart.Series<X, Y> series;
    private Label label;

    public Crosshair(Axis<X> axis, Axis<Y> axis1, Group plotArea, Series<X, Y> series, Label label) {
        super(axis, axis1);

        this.plotArea =plotArea;
        this.series = series;
        this.label =label;
    }

  public void getCrossHair(Line vLine,Line hLine){
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
      vLine.setOnMouseDragged(e->{
          Bounds b = plotArea.getBoundsInLocal();
          // If the mouse cursor is within the plot area bounds
          if (b.getMinX() < e.getX() && e.getX() < b.getMaxX() && b.getMinY() < e.getY() && e.getY() < b.getMaxY()) {


              vLine.setStartX(e.getX() - b.getMinX() - 5);
              vLine.setEndX(e.getX()- b.getMinX()-5);
          }


      });
      hLine.setOnMouseDragged(e->{
          Bounds b = plotArea.getBoundsInLocal();
          if (b.getMinX() < e.getX() && e.getX() < b.getMaxX() && b.getMinY() < e.getY() && e.getY() < b.getMaxY()) {
              hLine.setStartY(e.getY() - b.getMinY() - 5);
              hLine.setEndY(e.getY() - b.getMinY() - 5);

          }
          for (XYChart.Data<X, Y> data : this.series.getData()) {
              if (hLine.getBoundsInParent().intersects(data.getNode().getBoundsInParent())){
//                   System.out.println(data.getYValue().toString());
                  getLabel().setText(data.getYValue().toString());
              }
          }


      });


  }
    public Label getLabel() {
        return label;
    }
}
