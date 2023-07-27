package softuni.exam.models.entities;

import javax.persistence.*;

@Entity
@Table(name = "towns")
public class Town {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(unique = true)
    private String name;
    private Integer population;
    private String guide;

    public Town() {
    }

    public long getId() {
        return id;
    }

    public Town setId(long id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public Town setName(String name) {
        this.name = name;
        return this;
    }

    public Integer getPopulation() {
        return population;
    }

    public Town setPopulation(Integer population) {
        this.population = population;
        return this;
    }

    public String getGuide() {
        return guide;
    }

    public Town setGuide(String guide) {
        this.guide = guide;
        return this;
    }
}
