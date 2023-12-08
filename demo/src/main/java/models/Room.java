package models;

import lombok.Getter;
import lombok.Setter;

import java.sql.Date;

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

    public Room(int id, int renterID, Date renDate, Date orderDate, int price, int billID) {
        this.id = id;
        this.renterID = renterID;
        this.isRented = false;
        this.renDate = new Date(renDate.getTime());
        this.orderDate = new Date(orderDate.getTime());
        this.price = price;
        this.billID = billID;
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
}
