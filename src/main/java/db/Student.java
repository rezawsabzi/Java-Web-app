package db;

public class Student {
    private int stCode;
    private String firstName;
    private String lastName;
    private byte gender;
    private CoursePresentation[] selectedCourses;
    
    public Student(){
        
    }

    public Student(int stCode, String firstName, String lastName, byte gender) {
        this.stCode = stCode;
        this.firstName = firstName;
        this.lastName = lastName;
        this.gender = gender;
    }

    public Student(int stCode, String firstName, String lastName, byte gender, CoursePresentation[] selectedCourses) {
        this.stCode = stCode;
        this.firstName = firstName;
        this.lastName = lastName;
        this.gender = gender;
        this.selectedCourses = selectedCourses;
    }

    public int getStCode() {
        return stCode;
    }

    public void setStCode(int stCode) {
        this.stCode = stCode;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public byte getGender() {
        return gender;
    }

    public void setGender(byte gender) {
        this.gender = gender;
    }

    public CoursePresentation[] getSelectedCourses() {
        return selectedCourses;
    }

    public void setSelectedCourses(CoursePresentation[] selectedCourses) {
        this.selectedCourses = selectedCourses;
    }
    
    
    
}
