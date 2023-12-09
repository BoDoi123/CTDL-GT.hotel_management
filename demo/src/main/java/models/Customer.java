package models;

import lombok.Getter;
import lombok.Setter;

import java.sql.Date;

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

    public enum Gender {
        Male, Female, Other
    }

    public Customer(int userID, String name, Gender gender, Date birthday, String identification, String hometown, Date rentDate) {
        this.id = getNextId();
        this.userID = userID;
        this.name = name;
        this.gender = gender;
        this.birthday = new Date(birthday.getTime());
        this.identification = identification;
        this.hometown = hometown;
        this.rentDate = new Date(rentDate.getTime());
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

    public Date getRentDate() {
        return new Date(rentDate.getTime());
    }

    public void setRentDate(Date rentDate) {
        this.rentDate = new Date(rentDate.getTime());
    }
}
