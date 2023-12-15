package dao_test;

import static org.junit.jupiter.api.Assertions.*;

import dao.*;
import models.*;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;

import java.time.LocalDate;
import java.util.LinkedList;
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
    public void testRoom() {
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
        LocalDate departureDate = LocalDate.now().plusDays(2);
        room.rentRoom(customer, rentDate, departureDate);
        room.addService(service1);
        room.addService(service2);
        room.addService(service3);

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
        // Thêm dịch vụ
        Service newService = new Service("Service4", 15);
        serviceDAO.addService(newService);

        room.addService(newService);

        roomDAO.updateRoomServices(room);

        // Lấy thông tin hóa đơn
        bill = roomDAO.getBillByRoomID(room.getId());

        assertEquals(room.getServices().size(), serviceDAO.getAllServices().size());
        assertTrue(room.isRented());
        assertEquals(bill.getPrice(), room.getPrice());
        assertEquals(bill.getId(), room.getBillID());

        // Xóa dịch vụ
        room.removeService(room.getServices().get(0));

        roomDAO.updateRoomServices(room);

        // Lấy thông tin hóa đơn
        bill = roomDAO.getBillByRoomID(room.getId());

        assertEquals(room.getServices().size(), 3);
        assertTrue(room.isRented());
        assertEquals(bill.getPrice(), room.getPrice());
        assertEquals(bill.getId(), room.getBillID());


        // Cập nhật ngày trả phòng
        departureDate = LocalDate.now().plusDays(3);
        room.updateDepartureDate(departureDate);

        roomDAO.updateDepartureDate(room);

        // Lấy thông tin hóa đơn
        bill = roomDAO.getBillByRoomID(room.getId());

        assertEquals(room.getDepartureDate(), departureDate);
        assertTrue(room.isRented());
        assertEquals(bill.getPrice(), room.getPrice());
        assertEquals(room.getId(), bill.getRoomID());


        // Khách thực hiện trả phòng khách sạn
        room.checkout();
        customer = room.getCustomer();

        roomDAO.checkOutRoom(room);

        // Kiểm tra thông tin phòng
        assertEquals(0, room.getRenterID());
        assertFalse(room.isRented());
        assertNull(room.getRentDate());
        assertNull(room.getDepartureDate());
        assertEquals(0, room.getPrice());
        assertEquals(0, room.getBillID());
        assertEquals(0, customer.getRoomID());
        assertNull(customer.getRentDate());
    }

    @Test
    public void getAllRooms() {
        List<Room> rooms = roomDAO.getAllRooms();

        assertEquals(1, rooms.size());
    }
}
