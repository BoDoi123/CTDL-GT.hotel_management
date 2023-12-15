package model_test;

import models.*;
import models.calculateprice.SimplePriceCalculator;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;

public class HotelManagementSystemTest {

    @Test
    public void testUserCreation() {
        // Kiểm tra khởi tạo tài khoản
        User user = new User("Chu_Long", "password123", User.Role.Staff);

        assertEquals("Chu_Long", user.getUsername());
        assertEquals("password123", user.getPassword());
        assertEquals(User.Role.Staff, user.getRole());
    }

    @Test
    public void testEmployeeCreation() {
        // Kiểm tra khởi tạo nhân viên
        User user = new User("Bodoi", "password", User.Role.Staff);
        Employee employee = new Employee(user, "Lam", "HaNoi",
                "12345", LocalDate.of(2002, 2, 1),
                Employee.Gender.Male, 5000);

        assertEquals(user.getId(), employee.getUserID());
        assertEquals("Lam", employee.getName());
        assertEquals("HaNoi", employee.getHometown());
        assertEquals("12345", employee.getIdentification());
        assertEquals(Employee.Gender.Male, employee.getGender());
        assertEquals(user.getRole(), employee.getPosition());
        assertEquals(5000, employee.getSalary());

        assertEquals(LocalDate.of(2002, 2, 1), employee.getBirthday());
    }

    @Test
    public void testCustomerCreation() {
        // Kiểm tra khởi tạo khách hàng
        Customer customer = new Customer("Linh", Customer.Gender.Female,
                LocalDate.of(2002, 2, 1), "67890", "HaNoi");

        assertEquals("Linh", customer.getName());
        assertEquals(Customer.Gender.Female, customer.getGender());
        assertEquals("67890", customer.getIdentification());
        assertEquals("HaNoi", customer.getHometown());

        assertEquals(LocalDate.of(2002, 2, 1), customer.getBirthday());
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
        LocalDate rentDate = LocalDate.of(2023, 12, 11);
        LocalDate departureDate = LocalDate.of(2023, 12, 13);

        // Kiểm tra tính toán giá phòng dự kiến
        SimplePriceCalculator calculator = new SimplePriceCalculator(rentDate, departureDate);

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
        Customer customer = new Customer("Lam", Customer.Gender.Male,
                LocalDate.of(2002, 5, 2), "12345678", "HaNoi");

        // Ngày thuê và ngày trả phòng dự kiến
        LocalDate rentDate = LocalDate.now();
        LocalDate departureDate = LocalDate.now().plusDays(2);

        // Danh sách dịch vụ
        Service service1 = new Service("Wifi", 10);
        Service service2 = new Service("Breakfast", 20);

        // Thực hiện thuê phòng
        room.rentRoom(customer, rentDate, departureDate);
        room.addService(service1);
        room.addService(service2);

        // Kiểm tra khách hàng
        assertEquals(customer.getRoomID(), room.getId());
        assertEquals(customer.getRentDate(), rentDate);

        // Kiểm tra trạng thái phòng
        assertTrue(room.isRented());
        assertEquals(customer.getId(), room.getRenterID());
        assertNotNull(room.getRentDate());
        assertNotNull(room.getDepartureDate());
        assertNotNull(room.getBill());

        // Kiểm tra giá phòng
        int expectedPrice = new SimplePriceCalculator(rentDate, departureDate).calculatePrice(room.getServices());
        assertEquals(expectedPrice, room.getPrice());

        // Kiểm tra thêm và xóa dịch vụ
        // Thêm dịch vụ
        Service service = new Service("Lunch", 30);
        room.addService(service);

        assertEquals(3, room.getServices().size());

        expectedPrice = new SimplePriceCalculator(rentDate, departureDate).calculatePrice(room.getServices());
        assertEquals(expectedPrice, room.getPrice());

        // Xóa dịch vụ
        room.removeService(room.getServices().get(0));

        assertEquals(2, room.getServices().size());

        expectedPrice = new SimplePriceCalculator(rentDate, departureDate).calculatePrice(room.getServices());
        assertEquals(expectedPrice, room.getPrice());

        // Kiểm tra gia hạn ngày thuê
        LocalDate newDepartureDate = LocalDate.of(2023, 12, 18);
        room.updateDepartureDate(newDepartureDate);

        expectedPrice = new SimplePriceCalculator(rentDate, newDepartureDate).calculatePrice(room.getServices());
        assertEquals(expectedPrice, room.getPrice());
        
        // Thực hiện trả phòng
        room.checkout();

        // Kiểm tra khách hàng
        assertNull(customer.getRentDate());
        assertEquals(0, customer.getRoomID());

        // Kiểm tra trạng thái phòng
        assertFalse(room.isRented());
        assertNull(room.getDepartureDate());
        assertNull(room.getRentDate());
        assertNull(room.getBill());
        assertEquals(0, room.getRenterID());
        assertEquals(0, room.getPrice());
    }
}
