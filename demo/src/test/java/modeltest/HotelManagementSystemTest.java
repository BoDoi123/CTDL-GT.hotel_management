package modeltest;

import models.*;
import models.calculateprice.SimplePriceCalculator;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.text.SimpleDateFormat;
import java.util.LinkedList;
import java.util.List;

public class HotelManagementSystemTest {

    @Test
    public void testUserCreation() {
        // Kiểm tra khởi tạo tài khoản
        User user = new User("Chu_Long", "password123", User.Role.Customer);

        assertEquals("Chu_Long", user.getUsername());
        assertEquals("password123", user.getPassword());
        assertEquals(User.Role.Customer, user.getRole());
    }

    @Test
    public void testEmployeeCreation() {
        // Kiểm tra khởi tạo nhân viên
        Employee employee = new Employee(1, "Lam", "HaNoi",
                "12345", "01/02/2002",
                Employee.Gender.Male, Employee.Position.Manager, 5000);

        assertEquals(1, employee.getUserID());
        assertEquals("Lam", employee.getName());
        assertEquals("HaNoi", employee.getHometown());
        assertEquals("12345", employee.getIdentification());
        assertEquals(Employee.Gender.Male, employee.getGender());
        assertEquals(Employee.Position.Manager, employee.getPosition());
        assertEquals(5000, employee.getSalary());

        assertEquals("01/02/2002", new SimpleDateFormat("dd/MM/yyyy").format(employee.getBirthday()));
    }

    @Test
    public void testCustomerCreation() {
        // Kiểm tra khởi tạo khách hàng
        Customer customer = new Customer(2, "Linh", Customer.Gender.Female,
                "01/02/2002", "67890", "HaNoi");

        assertEquals(2, customer.getUserID());
        assertEquals("Linh", customer.getName());
        assertEquals(Customer.Gender.Female, customer.getGender());
        assertEquals("67890", customer.getIdentification());
        assertEquals("HaNoi", customer.getHometown());

        assertEquals("01/02/2002", new SimpleDateFormat("dd/MM/yyyy").format(customer.getBirthday()));
    }

    @Test
    public void testServiceCreation() {
        // Kiểm tra khởi tạo dịch vụ
        Service service = new Service("Wifi", 10);

        assertEquals("Wifi", service.getName());
        assertEquals(10, service.getCost());
    }

    @Test
    public void testSimplePriceCalculator() {
        // Ngày thuê và ngày trả phòng dự kiến
        String rentDateStr = "10/12/2023";
        String departureDateStr = "12/12/2023";

        // Kiểm tra tính toán giá phòng dự kiến
        SimplePriceCalculator calculator = new SimplePriceCalculator(rentDateStr, departureDateStr);

        List<Service> services = new LinkedList<>();
        services.add(new Service("Wifi", 10));
        services.add(new Service("Breakfast", 15));

        int totalPrice = calculator.calculatePrice(services);

        assertEquals(50, totalPrice);
    }

    @Test
    public void testRoomRenting() {
        // Tạo phòng
        Room room = new Room();

        // Tao 1 khách hàng
        Customer customer = new Customer(3, "Lam", Customer.Gender.Male, "02/05/2002", "12345678", "HaNoi");

        // Ngày thuê và ngày trả phòng dự kiến
        String rentDateStr = "10/12/2023";
        String departureDateStr = "14/12/2023";

        // Danh sách dịch vụ
        List<Service> services = new LinkedList<>();
        services.add(new Service("Wifi", 10));
        services.add(new Service("Breakfast", 20));

        // Thực hiện thuê phòng
        room.rentRoom(room, customer, rentDateStr, departureDateStr, services);

        // Kiểm tra trạng thái phòng
        assertTrue(room.isRented());
        assertEquals(customer.getId(), room.getRenterID());
        assertNotNull(room.getRentDate());
        assertNotNull(room.getDepartureDate());
        assertNotNull(room.getBill());

        // Kiểm tra giá phòng
        int expectedPrice = new SimplePriceCalculator(rentDateStr, departureDateStr).calculatePrice(services);
        assertEquals(expectedPrice, room.getPrice());
    }
}
