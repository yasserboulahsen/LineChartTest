package com.example.linecharttest;

import ESP.EspSerialPort;
import ESP.xyGraph;
import com.fazecast.jSerialComm.SerialPort;
import javafx.animation.TranslateTransition;
import javafx.application.Platform;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.print.*;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.shape.Circle;
import javafx.scene.transform.Scale;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.util.*;

public class Controller {

    public GridPane gridTop;
    @FXML
    private Button stopBtn;

    @FXML
    private Circle circle3;
    @FXML
    private Circle circle2;
    @FXML
    private Circle circle1;
    @FXML
    private Circle circle;
    @FXML
    private Button showRec;

    private  Tooltip showRecTooltip = new Tooltip("Select Points");
    @FXML

    private Button printingBtn;

    private Tooltip printTooltip = new Tooltip("Print Document");
    @FXML
    private VBox lineCursor;
    @FXML
    private Button cross;
    private Tooltip crossTooltip = new Tooltip("Cursor");
    @FXML
    private Button autosize;

    private  Tooltip autosizeTooltip = new Tooltip("Resize graph");
    @FXML
    private Label batteryLabel;
    @FXML
    private ProgressBar battery;
    @FXML
    private Label yValue2;
    @FXML
    private Label xValue2;
    @FXML
    private Button curseur;
    @FXML
    private Label xValue;
    @FXML
    private Button connect;
    @FXML
    private Label yValue;
    @FXML
    private BorderPane borderPane;
    @FXML
    private VBox vbox;


    @FXML
    private Button start;

    private boolean sizeAuto = false;

    private final NumberAxis xaxis = new NumberAxis();
    private final NumberAxis yaxis = new NumberAxis(0, 12, 1);
    private final NumberAxis xaxis1 = new NumberAxis();
    private final NumberAxis yaxis1 = new NumberAxis(0, 100, 1);
    //    private LineChart<Number,Number> newchart = new LineChart<>(xaxis,yaxis);
    private final Label xvalueLabel = new Label();
    private final Label yvalueLabel = new Label();
    private final Label label2 = new Label();
    private final Label xvalue2 = new Label();

    // progressBar
    private final ProgressBar progressBar = new ProgressBar();

    //    private Rectangle rec =
    private final XYChart.Series<Number, Number> forceSeries = new XYChart.Series<>();
    private final XYChart.Series<Number, Number> speedSeries = new XYChart.Series<>();
    private final Chart<Number, Number> forceChart = new Chart<>(xaxis, yaxis, yvalueLabel, xvalueLabel, forceSeries, borderPane);
    private final Chart<Number, Number> speedChart = new Chart<>(xaxis1, yaxis1, label2, xvalue2, speedSeries, borderPane);
    private xyGraph graph;
    private final SerialPort[] esp32 = {null};
    public static SerialPort closedesp32;

    private SimpleDoubleProperty batteryLevel;

    private boolean showCursorSpeed =false;
    private boolean showCursorForce = false;

    private Stage stage;
    private Scene scene;
    private Parent parent;

    private List<Node> circles = new ArrayList<>();



    public void initialize() throws IOException, InterruptedException {


        circles = List.of(circle,circle1,circle2,circle3);

        circles.forEach(e->{
            e.setVisible(false);
        });

        start.setDisable(true);
        stopBtn.setDisable(true);
        borderPane.setCenter(vbox);
        VBox.setVgrow(forceChart, Priority.ALWAYS);
        VBox.setVgrow(speedChart, Priority.ALWAYS);
        vbox.getChildren().addAll(forceChart, speedChart);

        forceSeries.setName("Force");
        speedSeries.setName("Speed");
        forceChart.setAnimated(false);
        speedChart.setAnimated(false);
        speedChart.autoResize(true);
        forceChart.autoResize(true);
        forceChart.setCreateSymbols(true);
        speedChart.setCreateSymbols(true);
        speedChart.setId("speedchart");
        forceChart.setId("forcechart");
        battery.progressProperty().setValue(1);
        lineCursor.setVisible(false);
        speedChart.setOnMouseClicked(e->{
            speedChart.setStyle("-fx-background-color: rgba(128, 128, 128, 0.08);");
            forceChart.setStyle("");
            showCursorSpeed = true;
            showCursorForce = false;
        });
        forceChart.setOnMouseClicked(e->{
            forceChart.setStyle("-fx-background-color: rgba(128, 128, 128, 0.08);");
            speedChart.setStyle("");
            showCursorSpeed = false;
            showCursorForce = true;
        });

     Tooltip.install(showRec,showRecTooltip);
     Tooltip.install(printingBtn,printTooltip);
     Tooltip.install(cross,crossTooltip);
     Tooltip.install(autosize,autosizeTooltip);

     borderPane.widthProperty().addListener((obs,oldval,newVal)->{
//         System.out.println(oldval);
//         System.out.println(newVal);
     });

    }


