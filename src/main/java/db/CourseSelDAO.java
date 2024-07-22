package db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class CourseSelDAO {


    public static ArrayList<CourseSel> getCourseSelForStCode(int id){
        ArrayList<CourseSel> clist = new ArrayList<>();
        try{
            Connection connection = DatabaseUtils.getConnection();
            String query = "select * from coursesel where stCode = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1,id);
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                CourseSel sc = new CourseSel(rs.getInt(1) ,
                        rs.getInt(2), rs.getInt(3),
                        rs.getDouble(4));
                clist.add(sc);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return clist;
    }
}
