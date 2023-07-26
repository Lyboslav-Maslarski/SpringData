package softuni.exam.domain.entities;

import javax.persistence.*;

@Entity
@Table(name = "teams")
public class Team {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(nullable = false)
    private String name;
    @ManyToOne(optional = false)
    private Picture picture;

    public Team() {
    }

    public Integer getId() {
        return id;
    }

    public Team setId(Integer id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public Team setName(String name) {
        this.name = name;
        return this;
    }

    public Picture getPicture() {
        return picture;
    }

    public Team setPicture(Picture picture) {
        this.picture = picture;
        return this;
    }
}
