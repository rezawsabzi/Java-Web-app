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
public class InstructorDAO {

    public static int save(Instructor ins) throws SQLException {
        int status = 0;
        try {
            Connection connection = DatabaseUtils.getConnection();
            String query = "insert into instructors(insCode,firstName,lastName, gender) values (?,?,?,?)";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1,ins.getInsCode());
            preparedStatement.setString(2,ins.getFirstName());
            preparedStatement.setString(3,ins.getLastName());
            preparedStatement.setByte(4,ins.getGender());
            status = preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return status;
    }

    public static int update(Instructor ins, int oldId){
        int status = 0;
        try{
            Connection connection = DatabaseUtils.getConnection();
            String query = "UPDATE `instructors` " +
                    "SET `insCode` = ?, " +
                    "`firstName` = ?, " +
                    "`lastName` = ?, `" +
                    "gender` = ? " +
                    "WHERE `instructors`.`insCode` = ?;";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1,ins.getInsCode());
            preparedStatement.setString(2,ins.getFirstName());
            preparedStatement.setString(3,ins.getLastName());
            preparedStatement.setByte(4,ins.getGender());
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
            String query = "delete from instructors where insCode = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1,id);

            status = preparedStatement.executeUpdate();

            connection.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return status;
    }

    public static Instructor getInstructorById(int id){
        Instructor ins = null;
        try{
            Connection connection = DatabaseUtils.getConnection();
            String query = "select * from instructors where insCode = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1,id);
            ResultSet rs = preparedStatement.executeQuery();
            if (rs.next()) {
                ins = new Instructor(rs.getInt(1) , rs.getString(2), rs.getString(3), rs.getByte(4));
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return ins;
    }
    
    public static List<Instructor> getAllInstructors(){
        List<Instructor> insList = new ArrayList<Instructor>();
        try{
            Connection con = DatabaseUtils.getConnection();
            PreparedStatement pr = con.prepareStatement("select * from instructors");
            ResultSet rs = pr.executeQuery();
            while(rs.next()){
                Instructor ins = new Instructor();
                ins.setInsCode(rs.getInt(1));
                ins.setFirstName(rs.getString(2));
                ins.setLastName(rs.getString(3));
                ins.setGender(rs.getByte(4));
                
                insList.add(ins);
            }
            con.close();
            
            
            
        }catch(Exception e){
            System.out.println(e);
        }   
    
        return insList;
        
    }
    
    public static int saveNewInstructor(Instructor ins){
        
        int status = 0;
        try{
            Connection con = DatabaseUtils.getConnection();
            PreparedStatement ps = con.prepareStatement
                ("insert into instructors(insCode,firstName,lastName,gender) values (?,?,?,?)");
            ps.setInt(1, ins.getInsCode());
            ps.setString(2, ins.getFirstName());
            ps.setString(3, ins.getLastName());
            ps.setByte(4, ins.getGender());
            
            
            status  = ps.executeUpdate();
            
            con.close();
            
        }catch(Exception e){
            System.out.println(e);
        }
        return status;
       
    }
}
