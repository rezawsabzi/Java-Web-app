
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
public class StudentDAO {

    public static int save(Student st) throws SQLException {
        int status = 0;
        try {
            Connection connection = DatabaseUtils.getConnection();
            String query = "insert into students(`stCode`," +
                    " `firstName`, `lastName`, `gender`) values (?,?,?,?)";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1,st.getStCode());
            preparedStatement.setString(2,st.getFirstName());
            preparedStatement.setString(3,st.getLastName());
            preparedStatement.setByte(4,st.getGender());
            status = preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        return status;
    }

    public static int update(Student st, int oldId){
        int status = 0;
        try{
            Connection connection = DatabaseUtils.getConnection();
            String query = "UPDATE `students` SET `stCode` = ?, `firstName` = ?, `lastName` = ?, `gender` = ? " +
                    "WHERE `students`.`stCode` = ?;";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1,st.getStCode());
            preparedStatement.setString(2,st.getFirstName());
            preparedStatement.setString(3,st.getLastName());
            preparedStatement.setByte(4,st.getGender());
            preparedStatement.setInt(5,oldId);

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
            String query = "delete from students where stCode = ?";
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

    public static Student getStudentById(int id){
        Student st = null;
        try{
            Connection connection = DatabaseUtils.getConnection();
            String query = "select * from students where stCode = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1,id);
            ResultSet rs = preparedStatement.executeQuery();
            if (rs.next()) {
                st = new Student(rs.getInt(1),
                        rs.getString(2),
                        rs.getString(3),
                        rs.getByte(4));
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return st;
    }



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
