package sample;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import java.io.*;
import java.net.*;

import java.net.URL;
import java.util.LinkedList;

public class Main extends Application {
    private Canvas canvas;
    @Override
    public void start(Stage primaryStage) throws Exception{
        LinkedList<Double> google = downloadStockPrices("https://query1.finance.yahoo.com/v7/finance/download" +
                                                        "/GOOG?period1=1262322000&period2=1451538000&interval=1mo&events=" +
                                                "history&includeAdjustedClose=true");
        LinkedList<Double>apple = downloadStockPrices("https://query1.finance.yahoo.com/v7/finance/download/" +
                "AAPL?period1=1262322000&period2=1451538000&interval=1mo&events=history&includeAdjustedClose=true");
        Group root = new Group();
        Canvas canvas = new Canvas(900, 700);
        GraphicsContext gc = canvas.getGraphicsContext2D();
        drawLinePlot(gc);
        root.getChildren().add(canvas);
        plotLine(gc, google, Color.RED);
        plotLine(gc, apple, Color.BLUE);
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }

    private void drawLinePlot(GraphicsContext gc) {
        gc.setStroke(Color.BLACK);
        gc.setLineWidth(2);
        gc.strokeLine(50, 650, 760, 650);
        gc.strokeLine(50, 50, 50, 650);

    }
    private void plotLine(GraphicsContext gc1, LinkedList<Double> company, Color colour){
        gc1.setStroke(colour);
        gc1.setLineWidth(2);

        int var = 0;
        for (int i = 1; i < company.size()-1; i++){
            int rand = i + 1;
            double point1 = 650 - Double.parseDouble(String.valueOf(company.get(i)));
            double point2 = 650 - Double.parseDouble(String.valueOf(company.get(rand)));
            gc1.strokeLine(var+52, point1, var+62, point2);
            var += 10;
        }
    }

    public LinkedList<Double> downloadStockPrices(String address) throws Exception{
        LinkedList<Double> close = new LinkedList();
        try {
            URL url = new URL(address);
            URLConnection conn = url.openConnection();
            conn.setDoOutput(false);
            conn.setDoInput(true);
            BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream()));
            String line= in.readLine();

            while((line = in.readLine()) != null) {
                String[] dataFields = line.split(",");
                close.add(Double.parseDouble(dataFields[4]));
            }
            // pull out the data that we want
        } catch (IOException e) {
            e.printStackTrace();
        }
        return close;
    }

    public static void main(String[] args) {
        launch(args);
    }
}
