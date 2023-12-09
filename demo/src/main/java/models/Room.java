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
    private Date departureDate;
    private int price;
    private int billID;
    private List<Service> services;

    private PriceCalculator priceCalculator;

    public Room() {
        this.id = getNextId();
        this.price = 0;
        this.services = new LinkedList<>();
        this.isRented = false;
    }

    public void rentRoom(Room room, Customer customer, Date renDate, Date departureDate, List<Service> services) {
        if (departureDate.after(renDate)) {
            room.services = services;
            room.renterID = customer.getId();
            room.isRented = true;

            room.renDate = new Date(renDate.getTime());
            customer.setRentDate(renDate);
            room.departureDate = new Date(departureDate.getTime());

            priceCalculator = new SimplePriceCalculator(renDate, departureDate);
            room.price = priceCalculator.calculatePrice(services);

            this.bill = new Bill(room);
            this.billID = bill.getId();
        } else {
            throw new IllegalArgumentException("departureDate must be after renDate");
        }
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

    public Date getDepartureDate() {
        return new Date(departureDate.getTime());
    }

    public void setDepartureDate(Date departureDate) {
        this.departureDate = new Date(departureDate.getTime());
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
