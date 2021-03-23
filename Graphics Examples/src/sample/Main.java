package sample;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.shape.ArcType;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.scene.canvas.Canvas;
import javafx.util.Duration;


public class Main extends Application {
    private Canvas canvas;
    private double screenWidth = 800;
    private double screenHeight = 600;

    @Override
    public void start(Stage primaryStage) throws Exception{
        Group root = new Group();        
        Scene scene = new Scene(root, 800, 600);

        //        Create Canvas object and add it into the scene
        canvas = new Canvas();
        canvas.widthProperty().bind(primaryStage.widthProperty());
        canvas.heightProperty().bind(primaryStage.heightProperty());
        root.getChildren().add(canvas);

        primaryStage.setTitle("Graphics - Hello World");
        primaryStage.setScene(scene);
        primaryStage.show();


//        draw an animation
        drawAnimation(root);
    }

    private int frameWidth = 118;
    private int frameHeight = 150;
    private int totalHeight = 900;
    private int numFrames = 6;
    private int sourceHeightOffset = 0;
    private int sourceWidthOffset = 0;
    private int frameIndex = 0;


    private void drawAnimation(Group root) {
        GraphicsContext gc = canvas.getGraphicsContext2D();
//loading image sprite using relative path
        Image image = new Image(getClass().getClassLoader().getResource("images/sprites.png").toString());

        Timeline timeline = new Timeline();
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.getKeyFrames().add(new KeyFrame(Duration.millis(200), new EventHandler<ActionEvent>(){

            @Override
            public void handle(ActionEvent actionEvent) {
//                background rect fpr the characters
                gc.setFill(Color.BLACK);
                gc.fillRect(650, 450, frameWidth, frameHeight);

                gc.drawImage(image, sourceWidthOffset, sourceHeightOffset, frameWidth, frameHeight, 650, 450, frameWidth, frameHeight);

//                we want to vary frameIndex from 0 to numFrames (not included) using the %
                frameIndex = (frameIndex+1) % numFrames;

//                calculating the offset height based on the frameIndex
                sourceHeightOffset = frameHeight*frameIndex;

            }
        }));

//      Starting the timeline
        timeline.playFromStart();
    }



    public static void main(String[] args) {
        launch(args);
    }
}
