package sample;

import java.text.DecimalFormat;

//class provided by csci2020u instructor
//class edited by Noshen Atashe & Saffana Ahammed

public class TestFile {
    private String filename;
    private double spamProbability;
    private String probRounded;
    private String actualClass;
    private int correctGuess;

    //Constructor class for TestFile
    public TestFile(String filename,
                    double spamProbability,
                    String guessClass,
                    String actualClass) {
        this.filename = filename.substring(filename.indexOf("0"));
        this.spamProbability = spamProbability;
        //this.guessClass = guessClass;
        this.probRounded = getSpamProbRounded();
        this.actualClass = actualClass;
        if(actualClass.equalsIgnoreCase(guessClass)){
            correctGuess = 1;
        }else{
            correctGuess = 0;
        }
    }


    //Getter and Setter methods
    public String getFilename() { return this.filename; }
    public double getSpamProbability() { return this.spamProbability; }
    public String getSpamProbRounded() {
        DecimalFormat df = new DecimalFormat("0.000000");
        return df.format(this.spamProbability);
    }
    public String getActualClass() { return this.actualClass; }
    public void setFilename(String value) { this.filename = value; }


    //Additional Getter and Setter methods
    public String getProbRounded() {return probRounded;}
    public void setProbRounded(String probRounded) {this.probRounded = probRounded;}
    public int getCorrectGuess() {
        return correctGuess;
    }
    public void setCorrectGuess(int correctGuess) {
        this.correctGuess = correctGuess;
    }
}
