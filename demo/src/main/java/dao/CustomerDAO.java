package dao;

import models.Customer;
import models.User;

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


public class CustomerDAO {
    private static final Logger LOGGER = Logger.getLogger(CustomerDAO.class.getName());

    // Thao tac co ban
    public void addCustomer(Customer customer) {
        try (Connection connection = DatabaseConnection.getConnection()) {
            String query = "INSERT INTO customer (user_id, name, gender, birthday, identification, hometown) VALUES (?, ?, ?, ?, ?, ?)";

            try (PreparedStatement preparedStatement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
                preparedStatement.setInt(1, customer.getUser().getId());
                preparedStatement.setString(2, customer.getName());
                preparedStatement.setString(3, customer.getGender().name());
                preparedStatement.setDate(4, Date.valueOf(customer.getBirthday()));
                preparedStatement.setString(5, customer.getIdentification());
                preparedStatement.setString(6, customer.getHometown());

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

    public void updateRentDate(int customerID, LocalDate rentDate) {
        try (Connection connection = DatabaseConnection.getConnection()) {
            String query = "UPDATE customer SET rent_date = ? WHERE id = ?";

            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setDate(1, Date.valueOf(rentDate));
                preparedStatement.setInt(2, customerID);

                preparedStatement.executeUpdate();
                LOGGER.log(Level.FINE, "Rent date updated for customer with ID: {0}", customerID);
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error updating rent date for customer in database", e);
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
        int userID = resultSet.getInt("user_id");
        String name = resultSet.getString("name");
        Customer.Gender gender = Customer.Gender.valueOf(resultSet.getString("gender"));
        LocalDate birthday = resultSet.getDate("birthday").toLocalDate();
        String identification = resultSet.getString("identification");
        String hometown = resultSet.getString("hometown");

        User user = new User("", "", User.Role.Customer);
        user.setId(userID);

        Customer customer = new Customer(user, name, gender, birthday, identification, hometown);
        customer.setId(id);
        return customer;
    }
}
