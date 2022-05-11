package com.example.linecharttest;

import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Line;




public class CursorRectangle extends Pane {

    private RectangleCross rectangleCross;
    private Line verticalLine;
    private Line horizontalLine;
    private Pane pane;

    private Label label;

    double initialSceneX, initialSceneY;
    double initialTranslateX, initialTranslateY;


    private Node node;

    public CursorRectangle(Pane pane, Node node) {
        this.node = node;
        this.pane = pane;
        this.rectangleCross = new RectangleCross();
        this.verticalLine = new Line();
        this.horizontalLine = new Line();
       this.horizontalLine.setStyle("-fx-stroke-dash-array: 2;");
       this.verticalLine.setStyle("-fx-stroke-dash-array: 2;");
        this.label =new Label("value");
        this.pane = new Pane();
        this.pane.setOnMouseDragged(rectangleOnMouseDraggedEventHandler);
        this.pane.setOnMousePressed(rectangleOnMousePressedEventHandler);

    }

    public Pane getCross() {

        getRectangleWithLines();
        this.pane.getChildren().addAll(this.verticalLine, this.rectangleCross,this.horizontalLine,this.label);
        return this.pane;
    }

    private void getRectangleWithLines() {
        this.rectangleCross.setLayoutX(this.node.getBoundsInLocal().getWidth() / 2 - 10);
        this.rectangleCross.setLayoutY(this.node.getBoundsInLocal().getHeight() / 2 - 10);
        this.verticalLine.setStartX(this.node.getBoundsInLocal().getMinX());
        this.verticalLine.setLayoutY(this.node.getBoundsInLocal().getHeight() / 2);
        this.verticalLine.setEndX(this.node.getBoundsInLocal().getMaxX());
        this.horizontalLine.setLayoutX(this.node.getBoundsInLocal().getWidth()/2);
        this.horizontalLine.setStartY(this.node.getBoundsInLocal().getMinY());
        this.horizontalLine.setStartY(this.node.getBoundsInLocal().getMaxY());
        this.label.setLayoutX(this.rectangleCross.getLayoutX()+20);
        this.label.setLayoutY(this.rectangleCross.getLayoutY()+20);

    }


    EventHandler<MouseEvent> rectangleOnMousePressedEventHandler =
            new EventHandler<>() {

                @Override
                public void handle(MouseEvent t) {
                    initialSceneX = t.getSceneX();
                    initialSceneY = t.getSceneY();
                    initialTranslateX = ((Pane) (t.getSource())).getTranslateX();
                    initialTranslateY = ((Pane) (t.getSource())).getTranslateY();
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
                    ((Pane) (t.getSource())).setTranslateX(newTranslateX);
                    ((Pane) (t.getSource())).setTranslateY(newTranslateY);
                }
            };


}