    public void onStart(ActionEvent actionEvent) throws IOException, InterruptedException {


        start.setDisable(true);
        speedChart.autoResize(true);
        forceChart.autoResize(true);


        esp32[0].openPort();

        forceSeries.getData().removeAll(forceSeries.getData());
        speedSeries.getData().removeAll(speedSeries.getData());
        forceChart.getData().clear();
        speedChart.getData().clear();
        graph = new xyGraph(esp32, speedSeries, forceSeries,progressBar);
        graph.chart(1, 25);



        forceChart.getDataSeries();
        speedChart.getDataSeries();

        battery.progressProperty().bind(progressBar.progressProperty());
        battery.styleProperty().bind(progressBar.styleProperty());







    }

    public void showRectangle(ActionEvent actionEvent) {

        if(showCursorForce){
            forceChart.addnewRectangle(borderPane);
        }
        if(showCursorSpeed){
            speedChart.addnewRectangle(borderPane);
        }






    }

    public void onAutoSize(ActionEvent actionEvent) {
        sizeAuto = !sizeAuto;

        speedChart.autoResize(sizeAuto);
        forceChart.autoResize(sizeAuto);


    }

    public void onStop(ActionEvent actionEvent) {
        start.setDisable(false);
        speedChart.autoResize(false);
        forceChart.autoResize(false);
        speedChart.getLine().toFront();
        forceChart.getLine().toFront();





        try {

            graph.shutDownService();
        } catch (Exception e) {
            System.out.println("not cennected");
        }
    }

    public void onConnexion(ActionEvent actionEvent) throws IOException {
        connect.setDisable(true);

        Random random = new Random();
        circles.forEach((e)->{

            e.setVisible(true);
            TranslateTransition transition = new TranslateTransition();

            transition.setNode(e);
            transition.setDuration(Duration.millis(1000));
            transition.setCycleCount(TranslateTransition.INDEFINITE);
            transition.setByY(-(random.nextInt(10)));
//           transition.setAutoReverse(true);

            transition.play();

        });






        Alert alert = new Alert(Alert.AlertType.ERROR);
//        for(double i = 0.0 ;i <= 1.0; i += 0.1){
//            progress.setProgress(i);
//        }

        Runnable runnable = () -> {
            try {

                esp32[0] = EspSerialPort.getSerialPort();

                closedesp32 = esp32[0];

                start.setDisable(false);
                stopBtn.setDisable(false);

                connect.setStyle("-fx-background-color: #008000; -fx-background-radius: 25px" );
                connect.setDisable(true);
                circles.forEach((e)->{
                    e.setVisible(false);
                });

                //Platform.runLater(() -> progress.setProgress(1.0));
            } catch (Exception e) {
                Platform.runLater(() -> {
                    try {
                        esp32[0] = EspSerialPort.getSerialPort();
                        start.setDisable(false);
                    } catch (Exception e1) {
                        //e.printStackTrace();
                        circles.forEach((eC)->{
                            eC.setVisible(false);
                        });

                        alert.setTitle("Erreur");
                        alert.setHeaderText("Erreur de connexion");
                        String s = "La connexion a échoué verifier si le bleutooth est activer !!!";
                        alert.setContentText(s);

                        alert.show();

                        connect.setDisable(false);

                    }
                });
            }

        };


        new Thread(runnable).start();
    }

