package com.example.linecharttest;

import javafx.event.EventHandler;
import javafx.geometry.Bounds;
import javafx.scene.Group;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;

public class CrossRectangle extends Rectangle {
    private final Line vLine  = new Line();
    Group plotArea;
    AnchorPane anchorPane;
    double orgSceneX, orgSceneY;
    double orgTranslateX, orgTranslateY;
    //@TODO add line to rectangle
    public CrossRectangle(Group plotArea) {
        this.plotArea = plotArea;
        this.setHeight(25);
        this.setWidth(25);
        this.setStroke(Color.BLACK);
        this.setFill(Color.TRANSPARENT);
        this.setOnMouseDragged(rectangleOnMouseDraggedEventHandler);
        this.setOnMousePressed(rectangleOnMousePressedEventHandler);

    }
    EventHandler<MouseEvent> rectangleOnMousePressedEventHandler =
            new EventHandler<>() {

                @Override
                public void handle(MouseEvent t) {
                    orgSceneX = t.getSceneX();
                    orgSceneY = t.getSceneY();
                    orgTranslateX = ((Rectangle) (t.getSource())).getTranslateX();
                    orgTranslateY = ((Rectangle) (t.getSource())).getTranslateY();
                }
            };
    EventHandler<MouseEvent> rectangleOnMouseDraggedEventHandler =
            new EventHandler<>() {

                @Override
                public void handle(MouseEvent t) {
                    double offsetX = t.getSceneX() - orgSceneX;
                    double offsetY = t.getSceneY() - orgSceneY;
                    double newTranslateX = orgTranslateX + offsetX;
                    double newTranslateY = orgTranslateY + offsetY;

                    ((Rectangle) (t.getSource())).setTranslateX(newTranslateX);
                    ((Rectangle) (t.getSource())).setTranslateY(newTranslateY);
                }
            };












}
