package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        primaryStage.setTitle("Bulliten Board Server");
        GridPane gridPane = new GridPane();
        Label message = new Label("Message");


        gridPane.setPadding(new Insets(10, 10, 10, 10));
        gridPane.setVgap(10);
        gridPane.setHgap(10);

        gridPane.add(message,1,1);
        TextArea messageField = new TextArea("Instructor: Don't forget quiz today \n" +
                "TA: I've finished marking assignment #1\n" +
                "Instructor: Office Hours in 10 mins\n\n\n");
        messageField.setPromptText("Message Board");
        gridPane.add(messageField, 3, 2);
        messageField.setMinWidth(200);
        messageField.setMinHeight(200);

        Button exit = new Button("Exit");

        gridPane.add(exit, 3,4);

        primaryStage.setScene(new Scene(gridPane, 500, 400));
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
