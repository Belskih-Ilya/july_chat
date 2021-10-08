package ru.geekbrains.july_chat.chat_server.error;

import java.sql.*;

public class UsersDb {
    private static UsersDb usersDb;
    private Connection connection;

    private UsersDb() {
        connectDb();
    }

    public static UsersDb getUsersDb() {
        if  (usersDb != null) {
            return usersDb;
        }
        usersDb = new UsersDb();
        return usersDb;
    }

    public String getNickname (String login, String password) {
        try (PreparedStatement ps = connection.prepareStatement("select nickname from users where login = ? and password = ?;")) {
            ps.setString(1, login);
            ps.setString(2, password);
            ResultSet rs = ps.executeQuery();
            if(rs.next()) {
                String nickname = rs.getString("nickname");
                rs.close();
                System.out.println("nickname is: " + nickname);
                return nickname;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        throw new UserNotFoundException("User not found");
    }

    private void connectDb() {
        try {
            connection = DriverManager.getConnection("jdbc:sqlite:users.db");
        } catch (SQLException e) {
            System.out.println("Can't connect to users.db");
        }
        System.out.println("Connected to users.db");
    }

    public void disconnectDb() {
        if (connection != null) {
            try {
                connection.close();
                System.out.println("Disconnected from users.db");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

}
