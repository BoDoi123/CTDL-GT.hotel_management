package models;

import java.sql.Date;

public class Room {
    private String id;
    private Customer renter;
    private boolean isRented;
    private Date renDate;
    private Date orderDate;
    private Customer orderCustomer;
    private int price;
    private Bill bill;

    public Room() {
        System.out.println("models.Room.<init>()");
        this.bill = new Bill(this);
    }

    public Room(String id, Customer renter, boolean isRented, Date renDate, Date orderDate, int price, Bill bill) {
        this();
        this.id = id;
        this.renter = renter;
        this.isRented = isRented;
        this.renDate = renDate;
        this.orderDate = orderDate;
        this.price = price;
        this.bill = bill;
    }

    public Room(String id, int price) {
        this();
        this.id = id;
        this.price = price;
    }

    public String getId() {
        return id;
    }

    public Customer getRenter() {
        return renter;
    }

    public boolean isRented() {
        return isRented;
    }

    public Date getRenDate() {
        return renDate;
    }

    public Date getOrderDate() {
        return orderDate;
    }

    public Customer getOrderCustomer() {
        return orderCustomer;
    }

    public int getPrice() {
        return price;
    }

    public Bill getBill() {
        return bill;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setRenter(Customer renter) {
        this.renter = renter;
    }

    public void setRented(boolean rented) {
        isRented = rented;
    }

    public void setRenDate(Date renDate) {
        this.renDate = renDate;
    }

    public void setOrderDate(Date orderDate) {
        this.orderDate = orderDate;
    }

    public void setOrderCustomer(Customer orderCustomer) {
        this.orderCustomer = orderCustomer;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public void setBill(Bill bill) {
        this.bill = bill;
    }
}
