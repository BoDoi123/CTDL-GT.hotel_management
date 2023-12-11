package dao;

import models.Employee;
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

public class EmployeeDAO {
    private static final Logger LOGGER = Logger.getLogger(EmployeeDAO.class.getName());

    // Thao tac co ban
    public void addEmployee(Employee employee) {
        try (Connection connection = DatabaseConnection.getConnection()) {
            String query = "INSERT INTO employee (user_id, name, hometown, identification, birthday, gender, position, salary) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

            try (PreparedStatement preparedStatement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
                preparedStatement.setInt(1, employee.getUser().getId());
                preparedStatement.setString(2, employee.getName());
                preparedStatement.setString(3, employee.getHometown());
                preparedStatement.setString(4, employee.getIdentification());
                preparedStatement.setDate(5, Date.valueOf(employee.getBirthday()));
                preparedStatement.setString(6, employee.getGender().name());
                preparedStatement.setString(7, employee.getPosition().name());
                preparedStatement.setInt(8, employee.getSalary());

                preparedStatement.executeUpdate();

                try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys()){
                    if (generatedKeys.next()) {
                        employee.setId(generatedKeys.getInt(1));
                    }
                }
                LOGGER.log(Level.FINE, "Employee added: {0}" + employee.getName());
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error adding employee to database", e);
        }
    }

    public void updateEmployee(Employee employee) {
        try (Connection connection = DatabaseConnection.getConnection()) {
            String query = "UPDATE employee SET name = ?, hometown = ?, identification = ?, birthday = ?, gender = ?, position = ?, salary = ? WHERE id = ?";

            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setString(1, employee.getName());
                preparedStatement.setString(2, employee.getHometown());
                preparedStatement.setString(3, employee.getIdentification());
                preparedStatement.setDate(4, Date.valueOf(employee.getBirthday()));
                preparedStatement.setString(5, employee.getGender().name());
                preparedStatement.setString(6, employee.getPosition().name());
                preparedStatement.setInt(7, employee.getSalary());
                preparedStatement.setInt(8, employee.getId());

                preparedStatement.executeUpdate();
                LOGGER.log(Level.FINE, "Employee updated: {0}" + employee.getName());
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error updating employee in database", e);
        }
    }

    public void deleteEmployee(int employeeID) {
        try (Connection connection = DatabaseConnection.getConnection()) {
            String query = "DELETE FROM employee WHERE id = ?";

            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setInt(1, employeeID);

                preparedStatement.executeUpdate();
                LOGGER.log(Level.FINE, "Employee deleted: {0}", employeeID);
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error deleting employee from database", e);
        }
    }

    // Thao tac truy van tim du lieu
    public Employee getEmployeeByID(int employeeID) {
        try (Connection connection = DatabaseConnection.getConnection()) {
            String query = "SELECT * FROM employee WHERE id = ?";

            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setInt(1, employeeID);

                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    if (resultSet.next()) {
                        Employee employee = mapResultSetToEmployee(resultSet);
                        LOGGER.log(Level.FINE, "Employee retrieved: {0}" + employee.getName());
                        return employee;
                    }
                }
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error getting employee from database", e);
        }

        return null;
    }

    public List<Employee> getAllEmployeeRoleStaff() {
        List<Employee> employees = new LinkedList<>();

        try (Connection connection = DatabaseConnection.getConnection()) {
            String query = "SELECT * FROM employee WHERE position = 'Staff'";

            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                ResultSet resultSet = preparedStatement.executeQuery();

                while (resultSet.next()) {
                    Employee employee = mapResultSetToEmployee(resultSet);
                    employees.add(employee);
                }
                LOGGER.log(Level.FINE, "Got all employee with role 'Staff' from database", employees.size());
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error getting employees with role 'Staff' from database", e);
        }

        return employees;
    }

    public List<Employee> getAllEmployee() {
        List<Employee> employees = new LinkedList<>();

        try (Connection connection = DatabaseConnection.getConnection()) {
            String query = "SELECT * FROM employee";

            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                ResultSet resultSet = preparedStatement.executeQuery();

                while (resultSet.next()) {
                    Employee employee = mapResultSetToEmployee(resultSet);
                    employees.add(employee);
                }
                LOGGER.log(Level.FINE, "Got all employee from database", employees.size());
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error getting employees from database", e);
        }

        return employees;
    }

    private Employee mapResultSetToEmployee(ResultSet resultSet) throws SQLException {
        int id = resultSet.getInt("id");
        int userID = resultSet.getInt("user_id");
        String name = resultSet.getString("name");
        String hometown = resultSet.getString("hometown");
        String identification = resultSet.getString("identification");
        LocalDate birthday = resultSet.getDate("birthday").toLocalDate();
        Employee.Gender gender = Employee.Gender.valueOf(resultSet.getString("gender"));
        User.Role position = User.Role.valueOf(resultSet.getString("position"));
        int salary = resultSet.getInt("salary");

        User user = new User("", "", position);
        user.setId(userID);

        Employee employee = new Employee(user, name, hometown, identification, birthday, gender, salary);
        employee.setId(id);
        return employee;
    }
}
