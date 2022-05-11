package com.example.linecharttest;

import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;

public class RectangleCross extends Rectangle {


    public RectangleCross() {
        this.setHeight(20);
        this.setWidth(20);
        this.setStroke(Color.BLACK);
        this.setFill(Color.TRANSPARENT);
        this.setStyle("-fx-stroke-dash-array: 2 ; ");

    }


}
