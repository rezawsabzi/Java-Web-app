
package db;


import java.sql.PreparedStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;


/**
 *
 * @author Reza Sabzi
 */
public class CourseDAO {
    
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
