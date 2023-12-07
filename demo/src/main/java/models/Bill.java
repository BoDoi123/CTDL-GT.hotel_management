package models;

import java.util.List;
import java.util.LinkedList;

public class Bill {
    private Room room;
    private List<Service> services;

    public Bill() {
        services = new LinkedList<>();
    }

    public Bill(Room room) {
        this();
        this.room = room;
    }

    public Room getRoom() {
        return room;
    }

    public List<Service> getServices() {
        return services;
    }

    public void setRoom(Room room) {
        this.room = room;
    }

    public void setServices(List<Service> services) {
        this.services = services;
    }

    public void appendService(Service service) {
        this.services.add(service);
    }
}
