package models;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Service {
    private String name;
    private int cost;

    public Service() {

    }

    public Service(String name, int cost) {
        this.name = name;
        setCost(cost);
    }

    public void setCost(int cost) {
        if (cost < 0) {
            throw new IllegalArgumentException("Cost must be non-negative.");
        }
        this.cost = cost;
    }
}
