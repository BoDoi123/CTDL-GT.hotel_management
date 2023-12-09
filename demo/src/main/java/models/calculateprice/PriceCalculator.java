package models.calculateprice;

import models.Service;

import java.util.List;

public interface PriceCalculator {
    int calculatePrice(List<Service> services);
}
