package models;

import lombok.Getter;
import lombok.Setter;

import java.util.LinkedList;
import java.util.List;

@Getter
@Setter
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

    public List<Service> getServices() {
        return new LinkedList<>(services);
    }

    public void setServices(List<Service> services) {
        this.services = new LinkedList<>(services);
    }

    public void addService(Service service) {
        this.services.add(service);
    }

    public void removeService(Service service) {
        this.services.remove(service);
    }
}
