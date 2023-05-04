package entities;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "drivers")
public class Driver {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(name = "full_name")
    private String fullName;
    @ManyToMany(mappedBy = "drivers", targetEntity = Truck.class)
    private Set<Truck> trucks;

    public Driver() {
    }

    public Driver(String fullName) {
        this.fullName = fullName;
        this.trucks = new HashSet<>();
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public Set<Truck> getTrucks() {
        return trucks;
    }

    public void addTrucks(Truck truck) {
        this.trucks.add(truck);
    }
}
