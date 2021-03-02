package sample;

import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;


public class Main extends Application {

    private TableView<StudentRecord> table;
    private BorderPane layout;
    private GridPane grid;

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));

        //Create the actual tableview
        table = new TableView<>();
        table.setItems(DataSource.getAllMarks());
        table.setEditable(false);

        //Create each column for the table
        TableColumn<StudentRecord, String> sidColumn = null;
        sidColumn = new TableColumn<>("SID");
        sidColumn.setMinWidth(100);
        sidColumn.setCellValueFactory(new PropertyValueFactory<>("StudentID"));

        TableColumn<StudentRecord, Float> AssignmentColumn = null;
        AssignmentColumn  = new TableColumn<>("Assignments");
        AssignmentColumn .setMinWidth(100);
        AssignmentColumn .setCellValueFactory(new PropertyValueFactory<>("Assignments"));

        TableColumn<StudentRecord, Float> MidtermColumn = null;
        MidtermColumn = new TableColumn<>("Midterm");
        MidtermColumn.setMinWidth(100);
        MidtermColumn.setCellValueFactory(new PropertyValueFactory<>("Midterm"));

        TableColumn<StudentRecord, Float> FinalExamColumn = null;
        FinalExamColumn = new TableColumn<>("Final Exam");
        FinalExamColumn.setMinWidth(100);
        FinalExamColumn.setCellValueFactory(new PropertyValueFactory<>("FinalExam"));

        TableColumn<StudentRecord, Float> FinalMarkColumn = null;
        FinalMarkColumn = new TableColumn<>("Final Mark");
        FinalMarkColumn.setMinWidth(100);
        FinalMarkColumn.setCellValueFactory(new PropertyValueFactory<>("FinalMark"));

        TableColumn<StudentRecord, String> LetterGradeColumn= null;
        LetterGradeColumn = new TableColumn<>("Letter Grade");
        LetterGradeColumn.setMinWidth(100);
        LetterGradeColumn.setCellValueFactory(new PropertyValueFactory<>("LetterGrade"));

        //get all column information and add it to the table
        table.getColumns().add(sidColumn);
        table.getColumns().add(AssignmentColumn);
        table.getColumns().add(MidtermColumn);
        table.getColumns().add(FinalExamColumn);
        table.getColumns().add(FinalMarkColumn);
        table.getColumns().add(LetterGradeColumn);


        grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(20);

        //TextField values

        TextField studentID = new TextField();
        TextField assignment = new TextField();
        TextField midterm = new TextField();
        TextField finalMark = new TextField();

        //Labels
        Label sLabel = new Label("SID: ");
        Label aLabel = new Label("Assignment");
        Label mLabel = new Label("Midterm");
        Label fLabel = new Label("Final Exam");


        ObservableList data = DataSource.getAllMarks();

        Button button = new Button("Add");
        button.setOnAction(new EventHandler<javafx.event.ActionEvent>() {
            @Override
            public void handle(javafx.event.ActionEvent event) {

                String sID = studentID.getText();
                float Assignment = Float.parseFloat(assignment.getText());
                float Midterm = Float.parseFloat(midterm.getText());
                float Final = Float.parseFloat(finalMark.getText());

                table.getItems().add(new StudentRecord(sID,Assignment,Midterm,Final));

                studentID.setText("");
                assignment.setText("");
                midterm.setText("");
                finalMark.setText("");

            }
        });


        grid.add(sLabel,1,1 );
        grid.add(aLabel, 5,1);
        grid.add(mLabel, 1,2);
        grid.add(fLabel, 5,2);
        grid.add(studentID, 2,1);
        grid.add(assignment, 6,1);
        grid.add(midterm, 2,2);
        grid.add(finalMark, 6,2);
        grid.add(button,4,3);


        layout = new BorderPane();
        layout.setCenter(table);
        layout.setBottom(grid);

        primaryStage.setTitle("Lab 05");
        primaryStage.setScene(new Scene(layout, 600, 620));
        primaryStage.show();



    }


    public static void main(String[] args) {
        launch(args);
    }
}
