package models;

import lombok.Getter;
import lombok.Setter;

import java.sql.Date;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import java.util.logging.Level;
import java.util.logging.Logger;

@Getter
@Setter
public class Customer {
    private static int nextId = 1;
    private int id;
    private int userID;
    private String name;
    private Gender gender;
    private Date birthday;
    private String identification;
    private String hometown;
    private Date rentDate;

    private static final Logger LOGGER = Logger.getLogger(Customer.class.getName());

    public enum Gender {
        Male, Female, Other
    }

    public Customer(User user, String name, Gender gender, String birthdayStr, String identification, String hometown) {
        if (user.getRole().equals(User.Role.Customer)) {
            this.id = getNextId();
            this.userID = user.getId();
            this.name = name;
            this.gender = gender;
            this.identification = identification;
            this.hometown = hometown;

            SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
            try {
                this.birthday = new Date(dateFormat.parse(birthdayStr).getTime());
            } catch (ParseException e) {
                LOGGER.log(Level.SEVERE, "Error parsing date", e);
            }
        } else {
            LOGGER.log(Level.SEVERE, "Error to create Customer from User account");
        }
    }

    private static int getNextId() {
        return nextId++;
    }

    public Date getBirthday() {
        return new Date(birthday.getTime());
    }

    public void setBirthday(String birthdayStr) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        try {
            this.birthday = new Date(dateFormat.parse(birthdayStr).getTime());
        } catch (ParseException e) {
            LOGGER.log(Level.SEVERE, "Error parsing date", e);
        }
    }

    public Date getRentDate() {
        return new Date(rentDate.getTime());
    }

    public void setRentDate(Date rentDate) {
        this.rentDate = new Date(rentDate.getTime());
    }
}
