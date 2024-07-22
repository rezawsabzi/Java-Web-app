package db;

import java.sql.Connection;
import java.sql.DriverManager;

public class DatabaseUtils {
    public static String userName = "root";
    public static String password = "";
    public static String className = "com.mysql.cj.jdbc.Driver";
    public static String db_con_str = "jdbc:mysql://127.0.0.1:3306/contactdb?characterEncoding=utf-8&useUnicode=true";

    public static Connection getConnection() {
        Connection con = null;

        try {
            Class.forName(className);
            String conStr = db_con_str;
            con = DriverManager.getConnection(conStr, userName, password);
        } catch (Exception e) {
            System.out.println(e);
        }
        return con;
    }//getConnection 
}
