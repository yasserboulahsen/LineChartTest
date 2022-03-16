package com.example.linecharttest;

import javafx.geometry.Bounds;
import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;

public class CrossRectangle extends Rectangle {
    private final Line vLine  = new Line();
  Group plotArea;
    //@TODO add line to rectangle
    public CrossRectangle(Group plotArea) {
        this.plotArea = plotArea;
        this.setHeight(25);
        this.setWidth(25);
        this.setStroke(Color.BLACK);
        this.setFill(Color.TRANSPARENT);

    }


    public void mouseDaraged() {
        this.setOnMouseDragged(mouseEvent -> {

            Bounds b = plotArea.getBoundsInLocal();


                this.setLayoutX(mouseEvent.getX() - b.getMinX());
                this.setLayoutY(mouseEvent.getY() - b.getMinY());

        });
    }









}
