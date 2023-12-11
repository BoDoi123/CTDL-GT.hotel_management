package models;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.logging.Level;
import java.util.logging.Logger;

@Getter
@Setter
public class Customer {
    private static AtomicInteger nextID = new AtomicInteger(1);
    private User user;
    private int id;
    private int userID;
    private String name;
    private Gender gender;
    private LocalDate birthday;
    private String identification;
    private String hometown;
    private LocalDate rentDate;

    private static final Logger LOGGER = Logger.getLogger(Customer.class.getName());

    public enum Gender {
        Male, Female
    }

    public Customer() {

    }

    public Customer(User user, String name, Gender gender, LocalDate birthday, String identification, String hometown) {
        if (user.getRole().equals(User.Role.Customer)) {
            this.user = user;
            this.id = nextID.getAndIncrement();
            this.userID = user.getId();
            this.name = name;
            this.gender = gender;
            this.birthday = birthday;
            this.identification = identification;
            this.hometown = hometown;
        } else {
            LOGGER.log(Level.SEVERE, "Error to create Customer from User account");
            throw new IllegalArgumentException("Role don't match with position");
        }
    }
}