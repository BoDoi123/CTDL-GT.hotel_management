package dao;

import models.Service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.List;
import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ServiceDAO {
    private static final Logger LOGGER = Logger.getLogger(ServiceDAO.class.getName());

    // Thao tac co ban
    public void addService(Service service) {
        try (Connection connection = DatabaseConnection.getConnection()) {
            String query = "INSERT INTO service (name, cost) VALUES (?, ?)";

            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setString(1, service.getName());
                preparedStatement.setInt(2, service.getCost());

                preparedStatement.executeUpdate();
                LOGGER.log(Level.FINE, "Service added: {0}", service.getName());
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error adding service to database", e);
        }
    }

    public void updateService(Service service) {
        try (Connection connection = DatabaseConnection.getConnection()) {
            String query = "UPDATE service SET cost = ? WHERE name = ?";

            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setInt(1, service.getCost());
                preparedStatement.setString(2, service.getName());

                preparedStatement.executeUpdate();
                LOGGER.log(Level.FINE, "Service updated: {0}", service.getName());
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error updating service in database", e);
        }
    }

    public void deleteService(String serviceName) {
        try (Connection connection = DatabaseConnection.getConnection()) {
            String query = "DELETE FROM service WHERE name = ?";

            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setString(1, serviceName);

                preparedStatement.executeUpdate();
                LOGGER.log(Level.FINE, "Service deleted: {0}", serviceName);
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error deleting service from database", e);
        }
    }

    // Truy van va tim kiem du lieu
    public Service getServiceByName(String serviceName) {
        try (Connection connection = DatabaseConnection.getConnection()) {
            String query = "SELECT * FROM service WHERE name = ?";

            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setString(1, serviceName);

                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    if (resultSet.next()) {
                        LOGGER.log(Level.FINE, "Service retrieved: {0}", serviceName);
                        return mapResultSetToService(resultSet);
                    }
                }
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error getting service from database", e);
        }

        return null;
    }

    public List<Service> getAllServices() {
        List<Service> services = new LinkedList<>();

        try (Connection connection = DatabaseConnection.getConnection()) {
            String query = "SELECT * FROM service";

            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                ResultSet resultSet = preparedStatement.executeQuery();

                while (resultSet.next()) {
                    Service service = mapResultSetToService(resultSet);
                    services.add(service);
                }
                LOGGER.log(Level.FINE, "Got all service from database", services.size());
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error getting all services from database", e);
        }

        return services;
    }

    public Service mapResultSetToService(ResultSet resultSet) throws SQLException {
        String name = resultSet.getString("name");
        int cost = resultSet.getInt("cost");

        return new Service(name, cost);
    }
}
