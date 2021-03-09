package sample;

import java.io.*;
import java.util.*;


public class TrainFiles {
    public HashMap<String,Integer> globalCount;
    public HashMap<String,Double> wordGivProb;
    public int spamLength;
    public int hamLength;
    private HashMap<String,Integer> fileCount;

    //Constructor for TrainFiles class
    public TrainFiles(File[] folder, boolean isSpam) throws IOException {
        globalCount = new HashMap<>();
        if(isSpam){spamLength = folder.length;}
        else{hamLength = folder.length;}
        //Iterate through the files, process a frequency map for each, meanwhile generating the global map.
        for (int i = 0; i < folder.length; i++) {
            processFile(folder[i]);
        }
        calculateProbability(globalCount, isSpam);
    }


    //count and add to map
    public void processFile(File file) throws IOException {
        Scanner scanner = new Scanner(file);
        fileCount = new HashMap<>();
        while (scanner.hasNext()) {
            //Parse to lowercase, improves accuracy.
            String word = scanner.next().toLowerCase();
            countWord(word);
        }
        Iterator itr = fileCount.entrySet().iterator();
        while (itr.hasNext()) {
            Map.Entry pair = (Map.Entry)itr.next();
            if(!globalCount.containsKey(pair.getKey())){
                //word doesn't exist in global map, add it.
                globalCount.put(pair.getKey().toString(),1);
            }else{
                //Increment the count
                int oldCount = globalCount.get(pair.getKey());
                globalCount.put(pair.getKey().toString(), oldCount+1);
            }
            itr.remove();
        }
    }


    private void countWord(String word) {
        if (fileCount.containsKey(word)) {
            // increment the count
            int oldCount = fileCount.get(word);
            fileCount.put(word, oldCount+1);
        } else {
            // add the word with count of 1
            fileCount.put(word, 1);
        }
    }


    public void calculateProbability(HashMap<String, Integer> dataSet, boolean isSpam) throws IOException {
        Set<String> dataKeys = dataSet.keySet();
        wordGivProb = new HashMap<>();
        Iterator<String> itr = dataKeys.iterator();
        while (itr.hasNext()) {
            String key = itr.next();
            int count = dataSet.get(key);
            if(isSpam) wordGivProb.put(key, (count / (double)spamLength));
            if(!isSpam) wordGivProb.put(key, (count / (double)hamLength));
        }
    }
}