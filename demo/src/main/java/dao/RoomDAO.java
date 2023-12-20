package dao;

import lombok.Getter;
import lombok.Setter;

import models.Bill;
import models.Customer;
import models.Room;
import models.Service;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Date;
import java.sql.Statement;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;

@Getter
@Setter
public class RoomDAO {
    private static final Logger LOGGER = Logger.getLogger(RoomDAO.class.getName());
    private CustomerDAO customerDAO = new CustomerDAO();
    private ServiceDAO serviceDAO = new ServiceDAO();
    private BillDAO billDAO = new BillDAO();

    // Room
    public void addRoom(Room room) {
        try (Connection connection = DatabaseConnection.getConnection()) {
            String query = "INSERT INTO room (is_rented, price) VALUES (?, ?)";

            try (PreparedStatement preparedStatement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
                preparedStatement.setBoolean(1, false);
                preparedStatement.setInt(2, room.getPrice());

                preparedStatement.executeUpdate();

                try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        room.setId(generatedKeys.getInt(1));
                        LOGGER.log(Level.FINE, "Room added: {0}", generatedKeys.getInt(1));
                    }
                }
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error adding room to database", e);
        }
    }

    // Thue phong khach san
    public void rentRoom(Room room) {
        try (Connection connection = DatabaseConnection.getConnection()) {
            billDAO.addBill(room.getBill());

            String query = "UPDATE room SET renter_id = ?, is_rented = ?, rent_date = ?, departure_date = ?, price = ? WHERE id = ?";

            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setInt(1, room.getRenterID());
                preparedStatement.setBoolean(2, true);
                preparedStatement.setDate(3, Date.valueOf(room.getRentDate()));
                preparedStatement.setDate(4, Date.valueOf(room.getDepartureDate()));
                preparedStatement.setInt(5, room.getPrice());
                preparedStatement.setInt(6, room.getId());

                preparedStatement.executeUpdate();

                addRoomService(room);
                customerDAO.customerRentRoom(room);
                updateBillID(room);
                LOGGER.log(Level.FINE, "Room rented: {0}", room.getId());
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error renting room", e);
        }
    }

    private void addRoomService(Room room) {
        try (Connection connection = DatabaseConnection.getConnection()) {
            String query = "INSERT INTO room_service (room_id, service_name, process) VALUES (?, ?, ?)";

            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                for (Service service : room.getServices()) {
                    preparedStatement.setInt(1, room.getId());
                    preparedStatement.setString(2, service.getName());
                    preparedStatement.setString(3, "Processing");
                    preparedStatement.addBatch();
                }

                preparedStatement.executeBatch();
                LOGGER.log(Level.FINE, "Room services added: {0}", room.getServices().size());
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error adding room services to database", e);
        }
    }

    public void updateBillID(Room room) {
        try (Connection connection = DatabaseConnection.getConnection()) {
            String query = "UPDATE room SET bill_id = ? WHERE id = ?";

            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setInt(1, room.getBill().getId());
                preparedStatement.setInt(2, room.getId());

                preparedStatement.executeUpdate();
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error updating room in database", e);
        }
    }

    // Cập nhật thông tin thuê phòng
    public void updateDepartureDate(Room room) {
        try (Connection connection = DatabaseConnection.getConnection()) {
            String query = "UPDATE room SET departure_date = ?, price = ? WHERE id = ?";

            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setDate(1, Date.valueOf(room.getDepartureDate()));
                preparedStatement.setInt(2, room.getPrice());
                preparedStatement.setInt(3, room.getId());

                preparedStatement.executeUpdate();

                billDAO.updateBill(room);
                LOGGER.log(Level.FINE, "Room updated: {0}", room.getId());
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error updating room in database", e);
        }
    }

    // Cập nhật dịch vụ
    public void updateRoomServices(Room room) {
        try  {
            // Xóa các dịch vụ cũ
            deleteRoomServices(room);

            // Thêm các dịch vụ mới vào phòng
            addRoomService(room);

            billDAO.updateBill(room);
            LOGGER.log(Level.FINE, "Room services updated: {0}", room.getId());
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error updating room services in database", e);
        }
    }

    private void deleteRoomServices(Room room) {
        try (Connection connection = DatabaseConnection.getConnection()) {
            String query = "DELETE FROM room_service WHERE room_id = ? AND process ='Processing'";

            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setInt(1, room.getId());

                preparedStatement.executeUpdate();
                LOGGER.log(Level.FINE, "Room Services deleted: {0}");
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error deleting room services in database", e);
        }
    }

    // Tra phong khach san
    public void checkOutRoom(Room room) {
        try (Connection connection = DatabaseConnection.getConnection()) {
            printBill(room.getId());

            String query = "UPDATE room SET is_rented = ?, rent_date = ?, departure_date = ?, price = ? WHERE id = ?";

            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setBoolean(1, false);
                preparedStatement.setDate(2, null);
                preparedStatement.setDate(3, null);
                preparedStatement.setInt(4, room.getPrice());
                preparedStatement.setInt(5, room.getId());

                preparedStatement.executeUpdate();

                customerDAO.customerCheckOut(room);
                finishedServices(room);
                billDAO.finishBill(room);
                LOGGER.log(Level.FINE, "Room checked out: {0}", room.getId());
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error checking out room in database", e);
        }
    }

    private void finishedServices(Room room) {
        try (Connection connection = DatabaseConnection.getConnection()) {
            String query = "UPDATE room_service SET process = ? WHERE room_id = ?";

            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setString(1, "Finished");
                preparedStatement.setInt(2, room.getId());

                preparedStatement.executeUpdate();
                LOGGER.log(Level.FINE, "Finished room services: {0}", room.getId());
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error finishing room services in database", e);
        }
    }

    // In ra hóa đơn phòng khi trả phòng
    private void printBill(int roomID) {
        Bill bill = billDAO.getBillByRoomID(roomID);
        List<Service> services = getRoomServices(roomID);
        Room room = getRoomByID(roomID);
        int price = room.getSimplePriceCalculator().getPricePerDay();

        if (bill != null) {
            System.out.println("Bill Information: ");
            System.out.println("Bill ID: " + bill.getId());
            System.out.println("Room ID: " + roomID);
            System.out.println("Customer name: " + room.getCustomer().getName());
            System.out.println("Rent date: " + bill.getRentDate());
            System.out.println("Departure date: " + bill.getDepartureDate());
            System.out.println("Room Services: ");
            for (Service service : services) {
                System.out.println(service.getName() + ": " + service.getCost());
                price += service.getCost();
            }
            System.out.println("Price = " + room.getPriceCalculator().getNumberOfDays() + "(day) * " + price + " = " + bill.getPrice());
        } else {
            System.out.println("Bill not found for Room ID: " + roomID);
        }
    }

    // Truy vấn
    public Room getRoomByID(int roomID) {
        try (Connection connection = DatabaseConnection.getConnection()) {
            String query = "SELECT * FROM room WHERE id = ?";

            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setInt(1, roomID);

                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    if (resultSet.next()) {
                        LOGGER.log(Level.FINE, "Room retrieved: {0}", roomID);
                        return mapResultSetToRoom(resultSet);
                    }
                }
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error getting room by id from database", e);
        }

        return null;
    }

    public List<Room> getRoomByCustomerID(int customerID) {
        List<Room> rooms = new ArrayList<>();

        try (Connection connection = DatabaseConnection.getConnection()) {
            String query = "SELECT * FROM room WHERE renter_id = ? AND is_rented = 1";

            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setInt(1, customerID);

                ResultSet resultSet = preparedStatement.executeQuery();

                while (resultSet.next()) {
                    Room room = mapResultSetToRoom(resultSet);
                    rooms.add(room);
                }
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error getting all room with false", e);
        }

        return rooms;
    }

    public List<Room> getRoomsWithStateFalse() {
        List<Room> rooms = new ArrayList<>();

        try (Connection connection = DatabaseConnection.getConnection()) {
            String query = "SELECT * FROM room WHERE is_rented = 0";

            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                ResultSet resultSet = preparedStatement.executeQuery();

                while (resultSet.next()) {
                    Room room = mapResultSetToRoom(resultSet);
                    rooms.add(room);
                }
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error getting all room with false", e);
        }

        return rooms;
    }

    public List<Room> getRoomsWithStateTrue() {
        List<Room> rooms = new ArrayList<>();

        try (Connection connection = DatabaseConnection.getConnection()) {
            String query = "SELECT * FROM room WHERE is_rented = 1";

            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                ResultSet resultSet = preparedStatement.executeQuery();

                while (resultSet.next()) {
                    Room room = mapResultSetToRoom(resultSet);
                    rooms.add(room);
                }
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error getting all room with false", e);
        }

        return rooms;
    }

    public List<Room> getAllRooms() {
        List<Room> rooms = new LinkedList<>();

        try (Connection connection = DatabaseConnection.getConnection()) {
            String query = "SELECT * FROM room";

            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                ResultSet resultSet = preparedStatement.executeQuery();

                while (resultSet.next()) {
                    Room room = mapResultSetToRoom(resultSet);
                    rooms.add(room);
                }
                LOGGER.log(Level.FINE, "Got all rooms from database: {0}", rooms.size());
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error getting all rooms from database", e);
        }

        return rooms;
    }

    public Room mapResultSetToRoom(ResultSet resultSet) throws SQLException {
        int id = resultSet.getInt("id");
        boolean isRented = resultSet.getBoolean("is_rented");

        // Khi phòng trống
        if (!isRented) {
            Room room = new Room();
            room.setId(id);

            return room;
        }

        // Khi phòng có người thuê
        int renterID = resultSet.getInt("renter_id");
        Customer customer = customerDAO.getCustomerByID(renterID);

        LocalDate rentDate = resultSet.getDate("rent_date").toLocalDate();
        LocalDate departureDate = resultSet.getDate("departure_date").toLocalDate();

        int billID = resultSet.getInt("bill_id");
        List<Service> services = getRoomServices(id);

        Room room = new Room();
        room.setId(id);
        room.rentRoom(customer, rentDate, departureDate);

        for (Service service : services) {
            room.addService(service);
        }

        room.setBillID(billID);
        room.setPrice(services);

        return room;
    }

    private List<Service> getRoomServices(int roomID) {
        List<Service> services = new LinkedList<>();

        try (Connection connection = DatabaseConnection.getConnection()) {
            String query = "SELECT * FROM room_service WHERE room_id = ?";

            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setInt(1, roomID);

                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    while (resultSet.next()) {
                        String serviceName = resultSet.getString("service_name");
                        Service service = serviceDAO.getServiceByName(serviceName);
                        services.add(service);
                    }
                }
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error getting room services from database", e);
        }

        return services;
    }

    public List<Service> getRoomServicesByProcessing(int roomID) {
        List<Service> services = new LinkedList<>();

        try (Connection connection = DatabaseConnection.getConnection()) {
            String query = "SELECT * FROM room_service WHERE room_id = ? AND process = 'Processing'";

            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setInt(1, roomID);

                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    while (resultSet.next()) {
                        String serviceName = resultSet.getString("service_name");
                        Service service = serviceDAO.getServiceByName(serviceName);
                        services.add(service);
                    }
                }
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error getting room services from database", e);
        }

        return services;
    }
}
