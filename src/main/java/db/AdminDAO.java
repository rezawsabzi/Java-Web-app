package db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AdminDAO {
    public static Admin validateLogin(String username, String password) {
        Admin admin = null;
        try{
            Connection connection = DatabaseUtils.getConnection();
            String query = "SELECT * FROM users WHERE username = ? AND userpass = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, password);

            ResultSet resultSet = preparedStatement.executeQuery();

            if(resultSet.next()){
                admin = new Admin(resultSet.getString(1), resultSet.getString(2), resultSet.getString(3));
            }

            connection.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return admin;
    }

}
