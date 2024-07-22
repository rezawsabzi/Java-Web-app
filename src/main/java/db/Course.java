
package db;

/**
 *
 * @author Reza Sabzi
 */
public class Course {
    
    private int courseId;
    private String title;
    private byte unitNumbers;
    
    public Course() {
    }

    public Course(int courseId, String title, byte unitNumbers) {
        this.courseId = courseId;
        this.title = title;
        this.unitNumbers = unitNumbers;
    }

    public int getCourseId() {
        return courseId;
    }

    public void setCourseId(int courseId) {
        this.courseId = courseId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public byte getUnitNumbers() {
        return unitNumbers;
    }

    public void setUnitNumbers(byte unitNumbers) {
        this.unitNumbers = unitNumbers;
    }
    
   
}
