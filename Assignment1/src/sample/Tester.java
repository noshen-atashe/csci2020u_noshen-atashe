package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.io.*;
import java.util.*;


public class Tester {
    public ObservableList<TestFile> testData = FXCollections.observableArrayList();

    //class constructor
    public Tester(File[] spamList, File[] hamList, HashMap<String, Double> wordHashMap) throws IOException{
        CalculateProb(spamList, wordHashMap);
        CalculateProb(hamList, wordHashMap);
    }

    //Calculate probability of spam or ham by scanning each word
    private void CalculateProb(File[] folder, HashMap<String, Double> wordMap) throws IOException {
        for (File file : folder) {
            String path = file.getPath();
            String actualClass = "Ham";
            if (path.contains("spam")) {
                actualClass = "Spam";
            }

            double total = 0;
            FileReader fileReader = new FileReader(file);
            Scanner scanner = new Scanner(fileReader);

            while (scanner.hasNext()) {
                String word = scanner.next().toLowerCase();
                    //Find if word exists in word map, if not it's irrelevant.
                    if (wordMap.containsKey(word)) {
                        double wordSpamProbability = wordMap.get(word);
                        //Apply Naive Bayes, log calculation
                        if (wordSpamProbability > 0.0f && wordSpamProbability < 1.0f) {
                            total += Math.log(1 - wordSpamProbability)
                                    - Math.log(wordSpamProbability);
                        }
                    }
            }
            fileReader.close();
            String guessClass = actualClass;
            //Sum running total of Naive Bayes
            double spamProbability = 1 / (1 + Math.pow(Math.E, total));
            //Guess class column calculation
            if (spamProbability >= 0.5){
                guessClass = "Spam";
            }else if(spamProbability < 0.5) {
                guessClass = "Ham";
            }
            //Add file with calculated probabilities to the full testData set.
            TestFile testFile = new TestFile(path, spamProbability, guessClass, actualClass);
            testData.add(testFile);
        }
    }
}
