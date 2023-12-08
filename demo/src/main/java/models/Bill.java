package models;

import lombok.Getter;
import lombok.Setter;

import java.util.LinkedList;
import java.util.List;

@Getter
@Setter
public class Bill {
    private Room room;
    private int roomID;
    private List<Service> services;
    private int price;

    public Bill() {
        services = new LinkedList<>();
    }

    public Bill(Room room) {
        this.room = room;
        this.roomID = room.getId();
        this.services = room.getServices();
        this.price = room.getPrice();
    }

    public List<Service> getServices() {
        return new LinkedList<>(services);
    }

    public void setServices(List<Service> services) {
        this.services = new LinkedList<>(services);
        room.setServices(services);
        room.setPrice(services);
        this.price = room.getPrice();
    }

    public void addService(Service service) {
        this.services.add(service);
        room.addService(service);
        this.price = room.getPrice();
    }

    public void removeService(Service service) {
        this.services.remove(service);
        room.removeService(service);
        this.price = room.getPrice();
    }
}
