package softuni.exam.models.entity;

import javax.persistence.*;

@Entity
@Table(name = "towns")
public class Town {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "town_name", nullable = false, unique = true)
    private String townName;
    @Column(nullable = false)
    private Integer population;

    public Town() {
    }

    public Long getId() {
        return id;
    }

    public Town setId(Long id) {
        this.id = id;
        return this;
    }

    public String getTownName() {
        return townName;
    }

    public Town setTownName(String townName) {
        this.townName = townName;
        return this;
    }

    public Integer getPopulation() {
        return population;
    }

    public Town setPopulation(Integer population) {
        this.population = population;
        return this;
    }
}
