package utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DatabaseManager {

    private static Connection connection;

    private static final String DB_URL = ConfigReader.getProperty("db.url") != null
            ? ConfigReader.getProperty("db.url")
            : "jdbc:h2:mem:testdb;DB_CLOSE_DELAY=-1";
    private static final String DB_USER = ConfigReader.getProperty("db.user") != null
            ? ConfigReader.getProperty("db.user")
            : "sa";
    private static final String DB_PASSWORD = ConfigReader.getProperty("db.password") != null
            ? ConfigReader.getProperty("db.password")
            : "";

    public static synchronized Connection getConnection() throws SQLException {
        if (connection == null || connection.isClosed()) {
            connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
        }
        return connection;
    }

    public static synchronized void closeConnection() {
        if (connection != null) {
            try {
                if (!connection.isClosed()) {
                    connection.close();
                }
            } catch (SQLException e) {
                System.err.println("Error closing database connection: " + e.getMessage());
            } finally {
                connection = null;
            }
        }
    }

    public static List<Map<String, Object>> executeQuery(String sql, Object... params) {
        List<Map<String, Object>> rows = new ArrayList<>();

        try (PreparedStatement statement = getConnection().prepareStatement(sql)) {
            for (int i = 0; i < params.length; i++) {
                statement.setObject(i + 1, params[i]);
            }

            try (ResultSet resultSet = statement.executeQuery()) {
                ResultSetMetaData metaData = resultSet.getMetaData();
                int columnCount = metaData.getColumnCount();

                while (resultSet.next()) {
                    Map<String, Object> row = new HashMap<>();
                    for (int i = 1; i <= columnCount; i++) {
                        row.put(metaData.getColumnLabel(i).toUpperCase(), resultSet.getObject(i));
                    }
                    rows.add(row);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Database query failed: " + sql + " | Error: " + e.getMessage(), e);
        }

        return rows;
    }

    public static int executeUpdate(String sql, Object... params) {
        try (PreparedStatement statement = getConnection().prepareStatement(sql)) {
            for (int i = 0; i < params.length; i++) {
                statement.setObject(i + 1, params[i]);
            }
            return statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Database update failed: " + sql + " | Error: " + e.getMessage(), e);
        }
    }
}