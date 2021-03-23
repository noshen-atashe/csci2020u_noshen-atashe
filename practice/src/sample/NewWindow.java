package sample;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.io.IOException;

public class NewWindow {
    public void openWindow(String buttonN){
        try {
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("NewWindow.fxml"));

            Scene scene = new Scene(fxmlLoader.load(), 600, 400);
            Stage stage = new Stage();
            stage.setTitle(buttonN + " Window");
            GridPane gridAnimation = new GridPane();
            gridAnimation.setAlignment(Pos.TOP_LEFT);
            gridAnimation.setHgap(10);
            gridAnimation.setVgap(10);
            Button backButton = new Button("Back");
            backButton.setPrefWidth(200);
            backButton.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent actionEvent) {
                    System.out.println("Clicked on back button");
                }
            });

            gridAnimation.add(backButton, 0,1);
            scene = new Scene(gridAnimation, 300, 275);

            stage.setScene(scene);
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
