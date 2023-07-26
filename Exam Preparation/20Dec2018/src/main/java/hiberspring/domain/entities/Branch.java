package hiberspring.domain.entities;

import javax.persistence.*;

@Entity
@Table(name = "branches")
public class Branch {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false)
    private String name;

    @ManyToOne(optional = false)
    private Town town;

    public Branch() {
    }

    public long getId() {
        return id;
    }

    public Branch setId(long id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public Branch setName(String name) {
        this.name = name;
        return this;
    }

    public Town getTown() {
        return town;
    }

    public Branch setTown(Town town) {
        this.town = town;
        return this;
    }
}
