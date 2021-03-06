package sample;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.BorderPane;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;
import java.io.File;
import java.util.*;

/**
 * @author: Noshen Atashe & Saffana Ahammed
 * @date: 03/08/2021
 * @project: CSCI 2020U Assignment 1
 * @file: Main.java
 */
public class Main extends Application {
    private TableView<TestFile> table;
    private BorderPane layout;
    @Override
    public void start(Stage primaryStage) throws Exception{
        //Initialize Primary stage with title
        primaryStage.setTitle("Spam Master 3000");

        System.out.println("Choose a directory");
        //Select Directory for Data
        DirectoryChooser directoryChooser = new DirectoryChooser();
        directoryChooser.setInitialDirectory(new File("."));
        File mainDirectory = directoryChooser.showDialog(primaryStage);

        //Initialize Training Phase
        System.out.println("Training in progress...");

        //Directory for ham
        File folderHam1 = new File(mainDirectory.getPath()+"/train/ham");
        File[] listOfFilesH1 = folderHam1.listFiles();
        TrainFiles hamF = new TrainFiles(listOfFilesH1, false);

        File folderHam2 = new File(mainDirectory.getPath()+"/train/ham2");
        File[] listOfFilesH2 = folderHam2.listFiles();
        TrainFiles hamF2 = new TrainFiles(listOfFilesH2, false);

        //Directory for spam
        File folderS = new File(mainDirectory.getPath()+"/train/spam");
        File[] listOfFilesS = folderS.listFiles();
        TrainFiles spamF = new TrainFiles(listOfFilesS, true);

        //Calculate Pr(S|Wi)
        HashMap<String,Double> probSpam = new HashMap<>();
        double probability;
        //determine probabilities for all spam words.
        Iterator iterator = spamF.wordGivProb.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry word = (Map.Entry)iterator.next();
            // Pr = W|S / W|S + W|H (Only exists in Ham1 file)
            if (hamF.globalCount.containsKey(word.getKey())){
                probability = ((Double) word.getValue()) / (((Double)word.getValue()) + hamF.wordGivProb.get(word.getKey()));
                probSpam.put(word.getKey().toString(), probability);
            }
            // Pr = W|S / W|S + W|H (Only exists in Ham2 file)
            else if (hamF2.globalCount.containsKey(word.getKey())) {
                probability = ((Double) word.getValue()) / (((Double) word.getValue()) + hamF2.wordGivProb.get(word.getKey()));
                probSpam.put(word.getKey().toString(), probability);
            }
            //Pr = W|S / W|S + W|H (Exists in both Ham files)
            else if (hamF.globalCount.containsKey(word.getKey()) && hamF2.globalCount.containsKey(word.getKey())){
                probability = ((Double) word.getValue()) / (((Double) word.getValue())
                        + (hamF.wordGivProb.get(word.getKey()) + hamF2.wordGivProb.get(word.getKey())));
                probSpam.put(word.getKey().toString(), probability);
            }
            //Pr = W|S / W|S + 0 (Key doesn't exist in either Ham files, therefore probability is 1)
            else{
                probSpam.put(word.getKey().toString(), 1.0);
            }
            iterator.remove();
        }
        //Testing Spam and Ham folders
        File spamFolder = new File(mainDirectory.getPath()+"/test/spam");
        File[] testSpamFiles = spamFolder.listFiles();
        File hamFolder = new File(mainDirectory.getPath()+"/test/ham");
        File[] testHamFiles = hamFolder.listFiles();
        Tester testMap = new Tester(testSpamFiles, testHamFiles, probSpam);
        System.out.println("Results Loading...");

        //Set up table for Interface
        table = new TableView<>();
        table.setItems(testMap.testData);
        table.setEditable(true);

        //Table Column Declaration; filename, actualClass, probRounded, guessClass
        TableColumn<TestFile,String> fileColumn = null;
        fileColumn = new TableColumn<>("File");
        fileColumn.setMinWidth(300);
        fileColumn.setCellValueFactory(new PropertyValueFactory<>("filename"));
        fileColumn.setCellFactory(TextFieldTableCell.<TestFile>forTableColumn());

        TableColumn<TestFile,String> classColumn = null;
        classColumn = new TableColumn<>("Actual Class");
        classColumn.setMinWidth(100);
        classColumn.setCellValueFactory(new PropertyValueFactory<>("actualClass"));
        classColumn.setCellFactory(TextFieldTableCell.<TestFile>forTableColumn());

        TableColumn<TestFile,String> probColumn = null;
        probColumn = new TableColumn<>("Spam Probability");
        probColumn.setMinWidth(100);
        probColumn.setCellValueFactory(new PropertyValueFactory<>("probRounded"));
        probColumn.setCellFactory(TextFieldTableCell.<TestFile>forTableColumn());



        table.getColumns().add(fileColumn);
        table.getColumns().add(classColumn);
        table.getColumns().add(probColumn);
        //table.getColumns().add(guessColumn);

        //Precision and Accuracy
        double correctGuesses = 0.0;
        double truePositives = 0.0;
        double falsePositives = 0.0;
        //Iterate through test map and count the correct / incorrect information.
        for (int i = 0; i < testMap.testData.size(); i++) {
            correctGuesses = correctGuesses + testMap.testData.get(i).getCorrectGuess();
            if (testMap.testData.get(i).getActualClass() == "Spam"){
                if (testMap.testData.get(i).getCorrectGuess() == 1){
                    truePositives = truePositives + 1;
                }else{
                    falsePositives = falsePositives + 1;
                }
            }
        }
        double accuracy = correctGuesses / testMap.testData.size();
        double precision = truePositives / (falsePositives+truePositives);

        Label precAcc = new Label("  Accuracy:  " + accuracy + "\n\n  Precision: " + precision);

        //Layout for stage and display results
        layout = new BorderPane();
        layout.setCenter(table);
        layout.setBottom(precAcc);
        primaryStage.setScene(new Scene(layout, 700, 700));
        primaryStage.show();
    }
    // Start application
    public static void main(String[] args) {
        launch(args);
    }
}


