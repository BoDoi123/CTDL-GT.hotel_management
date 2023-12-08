package models;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class Service {
    private String name;
    private int cost;
    private List<Room> signedRooms;

    public Service(String name, int cost) {
        this.name = name;
        setCost(cost);
        this.signedRooms = new ArrayList<>();
    }

    public void setCost(int cost) {
        if (cost < 0) {
            throw new IllegalArgumentException("Cost must be non-negative.");
        }
        this.cost = cost;
    }

    public List<Room> getSignedRooms() {
        List<Room> copiedRooms = new ArrayList<>();
        for (Room room : signedRooms) {
            copiedRooms.add(new Room(room.getId(), room.getRenterID(), room.getRenDate(), room.getOrderDate(), room.getPrice(), room.getBillID()));
        }
        return copiedRooms;
    }

    public void addSignedRoom(Room room) {
        signedRooms.add(new Room(room.getId(), room.getRenterID(), room.getRenDate(), room.getOrderDate(), room.getPrice(), room.getBillID()));
    }
}
