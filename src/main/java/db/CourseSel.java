package db;

public class CourseSel {

    private int courseSelId;
    private int stCode;
    private int coursePresId;
    private double grade;

    public CourseSel() {
    }

    public CourseSel(int stCode, int coursePresId) {
        this.stCode = stCode;
        this.coursePresId = coursePresId;
    }

    public CourseSel(int courseSelId, int stCode, int coursePresId) {
        this.courseSelId = courseSelId;
        this.stCode = stCode;
        this.coursePresId = coursePresId;
    }

    public CourseSel(int courseSelId, int stCode, int coursePresId, double grade) {
        this.courseSelId = courseSelId;
        this.stCode = stCode;
        this.coursePresId = coursePresId;
        this.grade = grade;
    }

    public int getCourseSelId() {
        return courseSelId;
    }

    public void setCourseSelId(int courseSelId) {
        this.courseSelId = courseSelId;
    }

    public int getStCode() {
        return stCode;
    }

    public void setStCode(int stCode) {
        this.stCode = stCode;
    }

    public int getCoursePresId() {
        return coursePresId;
    }

    public void setCoursePresId(int coursePresId) {
        this.coursePresId = coursePresId;
    }

    public double getGrade() {
        return grade;
    }

    public void setGrade(double grade) {
        this.grade = grade;
    }
}
