package models;

import lombok.Getter;
import lombok.Setter;
import models.calculateprice.PriceCalculator;
import models.calculateprice.SimplePriceCalculator;

import java.time.LocalDate;
import java.util.List;
import java.util.LinkedList;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.logging.Level;
import java.util.logging.Logger;

@Getter
@Setter
public class Room {
    private static AtomicInteger nextID = new AtomicInteger(1);
    private Bill bill;
    private Customer customer;
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
        this.id = nextID.getAndIncrement();
        this.price = 0;
        this.isRented = false;
    }

    public void rentRoom(Customer customer, LocalDate rentDate, LocalDate departureDate) {
        try {
            if (departureDate.isAfter(rentDate)) {
                this.customer = customer;
                this.services = new LinkedList<>();
                renterID = customer.getId();
                isRented = true;

                this.rentDate = rentDate;
                customer.setRentDate(rentDate);
                customer.rentRoom(this);
                this.departureDate = departureDate;

                priceCalculator = new SimplePriceCalculator(rentDate, departureDate);
                price = priceCalculator.calculatePrice(services);

                this.bill = new Bill(this);
                this.billID = bill.getId();
            } else {
                LOGGER.log(Level.SEVERE, "departureDate must be after rentDate");
                throw new IllegalArgumentException("departureDate must be after renDate");
            }
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error processing dates", e);
        }
    }

    public void checkout() {
        isRented = false;
        services = null;
        rentDate = null;
        departureDate = null;
        renterID = 0;
        billID = 0;
        bill = null;
        price = 0;
        getCustomer().checkOutRoom();
    }

    public void setPrice(List<Service> services) {
        this.services = services;
        priceCalculator = new SimplePriceCalculator(this.rentDate, this.departureDate);
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

    public void updateDepartureDate(LocalDate departureDate) {
        this.departureDate = departureDate;
        priceCalculator = new SimplePriceCalculator(this.rentDate, this.departureDate);
        this.price = priceCalculator.calculatePrice(services);
        bill.setPrice(this.price);
    }
}
