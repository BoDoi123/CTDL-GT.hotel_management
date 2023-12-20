package dao;

import lombok.Getter;
import lombok.Setter;

import models.Customer;
import models.Room;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Date;
import java.sql.Statement;

import java.time.LocalDate;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.List;
import java.util.LinkedList;

@Getter
@Setter
public class CustomerDAO {
    private static final Logger LOGGER = Logger.getLogger(CustomerDAO.class.getName());

    // Thao tac co ban
    public void addCustomer(Customer customer) {
        try (Connection connection = DatabaseConnection.getConnection()) {
            String query = "INSERT INTO customer (name, gender, birthday, identification, hometown) VALUES (?, ?, ?, ?, ?)";

            try (PreparedStatement preparedStatement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
                preparedStatement.setString(1, customer.getName());
                preparedStatement.setString(2, customer.getGender().name());
                preparedStatement.setDate(3, Date.valueOf(customer.getBirthday()));
                preparedStatement.setString(4, customer.getIdentification());
                preparedStatement.setString(5, customer.getHometown());

                preparedStatement.executeUpdate();

                try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        customer.setId(generatedKeys.getInt(1));
                    }
                }
                LOGGER.log(Level.FINE, "Customer added: {0}", customer.getName());
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error adding customer to database", e);
        }
    }

    public void updateCustomer(Customer customer) {
        try (Connection connection = DatabaseConnection.getConnection()) {
            String query = "UPDATE customer SET name = ?, gender = ?, birthday = ?, identification = ?, hometown = ?, rent_date = ? WHERE id = ?";

            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setString(1, customer.getName());
                preparedStatement.setString(2, customer.getGender().name());
                preparedStatement.setDate(3, Date.valueOf(customer.getBirthday()));
                preparedStatement.setString(4, customer.getIdentification());
                preparedStatement.setString(5, customer.getHometown());
                preparedStatement.setDate(6, Date.valueOf(customer.getRentDate()));
                preparedStatement.setInt(7, customer.getId());

                preparedStatement.executeUpdate();
                LOGGER.log(Level.FINE, "Customer updated: {0}", customer.getName());
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error updating customer in database", e);
        }
    }

    public void deleteCustomer(int customerID) {
        try (Connection connection = DatabaseConnection.getConnection()) {
            String query = "DELETE FROM customer WHERE id = ?";

            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setInt(1, customerID);

                preparedStatement.executeUpdate();
                LOGGER.log(Level.FINE, "Customer deleted: {0}", customerID);
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error deleting customer from database", e);
        }
    }

    // Thao tac truy van tim du lieu
    public Customer getCustomerByID(int customerID) {
        try (Connection connection = DatabaseConnection.getConnection()) {
            String query = "SELECT * FROM customer WHERE id = ?";

            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setInt(1, customerID);

                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    if (resultSet.next()) {
                        LOGGER.log(Level.FINE, "Customer retrieved: {0}", mapResultSetToCustomer(resultSet).getName());
                        return mapResultSetToCustomer(resultSet);
                    }
                }
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error getting customer by id from database", e);
        }

        return null;
    }

    public List<Customer> getCustomerByName(String nameCustomer) {
        List<Customer> customers = new LinkedList<>();

        try (Connection connection = DatabaseConnection.getConnection()) {
            String query = "SELECT * FROM customer WHERE name = ?";

            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setString(1, nameCustomer);

                ResultSet resultSet = preparedStatement.executeQuery();

                while (resultSet.next()) {
                    Customer customer = mapResultSetToCustomer(resultSet);
                    customers.add(customer);
                }
                LOGGER.log(Level.FINE, "Got all customers from database", customers.size());
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error getting all customers from database", e);
        }

        return customers;
    }

    public Customer getCustomerByIdentification(String identification) {
        try (Connection connection = DatabaseConnection.getConnection()) {
            String query = "SELECT * FROM customer WHERE identification = ?";

            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setString(1, identification);

                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    if (resultSet.next()) {
                        LOGGER.log(Level.FINE, "Customer retrieved: {0}", mapResultSetToCustomer(resultSet).getName());
                        return mapResultSetToCustomer(resultSet);
                    }
                }
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error getting customer by identification from database", e);
        }

        return null;
    }

    public List<Customer> getAllCustomers() {
        List<Customer> customers = new LinkedList<>();

        try (Connection connection = DatabaseConnection.getConnection()) {
            String query = "SELECT * FROM customer";

            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                ResultSet resultSet = preparedStatement.executeQuery();

                while (resultSet.next()) {
                    Customer customer = mapResultSetToCustomer(resultSet);
                    customers.add(customer);
                }
                LOGGER.log(Level.FINE, "Got all customers from database", customers.size());
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error getting all customers from database", e);
        }

        return customers;
    }

    private Customer mapResultSetToCustomer(ResultSet resultSet) throws SQLException {
        int id = resultSet.getInt("id");
        String name = resultSet.getString("name");
        Customer.Gender gender = Customer.Gender.valueOf(resultSet.getString("gender"));
        LocalDate birthday = resultSet.getDate("birthday").toLocalDate();
        String identification = resultSet.getString("identification");
        String hometown = resultSet.getString("hometown");

        LocalDate rentDate = null;
        if (resultSet.getDate("rent_date") != null) {
            rentDate = resultSet.getDate("rent_date").toLocalDate();
        }

        Customer customer = new Customer(name, gender, birthday, identification, hometown);
        customer.setId(id);
        customer.setRentDate(rentDate);
        return customer;
    }

    public void customerRentRoom(Room room) {
        try (Connection connection = DatabaseConnection.getConnection()) {
            String query = "UPDATE customer SET rent_date = ? WHERE id = ?";

            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setDate(1, Date.valueOf(room.getRentDate()));
                preparedStatement.setInt(2, room.getCustomer().getId());

                preparedStatement.executeUpdate();
                LOGGER.log(Level.FINE, "customer updated: {0}", room.getCustomer().getId());
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error updating customer in database", e);
        }
    }

    public void customerCheckOut(Room room) {
        try (Connection connection = DatabaseConnection.getConnection()) {
            String query = "UPDATE customer SET rent_date = ? WHERE id = ?";

            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setDate(1, null);
                preparedStatement.setInt(2, room.getCustomer().getId());

                preparedStatement.executeUpdate();
                LOGGER.log(Level.FINE, "Customer checked out room: {0}", room.getId());
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error Customer checked out room", e);
        }
    }
}
