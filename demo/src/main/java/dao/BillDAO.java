package dao;

import lombok.Setter;
import lombok.Getter;

import models.Bill;
import models.Room;

import java.sql.*;

import java.time.LocalDate;
import java.util.List;
import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;

@Getter
@Setter
public class BillDAO {
    private static final Logger LOGGER = Logger.getLogger(BillDAO.class.getName());

    // Thao tac co ban
    public void addBill(Bill bill) {
        try (Connection connection = DatabaseConnection.getConnection()) {
            String query = "INSERT INTO bill (room_id, renter_id, rent_date, departure_date, price, process) VALUES (?, ?, ?, ?, ?, ?)";

            try (PreparedStatement preparedStatement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
                preparedStatement.setInt(1, bill.getRoomID());
                preparedStatement.setInt(2, bill.getRenterID());
                preparedStatement.setDate(3, Date.valueOf(bill.getRentDate()));
                preparedStatement.setDate(4, Date.valueOf(bill.getDepartureDate()));
                preparedStatement.setInt(5, bill.getPrice());
                preparedStatement.setString(6, "Processing");

                preparedStatement.executeUpdate();

                try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        bill.setId(generatedKeys.getInt(1));
                        LOGGER.log(Level.FINE, "Bill added: {0}", bill.getId());
                    }
                }
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error adding bill to database", e);
        }
    }

    public void updateBill(Room room) {
        try (Connection connection = DatabaseConnection.getConnection()) {
            String query = "UPDATE bill SET departure_date = ?, price = ? WHERE room_id = ?";

            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setDate(1, Date.valueOf(room.getDepartureDate()));
                preparedStatement.setInt(2, room.getPrice());
                preparedStatement.setInt(3, room.getId());

                preparedStatement.executeUpdate();
                LOGGER.log(Level.FINE, "Bill updated: {0}", room.getBillID());
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error updating bill to database", e);
        }
    }

    public void finishBill(Room room) {
        try (Connection connection = DatabaseConnection.getConnection()) {
            String query = "UPDATE bill SET process = ? WHERE room_id = ? AND process = 'Processing'";

            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setString(1, "Finished");
                preparedStatement.setInt(2, room.getId());

                preparedStatement.executeUpdate();
                LOGGER.log(Level.FINE, "Bill updated: {0}", room.getBillID());
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error updating bill to database", e);
        }
    }

    public void deleteBill(int billID) {
        try (Connection connection = DatabaseConnection.getConnection()) {
            String query = "DELETE FROM bill WHERE id = ?";

            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setInt(1, billID);

                preparedStatement.executeUpdate();
                LOGGER.log(Level.FINE, "Bill deleted: {0}", billID);
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error deleting bill from database", e);
        }
    }

    public List<Bill> getBillsByCustomerID(int customerID) {
        List<Bill> bills = new LinkedList<>();

        try (Connection connection = DatabaseConnection.getConnection()) {
            String query = "SELECT * FROM bill WHERE renter_id = ? AND process = 'Finished'";

            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setInt(1, customerID);

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

    public List<Bill> getAllFinishedBills() {
        List<Bill> bills = new LinkedList<>();

        try (Connection connection = DatabaseConnection.getConnection()) {
            String query = "SELECT * FROM bill WHERE process = 'Finished'";

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

    public Bill getBillById(int billID) {
        try (Connection connection = DatabaseConnection.getConnection()) {
            String query = "SELECT * FROM bill WHERE id = ?";

            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setInt(1, billID);

                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    if (resultSet.next()) {
                        LOGGER.log(Level.FINE, "Bill retrieved: {0}", billID);
                        return mapResultSetToBill(resultSet);
                    }
                }
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error getting bill from database", e);
        }

        return null;
    }

    public Bill mapResultSetToBill(ResultSet resultSet) throws SQLException {
        int id = resultSet.getInt("id");
        int roomID = resultSet.getInt("room_id");
        int renterID = resultSet.getInt("renter_id");
        LocalDate rentDate = resultSet.getDate("rent_date").toLocalDate();
        LocalDate departureDate = resultSet.getDate("departure_date").toLocalDate();
        int price = resultSet.getInt("price");

        Bill bill = new Bill();
        bill.setId(id);
        bill.setRoomID(roomID);
        bill.setRenterID(renterID);
        bill.setRentDate(rentDate);
        bill.setDepartureDate(departureDate);
        bill.setPrice(price);
        return bill;
    }
}
