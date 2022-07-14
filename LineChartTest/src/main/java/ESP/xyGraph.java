package ESP;

import com.fazecast.jSerialComm.SerialPort;
import javafx.application.Platform;
import javafx.scene.chart.XYChart;
import javafx.scene.control.ProgressBar;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.regex.Pattern;

public class xyGraph {

    private final SerialPort[] esp32;
    private final XYChart.Series<Number, Number> series;
    private final XYChart.Series<Number, Number> series1;
    private final ScheduledExecutorService scheduledExecutorService;
    private final ScheduledExecutorService batteryExecutorService;
    public static List<XYChart.Series<String,Number>> list = new ArrayList<>();
    private Double battery;
    private final ProgressBar progressBar;
    private double timeInSecond =0;
    private final Date previuosTime = new Date();
    private final Date curentTime = new Date();
    private  BufferedReader input;


    public xyGraph(SerialPort[] esp32, XYChart.Series<Number, Number> series, XYChart.Series<Number, Number> series1,ProgressBar progressBar) {
        this.esp32 = esp32;
        this.series1 = series1;
        this.scheduledExecutorService =  new ScheduledThreadPoolExecutor(1);//Executors.newScheduledThreadPool(1);
        this.batteryExecutorService =new ScheduledThreadPoolExecutor(1);
        this.series = series;
        this.progressBar =progressBar;
        this.battery = 1.0;
    }

    public ScheduledExecutorService getScheduledExecutorService() {
        return scheduledExecutorService;
    }




    public void chart(int number, int period) {

        this.scheduledExecutorService.scheduleAtFixedRate(() -> {

            Platform.runLater(() -> {

                // get current time
                Date nowTime = new Date();
//                curentTime.setTime(nowTime.getTime() - previuosTime.getTime());
                timeInSecond = ((double)(nowTime.getTime() - previuosTime.getTime())/1000);
//                Scanner data = new Scanner(this.esp32[0].getInputStream());
                 input =  new BufferedReader(new InputStreamReader(this.esp32[0].getInputStream()));

//                if (data.hasNext()) {
//                    String outputData = data.nextLine();
                    try {
                        chartsSeries(number, input.readLine());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    //Chart update to series graph

//                }

            });

        }, 0, period, TimeUnit.MILLISECONDS);


        this.batteryExecutorService.scheduleAtFixedRate(()->{
                    Platform.runLater(() -> {
                        input =  new BufferedReader(new InputStreamReader(this.esp32[0].getInputStream()));
                        try {
                            getBatteryLevel(input.readLine());
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                        System.out.println("battery"+ this.battery);
                        if (this.battery != null) {
                            this.progressBar.progressProperty().setValue((this.battery - 3) / 0.7);
                            if (this.battery < 3) {
                                this.progressBar.setStyle("-fx-accent:red");
                            }
                        }
                    });
        },0,1000,TimeUnit.MILLISECONDS);


    }


//@TODO verifier le graph pour qu'il affiche les minute apre 60 second
    private void chartsSeries(int number, String t) {

        try {
            String split = splitData(t, number);
            String split1 = splitData(t, 0);
            String batterySplit =  splitData(t,2);
            if (  split !=null  && split1 !=null && batterySplit !=null) {

                this.series.getData().add(new XYChart.Data<>(timeInSecond, Double.valueOf(split)));
                this.series1.getData().add(new XYChart.Data<>(timeInSecond, Double.valueOf(split1)));
//               System.out.println(this.simpleDateFormat.format(curentTime)+"--"+ Double.valueOf(split1));
//                list.add(series);
//                this.battery = Double.parseDouble(batterySplit);

            }

        } catch (Exception e) {
            System.out.println(e.getMessage());

        }
    }
    private void getBatteryLevel(String t ){
        String batterySplit =  splitData(t,2);
        if ( batterySplit !=null) {
            this.battery = Double.parseDouble(batterySplit);
        }
    }


//    public double tryParseDouble(String value, double defaultVal) {
//        try {
//            return Double.parseDouble(value);
//        } catch (NumberFormatException e) {
//            return defaultVal;
//        }
//    }

    public String splitData(String s, int dataNumber) {

        String[] split = s.split(";",0);
        if (split.length == 3 && !split[0].isBlank() && !split[1].isBlank() && !split[0].isEmpty() && !split[1].isEmpty()
        && Pattern.matches("^[\\+\\-]?[0-9]+[\\.\\,][0-9]+$", split[0])) {
            return split[dataNumber];
        }

        return null;
    }

    public void shutDownService(){
        this.scheduledExecutorService.shutdownNow();
    }

//    private void chartColor() {

//        for (XYChart.Data<Number, Number> entry : series1.getData()) {
//            entry.getNode().setStyle("-fx-background-color: blue, white;\n"
//                    + "    -fx-background-insets: 0, 2;\n"
//                    + "    -fx-background-radius: 5px;\n"
//                    + "    -fx-padding: 5px;");
//        }
//        series1.getNode().lookup(".chart-series-line").setStyle("-fx-stroke: blue;");
//    }

}
