package _06_FootballBettingDatabase.entities;

import javax.persistence.*;
import java.util.Set;

@Entity(name = "countries")
public class Country {
    @Id
    @Column(unique = true, nullable = false, length = 3)
    private String id;
    private String name;
    @ManyToMany
    @JoinTable(name = "counties_continents",
            joinColumns = @JoinColumn(name = "country_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "continent_id", referencedColumnName = "id"))
    private Set<Continent> continents;

    public Country() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<Continent> getContinents() {
        return continents;
    }

    public void setContinents(Set<Continent> continents) {
        this.continents = continents;
    }
}
