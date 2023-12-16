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
    private RoomDAO roomDAO;

    @BeforeEach
    public void setUp() {
        // Khởi tạo
        roomDAO = new RoomDAO();
    }

    @AfterEach
    public void tearDown() {
        // Hủy tạo đối tượng
        roomDAO = null;
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
        roomDAO.getServiceDAO().addService(service1);
        roomDAO.getServiceDAO().addService(service2);
        roomDAO.getServiceDAO().addService(service3);

        // Khởi tạo khách hàng
        Customer customer = new Customer("TestCustomer", Customer.Gender.Male, LocalDate.now().minusYears(20), "123456", "City");
        roomDAO.getCustomerDAO().addCustomer(customer);

        // Khách hàng thực hiện thuê phòng
        LocalDate rentDate = LocalDate.now();
        LocalDate departureDate = LocalDate.now().plusDays(2);
        room.rentRoom(customer, rentDate, departureDate);
        room.addService(service1);
        room.addService(service2);
        room.addService(service3);

        roomDAO.rentRoom(room);

        // Lấy thông tin hóa đơn
        Bill bill = roomDAO.getBillDAO().getBillByRoomID(room.getId());

        assertEquals(room.getRenterID(), customer.getId());
        assertTrue(room.isRented());
        assertEquals(room.getRentDate(), customer.getRentDate());
        assertEquals(bill.getPrice(), room.getPrice());
        assertEquals(bill.getId(), room.getBillID());
        assertEquals(bill.getRenterID(), customer.getId());
        assertEquals(bill.getRentDate(), room.getRentDate());
        assertEquals(bill.getDepartureDate(), room.getDepartureDate());


        // Thay đổi dịch vụ phòng
        // Thêm dịch vụ
        Service newService = new Service("Service4", 15);
        roomDAO.getServiceDAO().addService(newService);

        room.addService(newService);

        roomDAO.updateRoomServices(room);

        // Lấy thông tin hóa đơn
        bill = roomDAO.getBillDAO().getBillByRoomID(room.getId());

        assertEquals(room.getServices().size(), roomDAO.getServiceDAO().getAllServices().size());
        assertTrue(room.isRented());
        assertEquals(bill.getPrice(), room.getPrice());
        assertEquals(bill.getId(), room.getBillID());

        // Xóa dịch vụ
        room.removeService(room.getServices().get(0));

        roomDAO.updateRoomServices(room);

        // Lấy thông tin hóa đơn
        bill = roomDAO.getBillDAO().getBillByRoomID(room.getId());

        assertEquals(room.getServices().size(), 3);
        assertTrue(room.isRented());
        assertEquals(bill.getPrice(), room.getPrice());
        assertEquals(bill.getId(), room.getBillID());


        // Cập nhật ngày trả phòng
        departureDate = LocalDate.now().plusDays(3);
        room.updateDepartureDate(departureDate);

        roomDAO.updateDepartureDate(room);

        // Lấy thông tin hóa đơn
        bill = roomDAO.getBillDAO().getBillByRoomID(room.getId());

        assertEquals(room.getDepartureDate(), departureDate);
        assertTrue(room.isRented());
        assertEquals(bill.getPrice(), room.getPrice());
        assertEquals(room.getId(), bill.getRoomID());
        assertEquals(bill.getDepartureDate(), room.getDepartureDate());

        // Khách thực hiện trả phòng khách sạn
        room.checkout();
        customer = room.getCustomer();

        roomDAO.checkOutRoom(room);

        // Kiểm tra thông tin phòng
        assertEquals(1, room.getRenterID());
        assertFalse(room.isRented());
        assertNull(room.getRentDate());
        assertNull(room.getDepartureDate());
        assertEquals(0, room.getPrice());
        assertEquals(bill.getId(), room.getBillID());
        assertNull(customer.getRentDate());
        assertEquals(bill.getRenterID(), customer.getId());
        assertEquals(bill.getDepartureDate(), departureDate);
    }

    @Test
    public void getAllRooms() {
        List<Room> rooms = roomDAO.getAllRooms();

        assertEquals(1, rooms.size());
    }

    @Test
    public void testGetAllBills() {
        List<Bill> bills = roomDAO.getBillDAO().getAllBill();

        assertEquals(1, bills.size());
    }
}
