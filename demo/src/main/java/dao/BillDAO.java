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
    public void deleteBill(int roomID) {
        try (Connection connection = DatabaseConnection.getConnection()) {
            String query = "DELETE FROM bill WHERE room_id = ?";

            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setInt(1, roomID);

                preparedStatement.executeUpdate();
                LOGGER.log(Level.FINE, "Bill deleted: {0}", roomID);
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error deleting bill from database", e);
        }
    }

    public List<Bill> getAllBill() {
        List<Bill> bills = new LinkedList<>();

        try (Connection connection = DatabaseConnection.getConnection()) {
            String query = "SELECT * FROM bill";

            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                ResultSet resultSet = preparedStatement.executeQuery();

                while (resultSet.next()) {
                    bills.add(roomDAO.mapResultSetToBill(resultSet));
                }
                LOGGER.log(Level.FINE, "Got all bills: {0}", bills.size());
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error getting all bills from database", e);
        }

        return bills;
    }
}
