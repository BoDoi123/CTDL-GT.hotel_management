package dao;

import models.User;
import org.mindrot.jbcrypt.BCrypt;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.List;
import java.util.LinkedList;

public class UserDAO {
    private final Logger LOGGER = Logger.getLogger(UserDAO.class.getName());

    // Thao tac co ban
    public void addUser(User user) {
        try (Connection connection = DatabaseConnection.getConnection()) {
            String query = "INSERT INTO user (username, password, role) VALUES (?, ?, ?)";

            try (PreparedStatement preparedStatement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
                preparedStatement.setString(1, user.getUsername());

                // Hash mat khau truoc khi luu vao csdl
                String hashedPassword = BCrypt.hashpw(user.getPassword(), BCrypt.gensalt());
                preparedStatement.setString(2, hashedPassword);
                preparedStatement.setString(3, user.getRole().name());

                preparedStatement.executeUpdate();

                try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        user.setId(generatedKeys.getInt(1));
                    }
                }
                LOGGER.log(Level.FINE, "User add: {0}", user.getUsername());
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error adding user", e);
        }
    }

    public void updatePassword(int userID, String newPassword) {
        try (Connection connection = DatabaseConnection.getConnection()) {
            String query = "UPDATE user SET password = ? WHERE id = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {

                // Hash mat khau truoc khi luu vao csdl
                String hashedPassword = BCrypt.hashpw(newPassword, BCrypt.gensalt());
                preparedStatement.setString(1, hashedPassword);

                preparedStatement.setInt(2, userID);

                preparedStatement.executeUpdate();
                LOGGER.log(Level.FINE, "Password updated for user with ID : {0}", userID);
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error update password", e);
        }
    }

    public void deleteUserByID(int userID) {
        try (Connection connection = DatabaseConnection.getConnection()) {
            String query = "DELETE FROM user WHERE id = ?";

            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setInt(1, userID);

                preparedStatement.executeUpdate();
                LOGGER.log(Level.FINE, "User deleted: {0}", userID);
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error delete user", e);
        }
    }

    // Xac thuc user
    public boolean authenticateUser(String username, String password) {
        User user = getUserByUsername(username);

        if (user != null) {
            return BCrypt.checkpw(password, user.getPassword());
        }
        return false;
    }

    // Thao tac truy van tim kiem du lieu
    public int getIDByUsername(String username) {
        try (Connection connection = DatabaseConnection.getConnection()) {
            String query = "SELECT id FROM user WHERE username = ?";

            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setString(1, username);

                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    if (resultSet != null && resultSet.next()) {
                        int id = resultSet.getInt("id");
                        LOGGER.log(Level.FINE, "ID retrieved: {0}", id);
                        return id;
                    }
                }
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error getting user ID by username from database", e);
        }
        return -1;
    }

    public User getUserByID(int userID) {
        try (Connection connection = DatabaseConnection.getConnection()) {
            String query = "SELECT * FROM user WHERE id = ?";

            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setInt(1, userID);


                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    if (resultSet != null && resultSet.next()) {
                        User user = mapResultSetToUser(resultSet);
                        LOGGER.log(Level.FINE, "User Retrieved: {0}", user.getUsername());
                        return user;
                    }
                }
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error getting user by id from database", e);
        }

        return null;
    }

    public User getUserByUsername(String username) {
        try (Connection connection = DatabaseConnection.getConnection()) {
            String query = "SELECT * FROM user WHERE username = ?";

            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setString(1, username);

                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    if (resultSet != null && resultSet.next()) {
                        User user = mapResultSetToUser(resultSet);
                        LOGGER.log(Level.FINE, "User Retrieved: {0}", user.getUsername());
                        return user;
                    }
                }
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error getting user by username from database", e);
        }

        return null;
    }

    public User.Role getUserRoleByUsername(String username) {
        try (Connection connection = DatabaseConnection.getConnection()) {
            String query = "SELECT role FROM user WHERE username = ?";

            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setString(1, username);

                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    if (resultSet != null && resultSet.next()) {
                        User.Role role = User.Role.valueOf(resultSet.getString("role"));
                        LOGGER.log(Level.FINE, "User Role Retrieved: {0}", role.name());
                        return role;
                    }
                }
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error getting user role by username from database", e);
        }

        return null;
    }

    public List<User> getAllUser() {
        List<User> userList = new LinkedList<>();

        try (Connection connection = DatabaseConnection.getConnection()) {
            String query = "SELECT * FROM user";

            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                ResultSet resultSet = preparedStatement.executeQuery();

                while (resultSet.next()) {
                    User user = mapResultSetToUser(resultSet);
                    userList.add(user);
                }
                LOGGER.log(Level.FINE, "Got all user account from database", userList.size());
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error getting all user account from database", e);
        }

        return userList;
    }

    private User mapResultSetToUser(ResultSet resultSet) throws SQLException {
        int id = resultSet.getInt("id");
        String username = resultSet.getString("username");
        String password = resultSet.getString("password");
        User.Role role = User.Role.valueOf(resultSet.getString("role"));

        User user = new User(username, password, role);
        user.setId(id);
        return user;
    }
}
