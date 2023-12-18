package models.calculateprice;

import lombok.Setter;
import lombok.Getter;

import models.Service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@Getter
@Setter
public class SimplePriceCalculator implements PriceCalculator {
    private long numberOfDays; // Số ngày đặt phòng
    private int pricePerDay = 50000; // Giá thuê một ngày
    private static final Logger LOGGER = Logger.getLogger(SimplePriceCalculator.class.getName());

    public SimplePriceCalculator(LocalDate rentDate, LocalDate departureDate) {
        try {
            if (departureDate.isBefore(rentDate)) {
                LOGGER.log(Level.SEVERE, "departureDate must be after rentDate");
                throw new IllegalArgumentException("departureDate must be after rentDate");
            }

            // Tính số ngày thuê
            this.numberOfDays = ChronoUnit.DAYS.between(rentDate, departureDate);
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error processing dates", e);
        }
    }

    @Override
    public int calculatePrice(List<Service> services) {
        for (Service service : services) {
            pricePerDay += service.getCost();
        }

        return pricePerDay * (int) numberOfDays;
    }
}
