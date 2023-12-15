package dao_test;

import static org.junit.jupiter.api.Assertions.*;

import dao.*;
import models.*;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;

import java.time.LocalDate;
import java.util.List;
public class RentRoomAndCheckOutTest {
    private ServiceDAO serviceDAO;
    private RoomDAO roomDAO;
    private CustomerDAO customerDAO;

    @BeforeEach
    public void setUp() {
        // Khởi tạo
        roomDAO = new RoomDAO();
        serviceDAO = new ServiceDAO();
        customerDAO = new CustomerDAO();
    }

    @AfterEach
    public void tearDown() {
        // Hủy tạo đối tượng
        roomDAO = null;
        serviceDAO = null;
        customerDAO = null;
    }

    @Test
    public void testAll() {
        // Khởi tạo phòng cho thuê
        Room room = new Room();
        roomDAO.addRoom(room);

        // Khởi tạo danh sách dịch vụ
        Service service1 = new Service("Service1", 10);
        Service service2 = new Service("Service2", 20);
        Service service3 = new Service("Service3", 30);
        serviceDAO.addService(service1);
        serviceDAO.addService(service2);
        serviceDAO.addService(service3);

        // Khởi tạo khách hàng
        Customer customer = new Customer("TestCustomer", Customer.Gender.Male, LocalDate.now().minusYears(20), "123456", "City");
        customerDAO.addCustomer(customer);

        // Khách hàng thực hiện thuê phòng
        LocalDate rentDate = LocalDate.now();
        LocalDate departureDate = LocalDate.of(2023, 12, 16);
        room.rentRoom(customer, rentDate, departureDate, serviceDAO.getAllServices());
        roomDAO.rentRoom(room);

        // Lấy thông tin hóa đơn
        Bill bill = roomDAO.getBillByRoomID(room.getId());

        assertEquals(room.getRenterID(), customer.getId());
        assertEquals(room.getId(), customer.getRoomID());
        assertTrue(room.isRented());
        assertEquals(room.getRentDate(), customer.getRentDate());
        assertEquals(bill.getPrice(), room.getPrice());
        assertEquals(bill.getId(), room.getBillID());

        // Thay đổi dịch vụ phòng

    }
}
