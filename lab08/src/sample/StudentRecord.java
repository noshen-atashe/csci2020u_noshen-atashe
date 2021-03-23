package sample;


public class StudentRecord {
    private String sid;
    private double midterm;
    private double assign;
    private double exam;
    private double finalMark;
    private String letterGrade;

    public StudentRecord(String sid, double assign, double midterm,  double exam) {
        this.sid = sid;
        this.midterm = midterm;
        this.assign = assign;
        this.exam = exam;
        this.finalMark = (midterm*.3 + assign*.2 + exam*.5);
        if(finalMark >= 80 && finalMark <= 100.0){
            this.letterGrade = "A";
        }
        else if (finalMark > 70 && finalMark <= 80){
            this.letterGrade = "B";
        }
        else if (finalMark > 60 && finalMark <= 70){
            this.letterGrade = "C";
        }
        else if (finalMark > 50 && finalMark <= 60){
            this.letterGrade = "D";
        }
        else{
            this.letterGrade = "F";
        }
    }

    public String getSid() {return sid;}
    public void setSid(String sid) {this.sid = sid;}
    public double getMidterm() {return midterm;}
    public void setMidterm(double midterm) {this.midterm = midterm;}
    public double getAssign() {return assign;}
    public void setAssign(double assign) {this.assign = assign;}
    public double getExam() {return exam;}
    public void setfExam(double fExam) {this.exam = fExam;}
    public double getFinalMark() {return finalMark;}
    public String getLetterGrade() {return letterGrade;}
}
