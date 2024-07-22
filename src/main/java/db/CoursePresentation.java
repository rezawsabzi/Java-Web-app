
package db;

public class CoursePresentation {
    private int coursePresId;
    private Course course;
    private Instructor instructor;

    public CoursePresentation() {
    }

    public CoursePresentation(int coursePresId, Course course, Instructor instructor) {
        this.coursePresId = coursePresId;
        this.course = course;
        this.instructor = instructor;
    }

    public int getCoursePresId() {
        return coursePresId;
    }

    public void setCoursePresId(int coursePresId) {
        this.coursePresId = coursePresId;
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    public Instructor getInstructor() {
        return instructor;
    }

    public void setInstructor(Instructor instructor) {
        this.instructor = instructor;
    }
}
