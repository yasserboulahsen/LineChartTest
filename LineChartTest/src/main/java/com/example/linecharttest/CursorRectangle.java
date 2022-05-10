package com.example.linecharttest;

import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Line;

import javax.swing.event.ChangeListener;


public class CursorRectangle {
    double x = 200;
    private RectangleCross rectangleCross;
    private Line line;
    private Group group;

    double initialSceneX, initialSceneY;
    double initialTranslateX, initialTranslateY;

    private Node node;

    public CursorRectangle(Group group, Node node) {
        this.node = node;
        this.group = group;
        this.rectangleCross = new RectangleCross();
        this.line = new Line();
        this.group = new Group();
        this.group.setOnMouseDragged(rectangleOnMouseDraggedEventHandler);
        this.group.setOnMousePressed(rectangleOnMousePressedEventHandler);

    }

    public Group getCross() {
        this.line.setStartX(rectangleCross.getBoundsInLocal().getCenterX());
        this.line.setStartY(this.node.getBoundsInLocal().getHeight() / 2);
        this.line.setEndX(this.node.getBoundsInLocal().getWidth());
        this.line.setEndY(this.node.getBoundsInLocal().getHeight() / 2);
        this.rectangleCross.setLayoutX(this.node.getBoundsInLocal().getWidth() / 2 - 10);
        this.rectangleCross.setLayoutY(this.node.getBoundsInLocal().getHeight() / 2 - 10);


        this.group.getChildren().addAll(this.line, this.rectangleCross);

        return this.group;
    }


    EventHandler<MouseEvent> rectangleOnMousePressedEventHandler =
            new EventHandler<>() {

                @Override
                public void handle(MouseEvent t) {
                    initialSceneX = t.getSceneX();
                    initialSceneY = t.getSceneY();
                    initialTranslateX = ((Group) (t.getSource())).getTranslateX();
                    initialTranslateY = ((Group) (t.getSource())).getTranslateY();
                }
            };
    EventHandler<MouseEvent> rectangleOnMouseDraggedEventHandler =
            new EventHandler<>() {

                @Override
                public void handle(MouseEvent t) {
                    double offsetX = t.getSceneX() - initialSceneX;
                    double offsetY = t.getSceneY() - initialSceneY;
                    double newTranslateX = initialTranslateX + offsetX;
                    double newTranslateY = initialTranslateY + offsetY;

                    ((Group) (t.getSource())).setTranslateX(newTranslateX);
                    ((Group) (t.getSource())).setTranslateY(newTranslateY);
                }
            };


}
