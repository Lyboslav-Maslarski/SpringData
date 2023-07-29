package softuni.exam.models.entity;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "stars")
public class Star {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false, unique = true)
    private String name;
    @Column(name = "light_years", nullable = false)
    private Double lightYears;
    @Column(nullable = false, columnDefinition = "TEXT")
    private String description;
    @Enumerated(EnumType.STRING)
    @Column(name = "star_type", nullable = false)
    private StarType starType;
    @ManyToOne(targetEntity = Constellation.class)
    private Constellation constellation;
    @OneToMany(targetEntity = Astronomer.class, mappedBy = "observingStar")
    private List<Astronomer> observers;

    public Star() {
    }

    public Long getId() {
        return id;
    }

    public Star setId(Long id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public Star setName(String name) {
        this.name = name;
        return this;
    }

    public Double getLightYears() {
        return lightYears;
    }

    public Star setLightYears(Double lightYears) {
        this.lightYears = lightYears;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public Star setDescription(String description) {
        this.description = description;
        return this;
    }

    public StarType getStarType() {
        return starType;
    }

    public Star setStarType(StarType starType) {
        this.starType = starType;
        return this;
    }

    public Constellation getConstellation() {
        return constellation;
    }

    public Star setConstellation(Constellation constellation) {
        this.constellation = constellation;
        return this;
    }

    public List<Astronomer> getObservers() {
        return observers;
    }

    public Star setObservers(List<Astronomer> observers) {
        this.observers = observers;
        return this;
    }

    @Override
    public String toString() {
        return String.format("Star: %s\n" +
                        "   *Distance: %.2f light years\n" +
                        "   **Description: %s\n" +
                        "   ***Constellation: %s",
                getName(), getLightYears(), getDescription(), getConstellation().getName());
    }
}
