package io.github.SpaceJomber.server;

import java.sql.*;
import java.util.HashMap;

public class Database {

    private static final String url = "jdbc:mysql://localhost:3306/spacejomber";
    private static final String login = "root";
    private static final String password = ""; // don't set any password please xD

    private final Connection connection;
    private final Statement statement;

    public Database() {
        try {
            this.connection = DriverManager.getConnection(url, login, password);
            this.statement = this.connection.createStatement();
        } catch (SQLException e) {
            throw new RuntimeException("Blad podczas laczenia z bazą danych", e);
        }
    }

    public void close() {
        try {
            if (statement != null && !statement.isClosed()) {
                statement.close();
            }
            if (connection != null && !connection.isClosed()) {
                connection.close();
            }
        } catch (SQLException e) {
            throw new RuntimeException("Blad podczas zamykania polaczenia", e);
        }
    }

    public void saveWinner(String nickname) {
        try {
            String query = "INSERT INTO score (nickname, wins) VALUES (" + "'" + nickname + "'"+ ", 1);";
            this.statement.executeUpdate(query);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public HashMap<String, Integer> getBestPlayers() {
        HashMap<String, Integer> map = new HashMap<>();
        String query = "SELECT nickname, wins FROM score ORDER BY wins DESC LIMIT 5;";
        try {
            ResultSet resultSet = this.statement.executeQuery(query);
            while (resultSet.next()) {
                String nickname = resultSet.getString("nickname");
                int wins = resultSet.getInt("wins");
                map.put(nickname, wins);
            }
            return map;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void updatePlayer(String nickname) {
        int wins = 0;
        String selectquery = "SELECT wins FROM score WHERE nickname = " + "'" + nickname + "'" + ";";
        try {
            ResultSet resultSet = this.statement.executeQuery(selectquery);
            while (resultSet.next()) {
                wins = resultSet.getInt("wins");
            }
            if (wins != 0) {
                wins += 1;
                String updatequery = "UPDATE score SET wins = " + wins +  " WHERE nickname = " + "'" + nickname + "'" + ";";
                this.statement.executeUpdate(updatequery);
            } else saveWinner(nickname);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