    public void onCursor(ActionEvent actionEvent) {
        forceChart.crosshair();
        speedChart.crosshair();
        xValue.textProperty().bind(forceChart.getxValue().textProperty());
        yValue.textProperty().bind(forceChart.getyVlaue().textProperty());
        xValue2.textProperty().bind(speedChart.getxValue().textProperty());
        yValue2.textProperty().bind(speedChart.getyVlaue().textProperty());
    }
    @FXML
    public void printButton() {


        Dialog<ButtonType> dialog = new Dialog<>();

//        System.out.println("print methode has been called");
        dialog.initOwner(borderPane.getScene().getWindow());

        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                gridTop.setVisible(false);
                Dialog<ButtonType> dialog = new Dialog<>();

                System.out.println("print methode has been called");
                dialog.initOwner(borderPane.getScene().getWindow());
                dialog.setTitle(" Print");
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("printDocument.fxml"));
                try {
                    //Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("todoItemDialog.fxml")));
                    dialog.getDialogPane().setContent(fxmlLoader.load());
                } catch (IOException e) {
                    System.out.println("Couldn't load the dialog");
                    e.printStackTrace();
                    return;
                }
                dialog.getDialogPane().getButtonTypes().add(ButtonType.OK);
                dialog.getDialogPane().getButtonTypes().add(ButtonType.CLOSE);
                Optional<ButtonType> result = dialog.showAndWait();
                if (result.isPresent() && result.get() == ButtonType.OK) {
                    printDocument document = fxmlLoader.getController();
                    System.out.println(document.nom());
                    System.out.println("OK");

//                    isSelected.setText("Nom : " + ducument.nom() + ",Groupe : " + ducument.group() +
//                            ",Masee :" + ducument.masse());


//                    ObservableSet<Printer> printers = Printer.getAllPrinters();
//                    for(Printer printer: printers){
//                        System.out.println(printer.getName());
//                    }
                    //@TODO fix the printing function and create a new scene !!!
                    printingSeating();


                }
                gridTop.setVisible(true);


            }


        });


    }
    private void printingSeating() {
        Printer printer = Printer.getDefaultPrinter();
        PageLayout pageLayout
                = printer.createPageLayout(Paper.NA_LETTER, PageOrientation.LANDSCAPE, Printer.MarginType.HARDWARE_MINIMUM);
        //PrinterAttributes attr = printer.getPrinterAttributes();
        PrinterJob job = PrinterJob.createPrinterJob();
        double scaleX
                = pageLayout.getPrintableWidth() / borderPane.getBoundsInParent().getWidth();
        double scaleY
                = pageLayout.getPrintableHeight() / borderPane.getBoundsInParent().getHeight();
        Scale scale = new Scale(scaleX, scaleY);
        borderPane.getTransforms().add(scale);


        if (job != null && job.showPrintDialog(borderPane.getScene().getWindow())) {
            boolean success = job.printPage(pageLayout, borderPane);
            if (success) {
                job.endJob();

            }
        }
        borderPane.getTransforms().remove(scale);
    }


    public void onCross(ActionEvent actionEvent) {

        if(showCursorForce){
            forceChart.showCross();
        }
        if(showCursorSpeed){
            speedChart.showCross();
        }
    }

    public void onCon(ActionEvent actionEvent) throws IOException {
        Parent root =  FXMLLoader.load(Objects.requireNonNull(getClass().getResource("connection.fxml")));
        stage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setTitle("Connection!");
        stage.setScene(scene);
        stage.show();
        Rectangle2D primScreenBounds = Screen.getPrimary().getVisualBounds();
        stage.setX((primScreenBounds.getWidth() - stage.getWidth()) / 2);
        stage.setY((primScreenBounds.getHeight() - stage.getHeight()) / 2);
    }



}