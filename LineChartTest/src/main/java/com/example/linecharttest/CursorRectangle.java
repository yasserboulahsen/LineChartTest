package com.example.linecharttest;

import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;



public class CursorRectangle extends Group {

    private RectangleCross rectangleCross;
    private Line verticalLineTop;

    private Line verticalLineDown;
    private Line horizontalLineRight;
    private Line horizontalLineLeft;
    private Group group;

    private Label label;
    double initialSceneX, initialSceneY;
    double initialTranslateX, initialTranslateY;

    private Node node;
    private XYChart.Series<?, ?> series;
    Rectangle rec;

    public CursorRectangle(Node node, XYChart.Series<?, ?> series) {

        this.series = series;
        this.node = node;
        this.rectangleCross = new RectangleCross();
        this.verticalLineTop = new Line();
        this.verticalLineDown = new Line();
        this.horizontalLineRight = new Line();
        this.horizontalLineRight.setStyle("-fx-stroke-dash-array: 2;");
        this.horizontalLineLeft = new Line();
        this.horizontalLineLeft.setStyle("-fx-stroke-dash-array: 2;");
        this.verticalLineTop.setStyle("-fx-stroke-dash-array: 2;");
        this.verticalLineDown.setStyle("-fx-stroke-dash-array: 2;");
        this.label = new Label();
        this.setOnMouseDragged(rectangleOnMouseDraggedEventHandler);
        this.setOnMousePressed(rectangleOnMousePressedEventHandler);

        getRectangleWithLines();
        this.getChildren().addAll(this.verticalLineTop, this.verticalLineDown, this.rectangleCross, this.horizontalLineRight, this.horizontalLineLeft, this.label);
        this.label.setId("labelChart");

    }


    public Group getCross() {

        return this;
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

    public RectangleCross getRectangleCross() {
        return rectangleCross;
    }
    public CursorRectangle getCursorRectangle() {
        return this;
    }

    public XYChart.Series<?, ?> getSeries() {
        return this.series;
    }

    EventHandler<MouseEvent> rectangleOnMousePressedEventHandler =
            new EventHandler<>() {

                @Override
                public void handle(MouseEvent t) {
                    initialSceneX = t.getSceneX();
                    initialSceneY = t.getSceneY();
                    initialTranslateX = ((Group) (t.getSource())).getTranslateX();
                    initialTranslateY = ((Group) (t.getSource())).getTranslateY();

                    //label.setText("");
                    //label.getStyleClass().clear();
                    //getRectangleCross().setStrokeWidth(1.5);
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
                    double x1 = (node.getBoundsInLocal().getMaxX() - getCross().getBoundsInParent().getMaxX());
                    horizontalLineRight.setEndX(horizontalLineRight.getBoundsInParent().getMaxX() + x1);
                    //
                    double x2 = (node.getBoundsInLocal().getMinX() - getCross().getBoundsInParent().getMinX());
                    horizontalLineLeft.setEndX(horizontalLineLeft.getBoundsInParent().getMinX() + x2);
                    //
                    double y1 = (node.getBoundsInLocal().getMinY() - getCross().getBoundsInParent().getMinY());
                    verticalLineTop.setEndY(verticalLineTop.getBoundsInParent().getMinY() + y1);
                    //
                    double y2 = (node.getBoundsInLocal().getMaxY() - getCross().getBoundsInParent().getMaxY());
                    verticalLineDown.setEndY(verticalLineDown.getBoundsInParent().getMaxY() + y2);
                    //

                    double xMax =  getCursorRectangle().localToParent(node.getBoundsInLocal()).getCenterX()+10;
                    double xMin =  getCursorRectangle().localToParent(node.getBoundsInLocal()).getCenterX() -10;
                    double yMax =  getCursorRectangle().localToParent(node.getBoundsInLocal()).getCenterY() +10;
                    double yMin =  getCursorRectangle().localToParent(node.getBoundsInLocal()).getCenterY() -10;



                    for(XYChart.Data<?, ?> data : getSeries().getData()){
                            //data.getNode().toFront();

                            if ((xMin <  data.getNode().getBoundsInParent().getCenterX() && ( data.getNode().getBoundsInParent().getCenterX() < xMax) &&
                                    (yMin <  data.getNode().getBoundsInParent().getCenterY() &&  data.getNode().getBoundsInParent().getCenterY() < yMax) )) {

                                label.setText("x: "+data.getXValue() + "\ny: " + data.getYValue());
                                label.getStyleClass().add("labelChart");
                               getRectangleCross().setStrokeWidth(4);
//                               data.getNode().setStyle("-fx-background-color: black, white;\n"
//                    + "    -fx-background-insets: 0, 5;\n"
//                    + "    -fx-background-radius: 5px;\n"
//                    + "    -fx-padding: 5px;");



                               break;
                            } else {

                                label.setText("");
                                label.getStyleClass().clear();
                                getRectangleCross().setStrokeWidth(1.5);

                            }


                    };

                }
            };






}
