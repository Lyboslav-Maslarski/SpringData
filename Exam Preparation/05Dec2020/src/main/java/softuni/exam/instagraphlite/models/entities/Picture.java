package softuni.exam.instagraphlite.models.entities;

import javax.persistence.*;

@Entity
@Table(name = "pictures")
public class Picture {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(nullable = false, unique = true)
    private String path;
    @Column(nullable = false)
    private Double size;

    public Picture() {
    }

    public int getId() {
        return id;
    }

    public Picture setId(int id) {
        this.id = id;
        return this;
    }

    public String getPath() {
        return path;
    }

    public Picture setPath(String path) {
        this.path = path;
        return this;
    }

    public Double getSize() {
        return size;
    }

    public Picture setSize(Double size) {
        this.size = size;
        return this;
    }

    @Override
    public String toString() {
        return String.format("%.2f - %s", getSize(), getPath());
    }
}
