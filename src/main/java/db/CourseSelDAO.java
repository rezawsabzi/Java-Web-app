package db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class CourseSelDAO {


    public static int save(CourseSel sc) throws SQLException {
        int status = 0;
        try {
            Connection connection = DatabaseUtils.getConnection();
            String query = "insert into coursesel(`stCode`," +
                    " `coursePresId`, `grade`) values (?,?,?)";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1,sc.getStCode());
            preparedStatement.setInt(2,sc.getCoursePresId());
            preparedStatement.setDouble(3,sc.getGrade());
            status = preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        return status;
    }

    public static int update(CourseSel c, int oldId){
        int status = 0;
//        try{
//            Connection connection = DatabaseUtils.getConnection();
//            String query = "UPDATE `students` SET `stCode` = ?, `firstName` = ?, `lastName` = ?, `gender` = ? " +
//                    "WHERE `students`.`stCode` = ?;";
//            PreparedStatement preparedStatement = connection.prepareStatement(query);
//            preparedStatement.setInt(1,c.getCourseSelId());
//            preparedStatement.setInt(2,c.getStCode());
//            preparedStatement.setInt(3,c.getCourseSelId());
//            preparedStatement.setDouble(4,c.getGrade());
//            preparedStatement.setInt(5,oldId);
//
//            status = preparedStatement.executeUpdate();
//
//            connection.close();
//        } catch (SQLException e) {
//            throw new RuntimeException(e);
//        }
        return status;
    }

    public static int delete(int id ){
        int status = 0 ;
        try {
            Connection connection = DatabaseUtils.getConnection();
            String query = "delete from coursesel where courseSelId = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1,id);
//            preparedStatement.setInt(2,cid);
            status = preparedStatement.executeUpdate();

            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }

        return status;
    }

    public static CourseSel getSelCourseByIds(int sid,int cid){
        CourseSel c = null;
        try{
            Connection connection = DatabaseUtils.getConnection();
            String query = "select * from coursesel where stCode = ? and coursePresId = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1,sid);
            preparedStatement.setInt(2,cid);
            ResultSet rs = preparedStatement.executeQuery();
            if (rs.next()) {
                c = new CourseSel(rs.getInt(1),
                        rs.getInt(2),
                        rs.getInt(3),
                        rs.getDouble(4));
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return c;
    }

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
