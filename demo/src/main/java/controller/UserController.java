package controller;

import lombok.Getter;
import lombok.Setter;

import dao.UserDAO;
import models.User;

import org.mindrot.jbcrypt.BCrypt;

import java.util.logging.Logger;
import java.util.logging.Level;

@Getter
@Setter
public class UserController {
    private static final Logger LOGGER = Logger.getLogger(UserController.class.getName());
    private UserDAO userDAO;

    public UserController(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    public boolean loginUser(String username, String password) {
        try {
            User user = userDAO.getUserByUsername(username);

            if (user != null && BCrypt.checkpw(password, user.getPassword())) {
                LOGGER.log(Level.FINE, "Login successful for user: {0}", username);

                return true;
            } else {
                LOGGER.log(Level.WARNING, "Invalid username or password for user: ", username);
                return false;
            }
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error during login for user", e);
            return false;
        }
    }

    public boolean changePassword(String username, String newPassword) {
        try {
            User user = userDAO.getUserByUsername(username);

            if (user != null) {
                int userID = user.getId();
                userDAO.updatePassword(userID, newPassword);

                LOGGER.log(Level.FINE, "Password has been changed: ", newPassword);
                return true;
            } else {
                LOGGER.log(Level.WARNING, "Invalid username: ", username);
                return false;
            }
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error during change password for user", e);
            return false;
        }
    }
}
