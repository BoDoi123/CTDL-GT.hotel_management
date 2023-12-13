package dao_test;

import static org.junit.jupiter.api.Assertions.*;

import dao.ServiceDAO;
import models.Service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;

import java.util.List;

public class ServiceDAOTest {
    private ServiceDAO serviceDAO;

    @BeforeEach
    public void setUp() {
        // Khởi tạo đối tượng
        serviceDAO = new ServiceDAO();
    }

    @AfterEach
    public void tearDown() {
        // Dọn dẹp môi trường kiểm thử
        cleanUpTestData();

        // Hủy tạo đối tượng
        serviceDAO = null;
    }

    private void cleanUpTestData() {
        // Xóa dữ liệu sau mỗi lần kiểm thử
        List<Service> services = serviceDAO.getAllServices();
        for (Service service : services) {
            serviceDAO.deleteService(service.getName());
        }
    }

    @Test
    public void testAddService() {
        Service service = createTestService();

        // Truy vấn
        Service retrievedService = serviceDAO.getServiceByName(service.getName());

        // Kiểm thử
        assertNotNull(retrievedService, "Service should be added successfully");

        assertEquals(retrievedService.getName(), service.getName(), "Service name should match");
        assertEquals(retrievedService.getCost(), service.getCost(), "Service cost should match");
    }

    @Test
    public void testUpdateService() {
        Service service = createTestService();

        // Update
        int newCost = 20;
        service.setCost(newCost);
        serviceDAO.updateService(service);

        // Truy vấn
        Service updatedService = serviceDAO.getServiceByName(service.getName());

        // Kiểm thử
        assertNotNull(updatedService, "Service should be updated successfully");

        assertEquals(newCost, updatedService.getCost(), "Service cost should be updated");
    }

    @Test
    public void testDeleteService() {
        Service service = createTestService();

        // Delete
        serviceDAO.deleteService(service.getName());

        // Kiểm thử
        Service deletedService = serviceDAO.getServiceByName(service.getName());
        assertNull(deletedService, "Service should be deleted");
    }

    @Test
    public void testGetServiceByName() {
        Service service = createTestService();

        // Truy vấn
        Service retrievedService = serviceDAO.getServiceByName(service.getName());

        // Kiểm thử
        assertNotNull(retrievedService, "Service should be retrieved successfully");

        assertEquals(retrievedService.getName(), service.getName());
        assertEquals(retrievedService.getCost(), service.getCost());
    }

    @Test
    public void testGetAllServices() {
        createTestGroupServices();

        // Truy vấn
        List<Service> services = serviceDAO.getAllServices();

        // Kiểm thử
        assertNotNull(services, "Service list should not be null");
        assertEquals(3, services.size(), "Service list should contain 3 services");
    }

    private Service createTestService() {
        // Khởi tạo dịch vụ
        Service service = new Service("TestService", 15);
        serviceDAO.addService(service);

        return service;
    }

    private void createTestGroupServices() {
        // Khởi tạo danh sách dịch vụ
        Service service1 = new Service("Service1", 10);
        Service service2 = new Service("Service2", 20);
        Service service3 = new Service("Service3", 30);
        serviceDAO.addService(service1);
        serviceDAO.addService(service2);
        serviceDAO.addService(service3);
    }
}
