package models;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Bill {
    private static int nextId = 1;
    private int id;
    private int roomID;
    private int price;

    public Bill() {
        this.id = getNextId();
    }

    public Bill(Room room) {
        this();
        this.roomID = room.getId();
        this.price = room.getPrice();
    }

    private static int getNextId() {
        return nextId++;
    }
}
