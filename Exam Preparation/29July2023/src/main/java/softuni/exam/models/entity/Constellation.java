package softuni.exam.models.entity;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "constellations")
public class Constellation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false,unique = true)
    private String name;
    @Column(nullable = false)
    private String description;
    @OneToMany(targetEntity = Star.class,mappedBy = "constellation")
    private List<Star> stars;

    public Constellation() {
    }

    public Long getId() {
        return id;
    }

    public Constellation setId(Long id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public Constellation setName(String name) {
        this.name = name;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public Constellation setDescription(String description) {
        this.description = description;
        return this;
    }

    public List<Star> getStars() {
        return stars;
    }

    public Constellation setStars(List<Star> stars) {
        this.stars = stars;
        return this;
    }
}
