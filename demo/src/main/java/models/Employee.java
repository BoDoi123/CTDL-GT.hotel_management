package models;

import lombok.Getter;
import lombok.Setter;

import java.sql.Date;

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

    public enum Gender {
        Male, Female, Other
    }

    public enum Position {
        Manager, Staff
    }

    public Employee(int userID, String name, String hometown, String identification, Date birthday, Gender gender, Position position, int salary) {
        this.id = getNextId();
        this.userID = userID;
        this.name = name;
        this.hometown = hometown;
        this.identification = identification;
        this.birthday = new Date(birthday.getTime());
        this.gender = gender;
        this.position = position;
        this.salary = salary;
    }

    private static int getNextId() {
        return nextId++;
    }

    public Date getBirthday() {
        return new Date(birthday.getTime());
    }

    public void setBirthday(Date birthday) {
        this.birthday = new Date(birthday.getTime());
    }
}
