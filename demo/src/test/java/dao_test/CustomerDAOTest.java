package dao_test;

import static org.junit.jupiter.api.Assertions.*;

import dao.*;
import models.*;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;

import java.time.LocalDate;
import java.util.List;

public class CustomerDAOTest {
    private CustomerDAO customerDAO;

    @BeforeEach
    public void setUp() {
        // Khởi tạo
        customerDAO = new CustomerDAO();
    }

    @AfterEach
    public void tearDown() {
        // Dọn dẹp môi trường kiểm thử
        cleanUpTestData();

        // Hủy tạo đối tượng
        customerDAO = null;
    }

    private void cleanUpTestData() {
        // Xóa dữ liệu sau mỗi lần kiểm thử
        List<Customer> customers = customerDAO.getAllCustomers();
        for (Customer customer : customers) {
            customerDAO.deleteCustomer(customer.getId());
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
        customer.setRentDate(LocalDate.of(23, 12, 13));
        customerDAO.updateCustomer(customer);

        // Kiểm thử
        Customer testCustomer = customerDAO.getCustomerByID(customer.getId());
        assertNotNull(testCustomer, "Customer should be retrieved successfully");

        assertNotNull(testCustomer.getRentDate());
        assertEquals(testCustomer.getRoomID(), 0);
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
        Customer customer = new Customer("TestCustomer", Customer.Gender.Male, LocalDate.now().minusYears(20), "123456", "City");
        customerDAO.addCustomer(customer);

        return customer;
    }

    private void createTestGroupCustomer() {
        // Khởi tạo danh sách khách hàng
        Customer customer1 = new Customer("Customer1", Customer.Gender.Female, LocalDate.now().minusYears(20), "1234567", "City1");
        Customer customer2 = new Customer("Customer2", Customer.Gender.Male, LocalDate.now().minusYears(19), "1234568", "City2");
        Customer customer3 = new Customer("Customer3", Customer.Gender.Female, LocalDate.now().minusYears(22), "1234569", "City3");

        customerDAO.addCustomer(customer1);
        customerDAO.addCustomer(customer2);
        customerDAO.addCustomer(customer3);
    }
}
