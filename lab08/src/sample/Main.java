package sample;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

//import javax.xml.crypto.Data;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Main extends Application {
    private Stage window;
    private BorderPane layout;
    private TableView<StudentRecord> table;
    public String currentFilename = "C:/csci2020u/lab08/test.csv";
    @Override
    public void start(Stage primaryStage) throws Exception{
        Menu fileMenu = new Menu("File");
        MenuItem newMenuItem = new MenuItem("New");
        fileMenu.getItems().add(newMenuItem);
        newMenuItem.setOnAction(e -> newScreen());

        fileMenu.getItems().add(new SeparatorMenuItem());
        MenuItem openMenuItem = new MenuItem("Open");
        fileMenu.getItems().add(openMenuItem);
        openMenuItem.setOnAction(e -> {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Open Resource File");
            File file = fileChooser.showOpenDialog(primaryStage);
            currentFilename = file.getPath();
            try {
                open(table, currentFilename);

            } catch (IOException e1) {
                e1.printStackTrace();
            }
        });

        fileMenu.getItems().add(new SeparatorMenuItem());
        MenuItem saveMenuItem = new MenuItem("Save");
        fileMenu.getItems().add(saveMenuItem);
        saveMenuItem.setOnAction(e -> {
            try {
                save(table, currentFilename);
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        });

        MenuItem saveAsMenuItem = new MenuItem("Save As...");
        fileMenu.getItems().add(saveAsMenuItem);
        saveAsMenuItem.setOnAction(e -> {
            try {
                FileChooser fileChooser = new FileChooser();
                fileChooser.setTitle("Open Resource File");
                File file = fileChooser.showOpenDialog(primaryStage);
                currentFilename = file.getPath();
                save(table, currentFilename);
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        });
        fileMenu.getItems().add(new SeparatorMenuItem());
        MenuItem exitMenuItem = new MenuItem("Exit");
        fileMenu.getItems().add(exitMenuItem);
        exitMenuItem.setOnAction( e -> System.exit(0) );

        MenuBar menuBar = new MenuBar();
        menuBar.getMenus().add(fileMenu);


        /* create the table (for the center of the user interface) */
        table = new TableView<>();
        //table.setItems(DataSource.getAllMarks());
        table.setEditable(true);

        /* create the table's columns */
        TableColumn<StudentRecord,String> sidColumn = null;
        sidColumn = new TableColumn<>("Student ID");
        sidColumn.setMinWidth(100);
        sidColumn.setCellValueFactory(new PropertyValueFactory<>("sid"));

        TableColumn<StudentRecord,Double> assignColumn = null;
        assignColumn = new TableColumn<>("Assignment");
        assignColumn.setMinWidth(100);
        assignColumn.setCellValueFactory(new PropertyValueFactory<>("assign"));

        TableColumn<StudentRecord,Double> midtermColumn = null;
        midtermColumn = new TableColumn<>("Midterm");
        midtermColumn.setMinWidth(100);
        midtermColumn.setCellValueFactory(new PropertyValueFactory<>("midterm"));


        TableColumn<StudentRecord,Double> fExamColumn = null;
        fExamColumn = new TableColumn<>("Final Exam");
        fExamColumn.setMinWidth(100);
        fExamColumn.setCellValueFactory(new PropertyValueFactory<>("exam"));

        TableColumn<StudentRecord,Double> finalMarkColumn = null;
        finalMarkColumn = new TableColumn<>("Final Mark");
        finalMarkColumn.setMinWidth(100);
        finalMarkColumn.setCellValueFactory(new PropertyValueFactory<>("finalMark"));

        TableColumn<StudentRecord,String> letterGradeColumn = null;
        letterGradeColumn = new TableColumn<>("Letter Grade");
        letterGradeColumn.setMinWidth(100);
        letterGradeColumn.setCellValueFactory(new PropertyValueFactory<>("letterGrade"));

        table.getColumns().add(sidColumn);
        table.getColumns().add(assignColumn);
        table.getColumns().add(midtermColumn);
        table.getColumns().add(fExamColumn);
        table.getColumns().add(finalMarkColumn);
        table.getColumns().add(letterGradeColumn);


        /* create an edit form (for the bottom of the user interface) */
        GridPane editArea = new GridPane();
        editArea.setPadding(new Insets(10, 10, 10, 10));
        editArea.setVgap(10);
        editArea.setHgap(10);

        Label sidLabel = new Label("SID:");
        editArea.add(sidLabel, 0, 0);
        TextField sidField = new TextField();
        sidField.setPromptText("SID");
        editArea.add(sidField, 1, 0);

        Label assignLabel = new Label("Assignments:");
        editArea.add(assignLabel, 0, 1);
        TextField assignField = new TextField();
        assignField.setPromptText("Assignment/100");
        editArea.add(assignField, 1, 1);

        Label midLabel = new Label("Midterm:");
        editArea.add(midLabel, 3, 0);
        TextField midField = new TextField();
        midField.setPromptText("Midterm/100");
        editArea.add(midField, 4, 0);

        Label fExamLabel = new Label("Final exam:");
        editArea.add(fExamLabel, 3, 1);
        TextField fExamField = new TextField();
        fExamField.setPromptText("Final Exam/100");
        editArea.add(fExamField, 4, 1);

        Button addButton = new Button("Add");
        addButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent e) {
                String sid = sidField.getText();
                double assign = Double.parseDouble(assignField.getText());
                double mid = Double.parseDouble(midField.getText());
                double fExam = Double.parseDouble(fExamField.getText());

                table.getItems().add(new StudentRecord(sid, assign, mid, fExam));

                sidField.setText("");
                assignField.setText("");
                midField.setText("");
                fExamField.setText("");
            }
        });
        editArea.add(addButton, 1, 4);

        layout = new BorderPane();
        layout.setTop(menuBar);
        layout.setCenter(table);
        layout.setBottom(editArea);

        Scene scene = new Scene(layout, 600, 600);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Lab 08");
        primaryStage.show();
    }

    public void save(TableView<StudentRecord> table, String currentFilename) throws IOException{
        BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(currentFilename));
        for (StudentRecord record: table.getItems()){
            bufferedWriter.write(String.format("%s,%f,%f,%f\n",record.getSid(),record.getAssign()
                    ,record.getMidterm(),record.getExam()));
        }
        bufferedWriter.flush();
        bufferedWriter.close();
    }
    public void newScreen(){
         //table.getColumns().get(0).setVisible(false);
         table.getItems().clear();
    }
    public TableView<StudentRecord> open(TableView<StudentRecord> table, String currentFileName) throws IOException{

        FileReader reader = new FileReader(currentFileName);
        BufferedReader in = new BufferedReader(reader);
        String line;
        DataSource data = new DataSource();
        ObservableList<StudentRecord> marks = FXCollections.observableArrayList();
        while((line = in.readLine()) != null) {
            if (line.trim().length() != 0) {
                String[] dataFields = line.split(",");
                marks.add(new StudentRecord(dataFields[0], Double.parseDouble(dataFields[1])
                        , Double.parseDouble(dataFields[2]), Double.parseDouble(dataFields[3])));
            }
        }
        table.setItems(marks);
        return table;

    }


    public static void main(String[] args) {
        launch();
    }
}
