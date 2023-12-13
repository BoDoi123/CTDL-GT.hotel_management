package dao_test;

import static org.junit.jupiter.api.Assertions.*;

import dao.UserDAO;
import models.User;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mindrot.jbcrypt.BCrypt;

import java.util.List;

public class UserDAOTest {

    private UserDAO userDAO;

    @BeforeEach
    public void setUp() {
        // Khởi tạo userDAO
        userDAO = new UserDAO();
    }

    @AfterEach
    public void tearDown() {
        // Dọn dẹp môi trường kiểm thử sau mỗi phương thức kiểm thử
        List<User> userList = userDAO.getAllUser();

        for (User user : userList) {
            userDAO.deleteUserByID(user.getId());
        }
    }

    @Test
    public void testAddUser() {
        // Kiểm tra xem người dùng đã tồn tại hay chưa
        int existingUserId = userDAO.getIDByUsername("sampleUser");
        assertEquals(-1, existingUserId, "User should not exist before adding");

        // Thêm người dùng mới
        User sampleUser = createTestUser();
        userDAO.addUser(sampleUser);

        // Kiểm tra xem người dùng đã được thêm thành công hay không
        int userId = userDAO.getIDByUsername("sampleUser");
        assertTrue(userId != -1, "User should be added successfully");
    }

    @Test
    public void testUpdatePassword() {
        // Phương thức kiểm thử cập nhật mật khẩu
        User sampleUser = createTestUser();
        userDAO.addUser(sampleUser);

        int userId = userDAO.getIDByUsername("sampleUser");
        userDAO.updatePassword(userId, "newPassword");

        User updatedUser = userDAO.getUserByID(userId);
        assertTrue(BCrypt.checkpw("newPassword", updatedUser.getPassword()), "Password should be updated");
    }

    @Test
    public void testDeleteUserByID() {
        // Phương thức kiểm thử xóa người dùng theo ID
        User sampleUser = createTestUser();
        userDAO.addUser(sampleUser);

        int userId = userDAO.getIDByUsername("sampleUser");
        userDAO.deleteUserByID(userId);

        User deletedUser = userDAO.getUserByID(userId);
        assertNull(deletedUser, "User should be deleted");
    }

    @Test
    public void testAuthenticateUser() {
        // Phương thức kiểm thử xác thực người dùng
        User sampleUser = createTestUser();
        userDAO.addUser(sampleUser);

        assertTrue(userDAO.authenticateUser("sampleUser", "password"), "Authentication should succeed");
        assertFalse(userDAO.authenticateUser("sampleUser", "wrongPassword"), "Authentication should fail");
        assertFalse(userDAO.authenticateUser("nonExistentUser", "password"), "Authentication should fail");
    }

    @Test
    public void testGetIDByUsername() {
        // Phương thức kiểm thử lấy ID theo tên người dùng
        User sampleUser = createTestUser();
        userDAO.addUser(sampleUser);

        int userId = userDAO.getIDByUsername("sampleUser");
        assertTrue(userId != -1, "User ID should be retrieved successfully");
    }

    @Test
    public void testGetUserByID() {
        // Phương thức kiểm thử lấy user theo userID
        User sampleUser = createTestUser();
        userDAO.addUser(sampleUser);

        int userID = userDAO.getIDByUsername("sampleUser");
        User user = userDAO.getUserByID(userID);

        assertNotNull(user, "User should be retrieved successfully");
    }

    @Test
    public void testGetUserByUsername() {
        // Phương thức kiểm thử lấy user theo username
        User sampleUser = createTestUser();
        userDAO.addUser(sampleUser);

        User user = userDAO.getUserByUsername("sampleUser");

        assertNotNull(user, "User shoule be retrieved successfully");
    }

    @Test
    public void testGetUserRoleByUsernme() {
        // Phương thức kiểm thử lấy User Role theo usernam
        User sampleUser = createTestUser();
        userDAO.addUser(sampleUser);

        User.Role role = userDAO.getUserRoleByUsername("sampleUser");

        assertNotNull(role, "User Role should be retrieved successfully");
    }

    @Test
    public void testGetAllUser() {
        // Phương thức kiểm thử lấy tất cả user account
        User user1 = new User("sampleUser1", "password", User.Role.Staff);
        User user2 = new User("sampleUser2", "password", User.Role.Staff);
        User user3 = new User("sampleUser3", "password", User.Role.Manager);
        userDAO.addUser(user1);
        userDAO.addUser(user2);
        userDAO.addUser(user3);

        List<User> userList = userDAO.getAllUser();

        assertEquals(3, userList.size());
    }

    private User createTestUser() {
        return new User("sampleUser", "password", User.Role.Staff);
    }
}
