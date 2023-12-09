package models;

import lombok.Getter;
import lombok.Setter;
import models.calculateprice.PriceCalculator;
import models.calculateprice.SimplePriceCalculator;

import java.sql.Date;

import java.text.ParseException;
import java.text.SimpleDateFormat;

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
    private Date rentDate;
    private Date departureDate;
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

    public void rentRoom(Room room, Customer customer, String rentDateStr, String departureDateStr, List<Service> services) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

        try {
            Date rentDate = new Date(dateFormat.parse(rentDateStr).getTime());
            Date depatureDate = new Date(dateFormat.parse(departureDateStr).getTime());

            if (departureDate.after(rentDate)) {
                room.services = services;
                room.renterID = customer.getId();
                room.isRented = true;

                room.rentDate = new Date(rentDate.getTime());
                customer.setRentDate(rentDate);
                room.departureDate = new Date(departureDate.getTime());

                priceCalculator = new SimplePriceCalculator(rentDateStr, departureDateStr);
                room.price = priceCalculator.calculatePrice(services);

                this.bill = new Bill(room);
                this.billID = bill.getId();
            } else {
                LOGGER.log(Level.SEVERE, "departureDate must be after rentDate");
                throw new IllegalArgumentException("departureDate must be after renDate");
            }
        } catch (ParseException e) {
            LOGGER.log(Level.SEVERE, "Error parsing date", e);
        }
    }

    private static int getNextId() {
        return nextId++;
    }

    public Date getRenDate() {
        return new Date(rentDate.getTime());
    }

    public void setRenDate(String rentDateStr) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

        try {
            this.rentDate = new Date(dateFormat.parse(rentDateStr).getTime());
        } catch (ParseException e) {
            LOGGER.log(Level.SEVERE, "Error parsing date", e);
        }
    }

    public Date getDepartureDate() {
        return new Date(departureDate.getTime());
    }

    public void setDepartureDate(String departureDateStr) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

        try {
            this.departureDate = new Date(dateFormat.parse(departureDateStr).getTime());
        } catch (ParseException e) {
            LOGGER.log(Level.SEVERE, "Error parsing date", e);
        }
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
