package ESP;

import com.fazecast.jSerialComm.SerialPort;
import javafx.application.Platform;
import javafx.scene.chart.XYChart;

import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.regex.Pattern;

public class xyGraph {

    private final SerialPort[] esp32;

    private final XYChart.Series<Number, Number> series;
    private final XYChart.Series<Number, Number> series1;
    private final ScheduledExecutorService scheduledExecutorService;
    public static List<XYChart.Series<String,Number>> list = new ArrayList<>();

    private double timeInSecond =0;
    private final Date previuosTime = new Date();
    private final Date curentTime = new Date();


    public xyGraph(SerialPort[] esp32, XYChart.Series<Number, Number> series, XYChart.Series<Number, Number> series1) {
        this.esp32 = esp32;

        this.series1 = series1;
        this.scheduledExecutorService =  new ScheduledThreadPoolExecutor(1);//Executors.newScheduledThreadPool(1);
        this.series = series;



    }

    public ScheduledExecutorService getScheduledExecutorService() {
        return scheduledExecutorService;
    }


    public void chart(int number, int period) {

        this.scheduledExecutorService.scheduleAtFixedRate(() -> {

            Platform.runLater(() -> {


//               chartColor();//color of chart changes to blue

                // get current time
                Date nowTime = new Date();
//                curentTime.setTime(nowTime.getTime() - previuosTime.getTime());
                timeInSecond = ((double)(nowTime.getTime() - previuosTime.getTime())/1000);
                Scanner data = new Scanner(this.esp32[0].getInputStream());

                if (data.hasNext()) {
                    //String[] split = outputData.split(",");
                    String outputData = data.nextLine();
//                    String force = splitData(outputData,0);
//                    if(force !=null) {
//                        boolean isDecimal = Pattern.matches("^[\\+\\-]{0,1}[0-9]+[\\.\\,][0-9]+$", force);
//                        if(isDecimal) {
//                            System.out.println(outputData);
//                            chartsSeries(number, outputData);
//                        }
//                    }



                    chartsSeries(number, outputData);

                    //Chart update to series graph


                }


            });

        }, 0, period, TimeUnit.MILLISECONDS);


    }


//@TODO verifier le graph pour qu'il affiche les minute apre 60 second
    private void chartsSeries(int number, String t) {

        try {
            String split = splitData(t, number);
            String split1 = splitData(t, 0);
            if (  split !=null  && split1 !=null) {

                this.series.getData().add(new XYChart.Data<>(timeInSecond, Double.valueOf(split)));
                this.series1.getData().add(new XYChart.Data<>(timeInSecond, Double.valueOf(split1)));
//               System.out.println(this.simpleDateFormat.format(curentTime)+"--"+ Double.valueOf(split1));
//                list.add(series);
            }

        } catch (Exception e) {
            System.out.println(e.getMessage());

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
        if (split.length == 2 && !split[0].isBlank() && !split[1].isBlank() && !split[0].isEmpty() && !split[1].isEmpty()
        && Pattern.matches("^[\\+\\-]?[0-9]+[\\.\\,][0-9]+$", split[0])) {
            return split[dataNumber];
        }

        return null;
    }



    public void shutDownService(){
        this.scheduledExecutorService.shutdownNow();
    }

    private void chartColor() {
        for (XYChart.Data<Number, Number> entry : series1.getData()) {
            entry.getNode().setStyle("-fx-background-color: blue, white;\n"
                    + "    -fx-background-insets: 0, 2;\n"
                    + "    -fx-background-radius: 5px;\n"
                    + "    -fx-padding: 5px;");
        }
        series1.getNode().lookup(".chart-series-line").setStyle("-fx-stroke: blue;");
    }

}
