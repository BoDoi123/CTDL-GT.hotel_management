package models;

import java.sql.Date;

public class Customer {
    private String id;
    private String name;
    private String gender;
    private Date birthday;
    private String identification;
    private String hometown;
    private Date renDate;

    public enum Gender {
        Male, Female, Other
    }

    public Customer(String id, String name, String gender, Date birthday, String identification, String hometown, Date renDate) {
        this.id = id;
        this.name = name;
        this.gender = gender;
        this.birthday = birthday;
        this.identification = identification;
        this.hometown = hometown;
        this.renDate = renDate;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getGender() {
        return gender;
    }

    public Date getBirthday() {
        return birthday;
    }

    public String getIdentification() {
        return identification;
    }

    public String getHometown() {
        return hometown;
    }

    public Date getRenDate() {
        return renDate;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public void setIdentification(String identification) {
        this.identification = identification;
    }

    public void setHometown(String hometown) {
        this.hometown = hometown;
    }

    public void setRenDate(Date renDate) {
        this.renDate = renDate;
    }
}
