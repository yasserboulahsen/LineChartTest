package com.example.linecharttest;

import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;

public class RectangleCross extends Rectangle {
    private final Line vLine  = new Line();
   Line line;
   Group group;

    //@TODO add line to rectangle
    public RectangleCross() {
        this.setHeight(20);
        this.setWidth(20);
        this.setStroke(Color.BLACK);
        this.setFill(Color.TRANSPARENT);




    }


}
