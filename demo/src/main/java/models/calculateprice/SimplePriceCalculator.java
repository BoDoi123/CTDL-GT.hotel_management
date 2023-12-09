package models.calculateprice;

import models.Service;

import java.util.List;

public class SimplePriceCalculator implements PriceCalculator {
    @Override
    public int calculatePrice(List<Service> services) {
        int totalPrice = 0;

        for (Service service : services) {
            totalPrice += service.getCost();
        }

        return totalPrice;
    }
}
