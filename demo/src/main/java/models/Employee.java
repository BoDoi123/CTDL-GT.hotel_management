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
public class Employee {
    private static int nextId = 1;
    private int id;
    private int userID;
    private String name;
    private String hometown;
    private String identification;
    private Date birthday;
    private Gender gender;
    private Position position;
    private int salary;

    private static final Logger LOGGER = Logger.getLogger(Employee.class.getName());

    public enum Gender {
        Male, Female, Other
    }

    public enum Position {
        Manager, Staff
    }

    public Employee(int userID, String name, String hometown, String identification, String birthdayStr, Gender gender, Position position, int salary) {
        this.id = getNextId();
        this.userID = userID;
        this.name = name;
        this.hometown = hometown;
        this.identification = identification;
        this.gender = gender;
        this.position = position;
        this.salary = salary;

        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        try {
            this.birthday = new Date(dateFormat.parse(birthdayStr).getTime());
        } catch (ParseException e) {
            LOGGER.log(Level.SEVERE, "Error parsing date", e);
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
}
