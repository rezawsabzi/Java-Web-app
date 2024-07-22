
package db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Reza Sabzi
 */
public class StudentDAO {
    
     public static List<Student> getAllStudents() {
        List<Student> list = new ArrayList<Student>();

        try {
            Connection con = DatabaseUtils.getConnection();
            PreparedStatement ps = con.prepareStatement("select * from students");
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Student st = new Student();
                st.setStCode(rs.getInt(1));
                st.setFirstName(rs.getString(2));
                st.setLastName(rs.getString(3));
                st.setGender(rs.getByte(4));
                list.add(st);
            }
            con.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }//getAllUsers
}
