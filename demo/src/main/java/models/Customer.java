package models;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.concurrent.atomic.AtomicInteger;

@Getter
@Setter
public class Customer {
    private static AtomicInteger nextID = new AtomicInteger(1);
    private int id;
    private String name;
    private Gender gender;
    private LocalDate birthday;
    private String identification;
    private String hometown;
    private LocalDate rentDate;

    public enum Gender {
        Male, Female
    }

    public Customer() {

    }

    public Customer(String name, Gender gender, LocalDate birthday, String identification, String hometown) {
        this.id = nextID.getAndIncrement();
        this.name = name;
        this.gender = gender;
        this.birthday = birthday;
        this.identification = identification;
        this.hometown = hometown;
    }

    public void checkOutRoom() {
        rentDate = null;
    }
}