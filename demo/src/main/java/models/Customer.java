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
    private Room room;
    private int id;
    private int roomID;
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

    public Customer(String name, Gender gender, LocalDate birthday, String identification, String hometown) {
        room = null;
        this.id = nextID.getAndIncrement();
        this.name = name;
        this.gender = gender;
        this.birthday = birthday;
        this.identification = identification;
        this.hometown = hometown;
    }

    public void rentRoom(Room room) {
        this.room = room;
        this.roomID = room.getId();
    }

    public void checkOutRoom(Room room) {
        roomID = 0;
        rentDate = null;
    }
}