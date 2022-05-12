package com.example.linecharttest;

import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Line;
import javafx.scene.shape.Shape;


public class CursorRectangle extends Pane {

    private RectangleCross rectangleCross;
    private Line verticalLineTop;

    private Line verticalLineDown;
    private Line horizontalLineRight;
    private Line horizontalLineLeft;
    private Group group;

    private Label label;

    double initialSceneX, initialSceneY;
    double initialTranslateX, initialTranslateY;

    double x = 100;
    private Node node;
    private XYChart.Series<?, ?> series;

    public CursorRectangle(Group group, Node node, XYChart.Series<?, ?> series) {
        this.series = series;
        this.node = node;
        this.group = group;
        this.rectangleCross = new RectangleCross();
        this.verticalLineTop = new Line();
        this.verticalLineDown = new Line();
        this.horizontalLineRight = new Line();
        this.horizontalLineRight.setStyle("-fx-stroke-dash-array: 2;");
        this.horizontalLineLeft = new Line();
        this.horizontalLineLeft.setStyle("-fx-stroke-dash-array: 2;");
        this.verticalLineTop.setStyle("-fx-stroke-dash-array: 2;");
        this.verticalLineDown.setStyle("-fx-stroke-dash-array: 2;");
        this.label = new Label("value");
        this.group = new Group();
        this.group.setOnMouseDragged(rectangleOnMouseDraggedEventHandler);
        this.group.setOnMousePressed(rectangleOnMousePressedEventHandler);


    }


    public Group getCross() {

        getRectangleWithLines();
        this.group.getChildren().addAll(this.verticalLineTop,this.verticalLineDown, this.rectangleCross, this.horizontalLineRight, this.horizontalLineLeft, this.label);
        return this.group;
    }

    private void getRectangleWithLines() {
        this.rectangleCross.setLayoutX(this.node.getBoundsInLocal().getWidth() / 2 - 10);
        this.rectangleCross.setLayoutY(this.node.getBoundsInLocal().getHeight() / 2 - 10);
        this.horizontalLineRight.setLayoutY(this.node.getBoundsInLocal().getHeight() / 2);
        this.horizontalLineRight.setStartX(this.rectangleCross.getBoundsInParent().getMaxX());
        this.horizontalLineRight.setEndX(this.node.getBoundsInLocal().getMaxX());
        this.horizontalLineLeft.setStartX(this.rectangleCross.getBoundsInParent().getMinX());
        this.horizontalLineLeft.setLayoutY(this.node.getBoundsInLocal().getHeight() / 2);
        this.horizontalLineLeft.setEndX(this.node.getBoundsInLocal().getMinY());
        this.verticalLineTop.setLayoutX(this.node.getBoundsInLocal().getWidth() / 2);
        this.verticalLineTop.setStartY(this.rectangleCross.getBoundsInParent().getMinY());
        this.verticalLineTop.setEndY(this.node.getBoundsInLocal().getMinY());
        this.verticalLineDown.setLayoutX(this.node.getBoundsInLocal().getWidth() / 2);
        this.verticalLineDown.setStartY(this.rectangleCross.getBoundsInParent().getMaxY());
        this.verticalLineDown.setEndY(this.node.getBoundsInLocal().getMaxY());
        this.label.setLayoutX(this.rectangleCross.getLayoutX() + 20);
        this.label.setLayoutY(this.rectangleCross.getLayoutY() + 20);


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
                    //
                    double x1 = (node.getBoundsInLocal().getMaxX() - group.getBoundsInParent().getMaxX());
                    horizontalLineRight.setEndX(horizontalLineRight.getBoundsInParent().getMaxX() + x1);
                    //
                    double x2 = (node.getBoundsInLocal().getMinX() - group.getBoundsInParent().getMinX());
                    horizontalLineLeft.setEndX(horizontalLineLeft.getBoundsInParent().getMinX() + x2);
                    //
                    double y1 = (node.getBoundsInLocal().getMinY() - group.getBoundsInParent().getMinY());
                    verticalLineTop.setEndY(verticalLineTop.getBoundsInParent().getMinY() + y1);
                    //
                    double y2 = (node.getBoundsInLocal().getMaxY() - group.getBoundsInParent().getMaxY());
                    verticalLineDown.setEndY(verticalLineDown.getBoundsInParent().getMaxY() + y2);
                    //


                }
            };


}
