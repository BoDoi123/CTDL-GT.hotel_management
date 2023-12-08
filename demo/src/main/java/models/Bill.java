package models;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class Bill {
    private Room room;
    private List<Service> services;

    public Bill() {
        services = new ArrayList<>();
    }

    public Bill(Room room) {
        this();
        this.room = room;
    }

    public List<Service> getServices() {
        List<Service> copiedServices = new ArrayList<>();
        for (Service service : services) {
            copiedServices.add(new Service(service.getName(), service.getCost()));
        }
        return copiedServices;
    }

    public void setServices(List<Service> services) {
        List<Service> copiedServices = new ArrayList<>();
        for (Service service : services) {
            copiedServices.add(new Service(service.getName(), service.getCost()));
        }
        this.services = copiedServices;
    }

    public void addService(Service service) {
        this.services.add(new Service(service.getName(), service.getCost())); // DeepCopy cho Service
    }
}
