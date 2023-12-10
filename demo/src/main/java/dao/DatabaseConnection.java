package dao;

import java.io.IOException;
import java.io.InputStream;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DatabaseConnection {
    private static final Logger LOGGER = Logger.getLogger(DatabaseConnection.class.getName());
    private static final Properties prop = new Properties();

    static {
        try (InputStream input = DatabaseConnection.class.getClassLoader().getResourceAsStream("application.properties")) {
            prop.load(input);
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, "Error loading application.properties", e);
        }
    }
    private static final String URL = prop.getProperty("db.url");
    private static final String USERNAME = prop.getProperty("db.username");
    private static final String PASSWORD = prop.getProperty("db.password");

    public static Connection getConnection() throws SQLException {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            return DriverManager.getConnection(URL, USERNAME, PASSWORD);
        } catch (ClassNotFoundException | SQLException e) {
            LOGGER.log(Level.SEVERE, "Error connection to the database", e);
            throw new RuntimeException("Khong the ket noi den co so du lieu.");
        }
    }
}
