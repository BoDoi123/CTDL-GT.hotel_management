package models;

import java.sql.Date;

public class Employee {
    private String id;
    private String name;
    private String hometown;
    private String identification;
    private Date birthday;
    private String gender;
    private String position;
    private int salary;

    public enum Gender {
        Male, Female, Other
    }

    public enum Position {
        Manager, Receptionist, CleaningStaff, Other
    }

    public Employee(String id, String name, String hometown, String identification, Date birthday, String gender, String position, int salary) {
        this.id = id;
        this.name = name;
        this.hometown = hometown;
        this.identification = identification;
        this.birthday = birthday;
        this.gender = gender;
        this.position = position;
        this.salary = salary;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getHometown() {
        return hometown;
    }

    public String getIdentification() {
        return identification;
    }

    public Date getBirthday() {
        return birthday;
    }

    public String getGender() {
        return gender;
    }

    public String getPosition() {
        return position;
    }

    public int getSalary() {
        return salary;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setHometown(String hometown) {
        this.hometown = hometown;
    }

    public void setIdentification(String identification) {
        this.identification = identification;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public void setSalary(int salary) {
        this.salary = salary;
    }
}
