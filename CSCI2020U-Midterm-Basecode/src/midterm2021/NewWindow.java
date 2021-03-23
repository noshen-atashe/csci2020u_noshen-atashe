package midterm2021;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.stage.Stage;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;

import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.util.Duration;


import java.io.File;
import java.io.IOException;

public class NewWindow {
    public void openWindow(String buttonN){
        try {
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("NewWindow.fxml"));

            Scene scene = new Scene(fxmlLoader.load(), 600, 400);
            Stage stage = new Stage();
            stage.setTitle(buttonN + " Window");
            GridPane gridSecond = new GridPane();
            gridSecond.setAlignment(Pos.TOP_LEFT);
            gridSecond.setHgap(10);
            gridSecond.setVgap(10);


            //go back to Main screen if "Back" button is presses
            Button backButton = new Button("Back");
            backButton.setPrefWidth(50);
            backButton.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent actionEvent) {
                    System.out.println("Clicked on back button");
                    Main back = new Main();
                    try {
                        back.start(stage);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });


            //Show animation if buttonN is "Animation"
            if(buttonN=="Animation"){
                System.out.println("Animation should start");
                Canvas canvas = new Canvas();
                canvas.widthProperty().bind(stage.widthProperty());
                canvas.heightProperty().bind(stage.heightProperty());
                gridSecond.getChildren().add(canvas);
                int frameWidth = 118;
                int frameHeight = 150;
                int totalHeight = 900;
                int numFrames = 6;
                final int[] sourceHeightOffset = {0};
                int sourceWidthOffset = 0;
                final int[] frameIndex = {0};
                Image image = new Image(getClass().getClassLoader().getResource("ducks.png").toString());
                GraphicsContext gc = canvas.getGraphicsContext2D();
                Timeline timeline = new Timeline();
                timeline.setCycleCount(Timeline.INDEFINITE);
                timeline.getKeyFrames().add(new KeyFrame(Duration.millis(200), new EventHandler<ActionEvent>(){

                    @Override
                    public void handle(ActionEvent actionEvent) {//
                        //background rect for the characters
                        gc.setFill(Color.BLACK);
                        gc.fillRect(650, 450, frameWidth, frameHeight);

                        gc.drawImage(image, sourceWidthOffset, sourceHeightOffset[0], frameWidth, frameHeight,
                                650, 450, frameWidth, frameHeight);

                        //we want to vary frameIndex from 0 to numFrames (not included) using the %
                        frameIndex[0] = (frameIndex[0] +1) % numFrames;

                        //calculating the offset height based on the frameIndex
                        sourceHeightOffset[0] = frameHeight* frameIndex[0];
                    }
                }));
                //Starting the timeline
                timeline.playFromStart();
            }
            //Draw initials if buttonN is "2D"
            else if(buttonN=="2D"){
                System.out.println("Initials in 2D should appear");
                //creating label
                Label initials = new Label("Initials: N A");
                gridSecond.add(initials,1,0);
                //Writing 'N'
                //Creating a line object
                Line lineN1 = new Line();
                Line lineN2 = new Line();
                Line lineN3 = new Line();
                Line lineA1 = new Line();
                Line lineA2 = new Line();
                Line lineA3 = new Line();

                //Setting the properties to a line
                lineN1.setStartX(0);
                lineN1.setStartY(0);
                lineN1.setEndX(0);
                lineN1.setEndY(200);
                //Setting the properties to a line
                lineN2.setStartX(50);
                lineN2.setStartY(0);
                lineN2.setEndX(50);
                lineN2.setEndY(200);
                //Setting the properties to a line
                lineN3.setStartX(0);
                lineN3.setStartY(0);
                lineN3.setEndX(100);
                lineN3.setEndY(200);

                //Setting the properties to a line
                lineA1.setStartX(100);
                lineA1.setStartY(0);
                lineA1.setEndX(0);
                lineA1.setEndY(200);
                //Setting the properties to a line
                lineA2.setStartX(100);
                lineA2.setStartY(0);
                lineA2.setEndX(200);
                lineA2.setEndY(200);
                //Setting the properties to a line
                lineA3.setStartX(50);
                lineA3.setStartY(100);
                lineA3.setEndX(100);
                lineA3.setEndY(100);

                //Creating a Group
                Group root = new Group(lineN1);
                gridSecond.add(root,1,3);
                Group root2 = new Group(lineN2);
                gridSecond.add(root2,2,3);
                Group root3 = new Group(lineN3);
                gridSecond.add(root3,1,3);

                //Creating a Group
                Group root4 = new Group(lineA1);
                gridSecond.add(root4,3,3);
                Group root5 = new Group(lineA2);
                gridSecond.add(root5,4,3);
                Group root6 = new Group(lineA3);
                gridSecond.add(root6,4,3);
            }
            //Show information from XML if buttonN is "About"
            else if(buttonN=="About"){
                System.out.println("About information should appear");

                File xmlFile = new File("About.xml");

                Label id = new Label("Student id: 100620403");
                Label name = new Label("Name: Noshen Atashe");
                Label email = new Label("Email: noshen.atashe@ontariotechu.net");
                Label description = new Label("Description: This is the \"About\" section of the \n" +
                                                    "\t\t 2021W midterm for csci2020u: Software Systems Development \n" +
                                                    "\t\t and Integration course.");
                gridSecond.add(id,1,1);
                gridSecond.add(name,1,2);
                gridSecond.add(email,1,3);
                gridSecond.add(description,1,4);
            }



            gridSecond.add(backButton, 0,0);
            scene = new Scene(gridSecond, 600, 275);

            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
