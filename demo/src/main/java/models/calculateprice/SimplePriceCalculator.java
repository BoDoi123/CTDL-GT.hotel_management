package models.calculateprice;

import models.Service;

import java.sql.Date;

import java.text.SimpleDateFormat;
import java.text.ParseException;

import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SimplePriceCalculator implements PriceCalculator {
    private long numberOfDays; // Số ngày đặt phòng
    private static final Logger LOGGER = Logger.getLogger(SimplePriceCalculator.class.getName());

    public SimplePriceCalculator(String rentDateStr, String departureDateStr) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

        try {
            Date rentDate = new Date(dateFormat.parse(rentDateStr).getTime());
            Date departureDate = new Date(dateFormat.parse(departureDateStr).getTime());


            if (departureDate.before(rentDate)) {
                LOGGER.log(Level.SEVERE, "departureDate must be after rentDate");
                throw new IllegalArgumentException("departureDate must be after rentDate");
            }

            // Tính số ngày thuê
            this.numberOfDays = TimeUnit.DAYS.convert(departureDate.getTime() - rentDate.getTime(), TimeUnit.MILLISECONDS);
        } catch (ParseException e) {
            LOGGER.log(Level.SEVERE, "Error parsing date", e);
        }
    }

    @Override
    public int calculatePrice(List<Service> services) {
        int totalPrice = 0;

        for (Service service : services) {
            totalPrice += service.getCost();
        }

        return totalPrice * (int) numberOfDays;
    }
}
