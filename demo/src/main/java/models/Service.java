package models;

import java.util.LinkedList;
import java.util.List;

public class Service {
    private String name;
    private int cost;
    private List<Room> signedRooms;

    public Service(String name, int cost) {
        this.name = name;
        this.cost = cost;
        this.signedRooms = new LinkedList<>();
    }

    public String getName() {
        return name;
    }

    public int getCost() {
        return cost;
    }

    public List<Room> getSignedRooms() {
        return signedRooms;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }

    public void setSignedRooms(List<Room> signedRooms) {
        this.signedRooms = signedRooms;
    }
}
