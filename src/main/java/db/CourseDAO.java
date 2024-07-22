
package db;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


/**
 *
 * @author Reza Sabzi
 */
public class CourseDAO {


    public static int save(Course c) throws SQLException {
        int status = 0;
        try {
            Connection connection = DatabaseUtils.getConnection();
            String query = "insert into courses(courseId,title,unitNumber) values (?,?,?)";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1,c.getCourseId());
            preparedStatement.setString(2,c.getTitle());
            preparedStatement.setByte(3,c.getUnitNumbers());
            status = preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return status;
    }

    public static int update(Course c, int oldId){
        int status = 0;
        try{
            Connection connection = DatabaseUtils.getConnection();
            String query = "UPDATE `courses` SET `courseId` = ?, `title` = ?, `unitNumber` = ? WHERE `courses`.`courseId` = ?;";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1,c.getCourseId());
            preparedStatement.setString(2,c.getTitle());
            preparedStatement.setByte(3,c.getUnitNumbers());
            preparedStatement.setInt(4,oldId);

            status = preparedStatement.executeUpdate();

            connection.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return status;
    }


    public static int delete(int id){
        int status = 0 ;
        try {
            Connection connection = DatabaseUtils.getConnection();
            String query = "delete from courses where courseId = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1,id);

            status = preparedStatement.executeUpdate();

            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }

        return status;
    }

    public static Course getCourseById(int id){
        Course c = null;
        try{
            Connection connection = DatabaseUtils.getConnection();
            String query = "select * from courses where courseId = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1,id);
            ResultSet rs = preparedStatement.executeQuery();
            if (rs.next()) {
                c = new Course(rs.getInt(1) , rs.getString(2),  rs.getByte(3));
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return c;
    }


    public static List<Course> getAllCourses(){
         List<Course> list = new ArrayList<Course>();
        try{
            Connection con = DatabaseUtils.getConnection();
            PreparedStatement pr = con.prepareStatement("select * from Courses");
            ResultSet rs = pr.executeQuery();
            while(rs.next()){
                Course c = new Course();
                c.setCourseId(rs.getInt(1));
                c.setTitle(rs.getString(2));
                c.setUnitNumbers(rs.getByte(3));
                list.add(c);
                
            }
            con.close();
        }catch(Exception e){
            System.out.println(e);
                 
        }
       
        return list;
        
        
    }

   
}
