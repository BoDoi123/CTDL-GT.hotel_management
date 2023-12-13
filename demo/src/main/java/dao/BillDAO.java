package dao;

import lombok.Setter;
import lombok.Getter;

import models.Bill;
import models.Room;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

import java.util.List;
import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;

@Getter
@Setter
public class BillDAO {
    private static final Logger LOGGER = Logger.getLogger(BillDAO.class.getName());
    private RoomDAO roomDAO = new RoomDAO();

    // Thao tac co ban
    public void addBill(Room room) {
        try (Connection connection = DatabaseConnection.getConnection()) {
            String query = "INSERT INTO bill (room_id, price) VALUES (?, ?)";

            try (PreparedStatement preparedStatement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
                preparedStatement.setInt(1, room.getId());
                preparedStatement.setInt(2, room.getPrice());

                preparedStatement.executeUpdate();

                try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        Bill bill = new Bill(room);
                        bill.setId(generatedKeys.getInt(1));
                        LOGGER.log(Level.FINE, "Bill added: {0}", room.getBillID());
                    }
                }
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error adding bill to database", e);
        }
    }

    public void  updateBill(Room room) {
        try (Connection connection = DatabaseConnection.getConnection()) {
            String query = "UPDATE bill SET price = ? WHERE room_id = ?";

            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setInt(1,room.getPrice());
                preparedStatement.setInt(2,room.getId());

                preparedStatement.executeUpdate();
                LOGGER.log(Level.FINE, "Bill updated: {0}", room.getBillID());
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error updating bill to database", e);
        }
    }

    public void deleteBill(int roomID) {
        try (Connection connection = DatabaseConnection.getConnection()) {
            String query = "DELETE FROM bill WHERE room_id = ?";

            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setInt(1, roomID);

                preparedStatement.executeUpdate();
                LOGGER.log(Level.FINE, "Bill deleted: {0}", roomID);
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error deleting bill from datatbase", e);
        }
    }

    // Truy van va tim kiem du lieu
    public Bill getBillByRoomID(int roomID) {
        try (Connection connection = DatabaseConnection.getConnection()) {
            String query = "SELECT * FROM bill WHERE room_id = ?";

            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setInt(1, roomID);

                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    if (resultSet.next()) {
                        LOGGER.log(Level.FINE, "Bill retrieved: {0}", roomID);
                        return mapResultSetToBill(resultSet);
                    }
                }
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error getting bill from database", e);
        }

        return null;
    }

    public List<Bill> getAllBill() {
        List<Bill> bills = new LinkedList<>();

        try (Connection connection = DatabaseConnection.getConnection()) {
            String query = "SELECT * FROM bill";

            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                ResultSet resultSet = preparedStatement.executeQuery();

                while (resultSet.next()) {
                    bills.add(mapResultSetToBill(resultSet));
                }
                LOGGER.log(Level.FINE, "Got all bills: {0}", bills.size());
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error getting all bills from database", e);
        }

        return bills;
    }

    public Bill mapResultSetToBill(ResultSet resultSet) throws SQLException {
        int id = resultSet.getInt("id");
        int roomID = resultSet.getInt("room_id");

        Room room = roomDAO.getRoomByID(roomID);

        Bill bill = new Bill(room);
        bill.setId(id);
        return bill;
    }
}
