package models;

import lombok.Getter;
import lombok.Setter;

import java.util.LinkedList;
import java.util.List;

@Getter
@Setter
public class Bill {
    private static int nextId = 1;
    private int id;
    private int roomID;
    private List<Service> services;
    private int price;

    public Bill() {
        this.id = getNextId();
    }

    public Bill(Room room) {
        this();
        this.roomID = room.getId();
        this.services = room.getServices();
        this.price = room.getPrice();
    }

    public List<Service> getServices() {
        return new LinkedList<>(services);
    }

    private static int getNextId() {
        return nextId++;
    }
}
