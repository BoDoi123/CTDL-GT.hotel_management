package models;

import lombok.Getter;
import lombok.Setter;

import java.util.concurrent.atomic.AtomicInteger;

@Getter
@Setter
public class Bill {
    private static AtomicInteger nextID = new AtomicInteger(1);
    private int id;
    private int roomID;
    private int renterID;
    private int price;

    public Bill() {
        this.id = nextID.getAndIncrement();
    }

    public Bill(Room room) {
        this();
        this.roomID = room.getId();
        this.renterID = room.getCustomer().getId();
        this.price = room.getPrice();
    }
}
