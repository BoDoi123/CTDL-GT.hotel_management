package daotest;

import static org.junit.jupiter.api.Assertions.*;

import models.Employee;
import models.User;
import dao.EmployeeDAO;
import dao.UserDAO;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;

public class EmployeeDAOTest {

    private EmployeeDAO employeeDAO;
    private UserDAO userDAO;

    @BeforeEach
    void setUp() {
        // Khởi tạo đối tượng EmployeeDAO và UserDAO
        employeeDAO = new EmployeeDAO();
        userDAO = new UserDAO();
    }

    @AfterEach
    void tearDown() {
        // Xóa bỏ dữ liệu sau mỗi lần kiểm thử
        cleanUpTestData();

        // Hủy tạo đối tượng EmployeeDAO và UserDAO
        employeeDAO = null;
        userDAO = null;
    }

    private void cleanUpTestData() {
        // Xóa dữ liệu sau mỗi lần kiểm thử
        List<Employee> employees = employeeDAO.getAllEmployee();
        for (Employee employee : employees) {
            employeeDAO.deleteEmployee(employee.getId());
        }

        List<User> users = userDAO.getAllUser();
        for (User user : users) {
            userDAO.deleteUserByID(user.getId());
        }
    }

    @Test
    void testEmployeeCreation() {
        // Khởi tạo nhân viên
        User staff = new User("staff1", "password", User.Role.Staff);
        userDAO.addUser(staff);

        Employee employee = new Employee(staff, "Staff1", "City", "1234567", LocalDate.of(2000, 11, 20), Employee.Gender.Male, 2000);
        employeeDAO.addEmployee(employee);

        // Truy vấn và kiểm thử
        Employee retrieveEmployee = employeeDAO.getEmployeeByID(employee.getId());
        assertNotNull(retrieveEmployee);
        assertEquals(employee.getName(), retrieveEmployee.getName());
    }

    @Test
    void testEmployeeUpdate() {
        // Khởi tạo nhân viên
        User staff = new User("staff1", "password", User.Role.Staff);
        userDAO.addUser(staff);

        Employee employee = new Employee(staff, "Staff1", "City", "1234567", LocalDate.of(2000, 11, 20), Employee.Gender.Male, 2000);
        employeeDAO.addEmployee(employee);

        // Update
        Employee retrieveEmployee = employeeDAO.getEmployeeByID(employee.getId());
        assertNotNull(retrieveEmployee);
        retrieveEmployee.setName("UpdateName");
        employeeDAO.updateEmployee(retrieveEmployee);

        // Kiểm thử Update
        retrieveEmployee = employeeDAO.getEmployeeByID(employee.getId());
        assertNotNull(retrieveEmployee);
        assertEquals("UpdateName", retrieveEmployee.getName());
    }

    @Test
    void testEmployeeDeletion() {
        // Khởi tạo nhân viên
        User staff = new User("staff1", "password", User.Role.Staff);
        userDAO.addUser(staff);

        Employee employee = new Employee(staff, "Staff1", "City", "1234567", LocalDate.of(2000, 11, 20), Employee.Gender.Male, 2000);
        employeeDAO.addEmployee(employee);

        // Delete
        employeeDAO.deleteEmployee(employee.getId());

        // Kiểm thử Delete
        Employee retrieveEmployee = employeeDAO.getEmployeeByID(employee.getId());
        assertNull(retrieveEmployee);
    }

    @Test
    void testGetAllEmployeeRoleStaff() {
        // Khởi tạo nhân viên
        User staff1 = new User("staff1", "password", User.Role.Staff);
        User staff2 = new User("staff2", "password", User.Role.Staff);
        User manager = new User("manager", "password", User.Role.Manager);
        userDAO.addUser(staff1);
        userDAO.addUser(staff2);
        userDAO.addUser(manager);

        Employee employee1 = new Employee(staff1, "Staff1", "City1", "1234567", LocalDate.of(2000, 11, 20), Employee.Gender.Male, 2000);
        Employee employee2 = new Employee(staff2, "Staff2", "City2", "0123456", LocalDate.of(1999, 12, 20), Employee.Gender.Female, 3000);
        Employee employee3 = new Employee(manager, "Manager", "City3", "9876543", LocalDate.of(1998, 10, 20), Employee.Gender.Female, 10000);
        employeeDAO.addEmployee(employee1);
        employeeDAO.addEmployee(employee2);
        employeeDAO.addEmployee(employee3);

        // Truy vấn
        List<Employee> staffList = employeeDAO.getAllEmployeeRoleStaff();

        // Kiểm thử
        assertEquals(2, staffList.size());
    }
}
