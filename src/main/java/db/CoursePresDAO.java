package db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;



public class CoursePresDAO {

    public static int save(CoursePresentation c) throws SQLException {
        int status = 0;
        try {
            Connection connection = DatabaseUtils.getConnection();
            String query = "insert into coursepres(`coursePresId`, `courseId`, `insCode`) values (?,?,?)";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1,c.getCoursePresId());
            preparedStatement.setInt(2,c.getCourse().getCourseId());
            preparedStatement.setInt(3,c.getInstructor().getInsCode());
            status = preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return status;
    }

    public static int update(CoursePresentation c, int oldId){
        int status = 0;
        try{
            Connection connection = DatabaseUtils.getConnection();
            String query = "UPDATE `coursepres` SET `coursePresId` = ?, `courseId` = ?, `insCode` = ? WHERE `coursepres`.`coursePresId` = ?;";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1,c.getCoursePresId());
            preparedStatement.setInt(2,c.getCourse().getCourseId());
            preparedStatement.setInt(3,c.getInstructor().getInsCode());
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
            String query = "delete from coursepres where coursePresId = ?";
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

    public static CoursePresentation getCoursePresById(int id){
        CoursePresentation cp = null;
        try{
            Connection connection = DatabaseUtils.getConnection();
            String query = "select * from coursepres where coursePresId = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1,id);
            ResultSet rs = preparedStatement.executeQuery();
            if (rs.next()) {
                Course c = new Course();
                Instructor i = new Instructor();
                i.setInsCode(rs.getInt(3));
                c.setCourseId(rs.getInt(2));
                cp = new CoursePresentation(rs.getInt(1) , c , i );
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return cp;
    }


    public static List<CoursePresentation> getAllCoursesPres(){
        List<CoursePresentation> list = new ArrayList<>();
        try{
            Connection con = DatabaseUtils.getConnection();
            PreparedStatement pr = con.prepareStatement("select * from coursepres");
            ResultSet rs = pr.executeQuery();
            while(rs.next()){
                CoursePresentation cp = new CoursePresentation();
                cp.setCoursePresId(rs.getInt(1));
                Course c = new Course();
                Instructor i = new Instructor();
                c.setCourseId(rs.getInt(2));
                i.setInsCode(rs.getInt(3));
                cp.setCourse(c);
                cp.setInstructor(i);
                list.add(cp);
            }
            con.close();
        }catch(Exception e){
            System.out.println(e);

        }

        return list;


    }

}
