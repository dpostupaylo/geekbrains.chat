package com.gb.chat.fx.server.db;

import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ChatDataBase {
    private final String path = "jdbc:sqlite:D:\\databaseforjavacources\\database.db";

    public List<User> getUsersList() {
        List<User> users = new ArrayList<>();
        try(Connection connection = getConnection()) {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("select id, login, password, nick from users");

            while (resultSet.next()){
                users.add(new User(resultSet.getInt("id")
                        ,resultSet.getString("login")
                        ,resultSet.getString("password")
                        ,resultSet.getString("nick")));
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }

        return users;
    }

    public void modifyUserNick(User user, String nick){
        try(Connection connection = getConnection()) {
            Statement statement = connection.createStatement();
            statement.executeQuery("update users set nick = '"+nick+"' where id = '"+user.getId()+"'");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    private Connection getConnection() throws SQLException {
        return DriverManager.getConnection(path);
    }
}
