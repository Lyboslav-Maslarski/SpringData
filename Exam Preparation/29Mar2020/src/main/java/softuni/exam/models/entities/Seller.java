package softuni.exam.models.entities;

import javax.persistence.*;

@Entity
@Table(name = "sellers")
public class Seller {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(name = "first_name")
    private String firstName;
    @Column(name = "last_name")
    private String lastName;
    @Column(unique = true)
    private String email;
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Rating rating;
    @Column(nullable = false)
    private String town;

    public Seller() {
    }

    public long getId() {
        return id;
    }

    public Seller setId(long id) {
        this.id = id;
        return this;
    }

    public String getFirstName() {
        return firstName;
    }

    public Seller setFirstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public String getLastName() {
        return lastName;
    }

    public Seller setLastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public String getEmail() {
        return email;
    }

    public Seller setEmail(String email) {
        this.email = email;
        return this;
    }

    public Rating getRating() {
        return rating;
    }

    public Seller setRating(Rating rating) {
        this.rating = rating;
        return this;
    }

    public String getTown() {
        return town;
    }

    public Seller setTown(String town) {
        this.town = town;
        return this;
    }
}
