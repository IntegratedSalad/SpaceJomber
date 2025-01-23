package io.github.SpaceJomber.server;

import java.sql.*;

public class Database {

    private static final String url = "jdbc:mysql://localhost:3306/spacejomber";
    private static final String login = "root";
    private static final String password = "haslo";

    private Connection connection;
    private Statement statement;

    public Database() {
        try {
            this.connection = DriverManager.getConnection(url, login, password);
            this.statement = this.connection.createStatement();
        } catch (SQLException e) {
            throw new RuntimeException("Błąd podczas łączenia z bazą danych", e);
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
            throw new RuntimeException("Błąd podczas zamykania zasobów", e);
        }
    }

    public ResultSet executeQuery(String sql) {
        try {
            return statement.executeQuery(sql);
        } catch (SQLException e) {
            throw new RuntimeException("Błąd podczas wykonywania zapytania: " + sql, e);
        }
    }

    public void executeUpdate(String sql) {
        try {
            statement.executeUpdate(sql);
        } catch (SQLException e) {
            throw new RuntimeException("Błąd podczas wykonywania aktualizacji: " + sql, e);
        }
    }

    public void saveWinner(String nickname, int wins) {
        String query = "INSERT INTO score (nickname, wins) VALUES ('" + nickname + "', " + wins + ");";
        try {
            statement.executeUpdate(query);
        } catch (SQLException e) {
            throw new RuntimeException("Błąd podczas zapisywania zwycięzcy", e);
        }
    }
}
