package entities;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "trucks")
public class Truck extends Vehicle {
    @Basic
    private Double loadCapacity;
    @ManyToMany(targetEntity = Driver.class)
    @JoinTable(name = "trucks_drivers",
            joinColumns = @JoinColumn(name = "truck_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "driver_id", referencedColumnName = "id"))
    private Set<Driver> drivers;

    public Truck() {
    }

    public Truck(String type, String model, BigDecimal price, String fuelType, Double loadCapacity) {
        super(type, model, price, fuelType);
        this.loadCapacity = loadCapacity;
        this.drivers = new HashSet<>();
    }

    public Double getLoadCapacity() {
        return loadCapacity;
    }

    public void setLoadCapacity(Double loadCapacity) {
        this.loadCapacity = loadCapacity;
    }

    public Set<Driver> getDrivers() {
        return drivers;
    }

    public void addDriver(Driver driver) {
        this.drivers.add(driver);
    }
}
