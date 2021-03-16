package sample;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Side;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.PieChart;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.HashMap;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{

        HashMap<String,Integer> dataCount;
        dataCount = new HashMap<>();

        String dataFile = "weatherwarnings-2015.csv";
        FileReader reader = new FileReader(dataFile);
        BufferedReader in = new BufferedReader(reader);

        String row;

        while((row = in.readLine()) != null) {
            if (row.trim().length() != 0) {
                String[] dataFields = row.split(",");
                if(!dataCount.containsKey(dataFields[5])){
                    dataCount.put(dataFields[5],1);
                }else{
                    int temp = dataCount.get(dataFields[5]);
                    dataCount.put(dataFields[5], temp+1);
                }
            }
        }
        System.out.println(dataCount);
        int length = dataCount.size();
        String[] label = new String [length];
        int [] count = new int [length];
        int counter = 0;
        for ( HashMap.Entry<String, Integer> entry : dataCount.entrySet()) {
            label[counter] = entry.getKey();
            count[counter] = entry.getValue();
            counter += 1;
        }
        ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList(
                new PieChart.Data(label[0], count[0]),
                new PieChart.Data(label[1], count[1]),
                new PieChart.Data(label[2], count[2]),
                new PieChart.Data(label[3], count[3]));


        final PieChart chart = new PieChart(pieChartData);
        chart.setTitle("Weather Warnings");
        primaryStage.setTitle("Lab 07");
        chart.setLabelLineLength(10);
        chart.setLegendSide(Side.LEFT);  //setting the labels on the left
        primaryStage.setScene(new Scene(chart, 900, 475));
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
