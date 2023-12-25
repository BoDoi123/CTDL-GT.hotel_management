package models;

import lombok.Getter;
import lombok.Setter;
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

    private SimplePriceCalculator priceCalculator;

    private static final Logger LOGGER = Logger.getLogger(Room.class.getName());

    public Room() {
        this.id = nextID.getAndIncrement();
        this.price = 50000;
        this.isRented = false;
    }

    public void rentRoom(Customer customer, LocalDate rentDate, LocalDate departureDate) {
        if (!isRented) {
            try {
                if (departureDate.isAfter(rentDate)) {
                    this.customer = customer;
                    this.services = new LinkedList<>();
                    renterID = customer.getId();
                    isRented = true;

                    this.rentDate = rentDate;
                    customer.setRentDate(rentDate);
                    this.departureDate = departureDate;

                    priceCalculator = new SimplePriceCalculator(rentDate, departureDate);
                    price = priceCalculator.calculatePrice(services);

                    Bill bill = new Bill(this);
                    setBill(bill);
                } else {
                    LOGGER.log(Level.SEVERE, "departureDate must be after rentDate");
                    throw new IllegalArgumentException("departureDate must be after renDate");
                }
            } catch (Exception e) {
                LOGGER.log(Level.SEVERE, "Error processing dates", e);
            }
        } else {
            throw new IllegalArgumentException("Room is rented by customer");
        }
    }

    public void checkout() {
        services = new LinkedList<>();
        isRented = false;
        rentDate = null;
        departureDate = null;
        price = 50000;
        customer.checkOutRoom();
    }

    public void setPrice(List<Service> services) {
        this.services = services;
        priceCalculator = new SimplePriceCalculator(this.rentDate, this.departureDate);
        price = priceCalculator.calculatePrice(services);
        bill.setPrice(price);
    }

    public void addService(Service service) {
        this.services.add(service);
        setPrice(services);
    }

    public void removeService(Service service) {
        this.services.remove(service);
        setPrice(services);
    }

    public void setServices(List<Service> services) {
        this.services = services;
        setPrice(services);
    }

    public void updateDepartureDate(LocalDate departureDate) {
        // Cập nhật ngày trả phòng
        this.departureDate = departureDate;
        bill.setDepartureDate(departureDate);

        // Cập nhật giá thuê
        priceCalculator = new SimplePriceCalculator(this.rentDate, this.departureDate);
        this.price = priceCalculator.calculatePrice(services);
        bill.setPrice(this.price);
    }

    public SimplePriceCalculator getSimplePriceCalculator() {
        return new SimplePriceCalculator(this.rentDate, this.departureDate);
    }

    public void setBill(Bill bill) {
        this.bill = bill;
        this.billID = bill.getId();
    }
}
