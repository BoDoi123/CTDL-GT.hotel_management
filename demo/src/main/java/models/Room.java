package models;

import lombok.Getter;
import lombok.Setter;

import java.sql.Date;
import java.util.List;
import java.util.LinkedList;

@Getter
@Setter
public class Room {
    private int id;
    private int renterID;
    private boolean isRented;
    private Date renDate;
    private Date orderDate;
    private int price;
    private int billID;
    private List<Service> services;

    public Room(int id, int renterID, Date renDate, Date orderDate, int billID) {
        this.id = id;
        this.renterID = renterID;
        this.isRented = false;
        this.renDate = new Date(renDate.getTime());
        this.orderDate = new Date(orderDate.getTime());
        this.price = 0;
        this.billID = billID;
        this.services = new LinkedList<>();
    }

    public Date getRenDate() {
        return new Date(renDate.getTime());
    }

    public void setRenDate(Date renDate) {
        this.renDate = new Date(renDate.getTime());
    }

    public Date getOrderDate() {
        return new Date(orderDate.getTime());
    }

    public void setOrderDate(Date orderDate) {
        this.orderDate = new Date(orderDate.getTime());
    }

    public int getPrice() {
        if (services.isEmpty()) {
            return 0;
        }
        for (Service service : services) {
            this.price += service.getCost();
        }
        return this.price;
    }

    public void setPrice(List<Service> services) {
        this.services = services;
        int result = 0;

        for (Service service : services) {
            result += service.getCost();
        }
        this.price = result;
    }

    public List<Service> getServices() {
        return new LinkedList<>(services);
    }

    public void addService(Service service) {
        this.services.add(service);
    }

    public void removeService(Service service) {
        this.services.remove(service);
    }
}
