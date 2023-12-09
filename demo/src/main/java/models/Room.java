package models;

import lombok.Getter;
import lombok.Setter;
import models.calculateprice.PriceCalculator;
import models.calculateprice.SimplePriceCalculator;

import java.sql.Date;
import java.util.List;
import java.util.LinkedList;

@Getter
@Setter
public class Room {
    private static int nextId = 1;
    private Bill bill;
    private int id;
    private int renterID;
    private boolean isRented;
    private Date renDate;
    private Date orderDate;
    private int price;
    private int billID;
    private List<Service> services;

    private PriceCalculator priceCalculator = new SimplePriceCalculator();

    public Room(int renterID, Date renDate, Date orderDate, Bill bill) {
        this.bill = bill;
        this.id = getNextId();
        this.renterID = renterID;
        this.isRented = false;
        this.renDate = new Date(renDate.getTime());
        this.orderDate = new Date(orderDate.getTime());
        this.price = 0;
        this.billID = bill.getId();
        this.services = new LinkedList<>();
    }

    private static int getNextId() {
        return nextId++;
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

    public void setPrice(List<Service> services) {
        this.services = services;
        price = priceCalculator.calculatePrice(services);
        bill.setPrice(price);
    }

    public List<Service> getServices() {
        return new LinkedList<>(services);
    }

    public void addService(Service service) {
        this.services.add(service);
        setPrice(services);
    }

    public void removeService(Service service) {
        this.services.remove(service);
        setPrice(services);
    }
}
