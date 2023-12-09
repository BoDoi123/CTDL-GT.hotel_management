package models.calculateprice;

import models.Service;

import java.sql.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class SimplePriceCalculator implements PriceCalculator {
    private final long numberOfDays; // Số ngày đặt phòng

    public SimplePriceCalculator(Date renDate, Date departureDate) {
        if (departureDate.before(renDate)) {
            throw new IllegalArgumentException("departureDate must be after renDate");
        }

        // Tính số ngày thuê
        this.numberOfDays = TimeUnit.DAYS.convert(departureDate.getTime() - renDate.getTime(), TimeUnit.MICROSECONDS);
    }
    @Override
    public int calculatePrice(List<Service> services) {
        int totalPrice = 0;

        for (Service service : services) {
            totalPrice += service.getCost();
        }

        return totalPrice * (int)numberOfDays;
    }
}
