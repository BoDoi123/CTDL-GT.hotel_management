package dao_test;

import static org.junit.jupiter.api.Assertions.*;

import models.Customer;
import models.User;
import dao.CustomerDAO;
import dao.UserDAO;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;

import java.time.LocalDate;
import java.util.List;

public class CustomerDAOTest {
    private UserDAO userDAO;
    private CustomerDAO customerDAO;

    @BeforeEach
    public void setUp() {
        // Khởi tạo
        customerDAO = new CustomerDAO();
        userDAO = new UserDAO();
    }

    @AfterEach
    public void tearDown() {
        // Dọn dẹp môi trường kiểm thử
        cleanUpTestData();

        // Hủy tạo đối tượng
        customerDAO = null;
        userDAO = null;
    }

    private void cleanUpTestData() {
        // Xóa dữ liệu sau mỗi lần kiểm thử
        List<Customer> customers = customerDAO.getAllCustomers();
        for (Customer customer : customers) {
            customerDAO.deleteCustomer(customer.getId());
        }

        List<User> users = userDAO.getAllUser();
        for (User user : users) {
            userDAO.deleteUserByID(user.getId());
        }
    }

    @Test
    public void testAddCustomer() {
        Customer customer = createTestCustomer();

        // Truy vẫn và kiểm thử
        Customer retrievedCustomer = customerDAO.getCustomerByID(customer.getId());
        assertNotNull(retrievedCustomer, "Customer should be added successfully");
    }

    @Test
    public void testUpdateCustomer() {
        Customer customer = createTestCustomer();

        // Thay đổi thông tin khách hàng
        customer.setName("NewName");
        customer.setGender(Customer.Gender.Female);
        customer.setBirthday(LocalDate.now().minusYears(20));
        customer.setIdentification("NewID");
        customer.setHometown("NewHometown");
        customer.setRentDate(LocalDate.now());

        customerDAO.updateCustomer(customer);

        // Kiểm tra xem thông tin đã được cập nhật thành công hay không
        Customer updatedCustomer = customerDAO.getCustomerByID(customer.getId());
        assertEquals("NewName", updatedCustomer.getName());
        assertEquals(Customer.Gender.Female, updatedCustomer.getGender());
        assertEquals(LocalDate.now().minusYears(20), updatedCustomer.getBirthday());
        assertEquals("NewID", updatedCustomer.getIdentification());
        assertEquals("NewHometown", updatedCustomer.getHometown());
    }

    @Test
    public void testDeleteCustomer() {
        Customer customer = createTestCustomer();

        // Xóa khách hàng
        customerDAO.deleteCustomer(customer.getId());

        // Kiểm thử
        Customer deletedCustomer = customerDAO.getCustomerByID(customer.getId());
        assertNull(deletedCustomer, "Customer should be deleted");
    }

    @Test
    public void testGetCustomerByID() {
        Customer customer = createTestCustomer();

        // Kiểm thử
        Customer testCustomer = customerDAO.getCustomerByID(customer.getId());
        assertNotNull(testCustomer, "Customer should be retrieved successfully");
    }

    @Test
    public void testGetAllCustomers() {
        createTestGroupCustomer();

        // Truy vấn
        List<Customer> customers = customerDAO.getAllCustomers();

        // Kiểm thử
        assertEquals(3, customers.size());
    }

    private Customer createTestCustomer() {
        // Khởi tạo khách hàng
        User user = new User("customer", "password", User.Role.Customer);
        userDAO.addUser(user);

        Customer customer = new Customer(user, "TestCustomer", Customer.Gender.Male, LocalDate.now().minusYears(20), "123456", "City");
        customerDAO.addCustomer(customer);

        return customer;
    }

    private void createTestGroupCustomer() {
        // Khởi tạo danh sách khách hàng
        User user1 = new User("customer1", "password", User.Role.Customer);
        User user2 = new User("customer2", "password", User.Role.Customer);
        User user3 = new User("customer3", "password", User.Role.Customer);

        userDAO.addUser(user1);
        userDAO.addUser(user2);
        userDAO.addUser(user3);

        Customer customer1 = new Customer(user1, "Customer1", Customer.Gender.Female, LocalDate.now().minusYears(20), "1234567", "City1");
        Customer customer2 = new Customer(user2, "Customer2", Customer.Gender.Male, LocalDate.now().minusYears(19), "1234568", "City2");
        Customer customer3 = new Customer(user3, "Customer3", Customer.Gender.Female, LocalDate.now().minusYears(22), "1234569", "City3");

        customerDAO.addCustomer(customer1);
        customerDAO.addCustomer(customer2);
        customerDAO.addCustomer(customer3);
    }
}
