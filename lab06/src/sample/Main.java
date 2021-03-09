package sample;


import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.shape.ArcType;

public class Main extends Application {
    @FXML
    private Canvas canvas;

    //sample data for the Bar chart
    static double[] avgHousingPricesByYear = {
            247381.0,264171.4,287715.3,294736.1,
            308431.4,322635.9,340253.0,363153.7
    };
    static double[] avgCommercialPricesByYear = {
            1121585.3,1219479.5,1246354.2,1295364.8,
            1335932.6,1472362.0,1583521.9,1613246.3
    };

    //sample data for Pie chart
    private static String[] ageGroups = {
            "18-25", "26-35", "36-45", "46-55", "56-65", "65+"
    };
    private static int[] purchasesByAgeGroup = {
            648, 1021, 2453, 3173, 1868, 2247
    };
    private static Color[] pieColours = {
            Color.AQUA, Color.GOLD, Color.DARKORANGE,
            Color.DARKSALMON, Color.LAWNGREEN, Color.PLUM
    };

    @Override
    public void start(Stage primaryStage) throws Exception{
        Group root = new Group();
        Scene scene = new Scene(root, 800, 600);

        canvas = new Canvas();
        canvas.setWidth(900);
        canvas.setHeight(800);


        root.getChildren().add(canvas);
        primaryStage.setTitle("Lab-06");
        primaryStage.setScene(scene);
        primaryStage.show();

        drawFigures(root);
    }


    private void drawFigures(Group group) {

        GraphicsContext rec = canvas.getGraphicsContext2D();
        System.out.println("width: " + canvas.getWidth());
        System.out.println("height: " + canvas.getHeight());
        rec.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());


        //Pie chart configuration
        double currentPiece = 0.0;
        double totalPieces = 11410;

        for(int i =0; i < 6; i++){
            double piecePercent = purchasesByAgeGroup[i]/totalPieces;
            double pieceAngle = piecePercent*360;
            rec.setFill(pieColours[i]);
            rec.fillArc(420,200, 300,300,currentPiece, pieceAngle, ArcType.ROUND );
            currentPiece += pieceAngle;

        }

        //bar graph configuration
        for(int i = 0; i < avgHousingPricesByYear.length; i++){

            //red section
            rec.setFill(Color.RED);
            rec.setStroke(Color.RED);
            rec.fillRect(40+(40*i), 580-(avgHousingPricesByYear[i]/3000.0),15,avgHousingPricesByYear[i]/3000.0 );

            //blue section
            rec.setFill(Color.BLUE);
            rec.setStroke(Color.BLUE);
            rec.fillRect(55+(40*i), 580-avgCommercialPricesByYear[i]/3500.0, 15,avgCommercialPricesByYear[i]/3500.0);

        }

    }


    public static void main(String[] args) {
        launch(args);
    }
}
