package models;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.logging.Level;
import java.util.logging.Logger;

@Getter
@Setter
public class Employee {
    private static AtomicInteger nextID = new AtomicInteger(1);
    private User user;
    private int id;
    private int userID;
    private String name;
    private String hometown;
    private String identification;
    private LocalDate birthday;
    private Gender gender;
    private User.Role position;
    private int salary;

    private static final Logger LOGGER = Logger.getLogger(Employee.class.getName());

    public enum Gender {
        Male, Female
    }

    public Employee() {

    }

    public Employee(User user, String name, String hometown, String identification, LocalDate birthday, Gender gender, int salary) {
        if (user.getRole().equals(User.Role.Staff)) {
            this.position = User.Role.Staff;
        } else if (user.getRole().equals(User.Role.Manager)) {
            this.position = User.Role.Manager;
        } else {
            LOGGER.log(Level.SEVERE, "Error to create Employee from User account");
            throw new IllegalArgumentException("Role don't match with position");
        }
        this.user = user;
        this.id = nextID.getAndIncrement();
        this.userID = user.getId();
        this.name = name;
        this.hometown = hometown;
        this.identification = identification;
        this.gender = gender;
        this.salary = salary;
        this.birthday = birthday;
    }

    public void setRole(User.Role role) {
        this.position = role;
        user.setRole(role);
    }

    public void setUser(User user) {
        this.user = user;
        setUserID(user.getId());
    }
}
