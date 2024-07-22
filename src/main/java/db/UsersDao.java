package db;

import java.util.*;
import java.sql.*;

public class UsersDao {
    

    public static int save(User u) {
        int status = 0;
        try {
            Connection con = DatabaseUtils.getConnection();
            PreparedStatement ps = con.prepareStatement(
                    "insert into users(userid,username,userpass,fullName,stat,userType) values (?,?,?,?,?,?)");
            ps.setInt(1, u.getId());
            ps.setString(2, u.getUsername());
            ps.setString(3, u.getUserpass());
            ps.setString(4, u.getFullName());
            ps.setByte(5, u.getState());
            ps.setByte(6, u.getUserType());

            
            status = ps.executeUpdate();

            con.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return status;
    }//save

    public static int update(User u) {
        int status = 0;
        try {
            Connection con = DatabaseUtils.getConnection();
            PreparedStatement ps = con.prepareStatement(
                    "update users set username=?,userpass=?,FullName=?,stat=?,userType=? where userid=?");
            ps.setString(1, u.getUsername());
            ps.setString(2, u.getUserpass());
            ps.setString(3, u.getFullName());
            ps.setByte(4, u.getState());
            ps.setByte(5, u.getUserType());
            ps.setInt(6, u.getId());

            status = ps.executeUpdate();

            con.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return status;
    }//update

    public static int delete(int id) {
        int status = 0;
        try {
            Connection con = DatabaseUtils.getConnection();
            PreparedStatement ps = con.prepareStatement("delete from users where userid=?");
            ps.setInt(1, id);
            status = ps.executeUpdate();

            con.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return status;
    }//delete

    public static User getUserById(int id) {
        User user = null;

        try {
            Connection con = DatabaseUtils.getConnection();
            PreparedStatement ps = con.prepareStatement("select * from users where userid=?");
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                user = new User();
                user.setId(rs.getInt(1));
                user.setUsername(rs.getString(2));
                user.setUserpass(rs.getString(3));
                user.setFullName(rs.getString(4));
                user.setState(rs.getByte(5));
                user.setUserType(rs.getByte(6));
            }
            con.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return user;
    }//getUserById

    public static List<User> getAllUsers() {
        List<User> list = new ArrayList<User>();

        try {
            Connection con = DatabaseUtils.getConnection();
            PreparedStatement ps = con.prepareStatement("select * from users");
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                User user = new User();
                user.setId(rs.getInt(1));
                user.setUsername(rs.getString(2));
                user.setUserpass(rs.getString(3));
                user.setFullName(rs.getString(4));
                user.setState(rs.getByte(5));
                user.setUserType(rs.getByte(6));
                list.add(user);
            }
            con.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }//getAllUsers

    public static int getMaxUserId() {

        int maxId = 0;
        try {
            Connection con = DatabaseUtils.getConnection();

            PreparedStatement ps = con.prepareStatement("select max(userid) from users");

            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                maxId = rs.getInt(1);
            }
            con.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return maxId;
    }//getMaxUserId

    public static boolean validateLogin(String name, String pass) {
        boolean status = false;
        try {
            Connection con = DatabaseUtils.getConnection();

            PreparedStatement ps = con.prepareStatement(
                    "select * from users where username=? and userpass=?");
            ps.setString(1, name);
            ps.setString(2, pass);

            ResultSet rs = ps.executeQuery();
            status = rs.next();

        } catch (Exception e) {
            System.out.println(e);
        }
        return status;
    }//validateLogin
    
    public static User validateLoginGetUser(String name, String pass) {
       
        User user = null;
        try {
            Connection con = DatabaseUtils.getConnection();

            PreparedStatement ps = con.prepareStatement(
                    "select * from users where username=? and userpass=?");
            ps.setString(1, name);
            ps.setString(2, pass);

            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                user = new User();
                user.setId(rs.getInt(1));
                user.setUsername(rs.getString(2));
                user.setUserpass(rs.getString(3));
                user.setFullName(rs.getString(4));
                user.setState(rs.getByte(5));
                user.setUserType(rs.getByte(6));
            }
            con.close();

        } catch (Exception e) {
            System.out.println(e);
        }
        return user;
    }//validateLogin
}
