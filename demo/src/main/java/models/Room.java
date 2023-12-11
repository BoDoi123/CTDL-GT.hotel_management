package models;

import lombok.Getter;
import lombok.Setter;
import models.calculateprice.PriceCalculator;
import models.calculateprice.SimplePriceCalculator;

import java.time.LocalDate;
import java.util.List;
import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;

@Getter
@Setter
public class Room {
    private static int nextId = 1;
    private Bill bill;
    private int id;
    private int renterID;
    private boolean isRented;
    private LocalDate rentDate;
    private LocalDate departureDate;
    private int price;
    private int billID;
    private List<Service> services;

    private PriceCalculator priceCalculator;

    private static final Logger LOGGER = Logger.getLogger(Room.class.getName());

    public Room() {
        this.id = getNextId();
        this.price = 0;
        this.services = new LinkedList<>();
        this.isRented = false;
    }

    public void rentRoom(Room room, Customer customer, LocalDate rentDate, LocalDate departureDate, List<Service> services) {
        try {
            if (departureDate.isAfter(rentDate)) {
                room.services = services;
                room.renterID = customer.getId();
                room.isRented = true;

                room.rentDate = rentDate;
                customer.setRentDate(rentDate);
                room.departureDate = departureDate;

                priceCalculator = new SimplePriceCalculator(rentDate, departureDate);
                room.price = priceCalculator.calculatePrice(services);

                this.bill = new Bill(room);
                this.billID = bill.getId();
            } else {
                LOGGER.log(Level.SEVERE, "departureDate must be after rentDate");
                throw new IllegalArgumentException("departureDate must be after renDate");
            }
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error processing dates", e);
        }
    }

    private static int getNextId() {
        return nextId++;
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
