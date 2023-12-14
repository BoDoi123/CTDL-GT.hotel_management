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
    private int price;

    public Bill(Room room) {
        this.id = nextID.getAndIncrement();
        this.roomID = room.getId();
        this.price = room.getPrice();
    }
}
