package softuni.exam.models.entity;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "offers")
public class Offer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private BigDecimal price;
    @Column(nullable = false)
    private LocalDate publishedOn;
    @ManyToOne
    private Agent agent;
    @ManyToOne
    private Apartment apartment;

    public Offer() {
    }

    public Long getId() {
        return id;
    }

    public Offer setId(Long id) {
        this.id = id;
        return this;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public Offer setPrice(BigDecimal price) {
        this.price = price;
        return this;
    }

    public LocalDate getPublishedOn() {
        return publishedOn;
    }

    public Offer setPublishedOn(LocalDate publishedOn) {
        this.publishedOn = publishedOn;
        return this;
    }

    public Agent getAgent() {
        return agent;
    }

    public Offer setAgent(Agent agent) {
        this.agent = agent;
        return this;
    }

    public Apartment getApartment() {
        return apartment;
    }

    public Offer setApartment(Apartment apartment) {
        this.apartment = apartment;
        return this;
    }

    @Override
    public String toString() {
        return String.format("""
                        Agent %s %s with offer №%d:
                           -Apartment area: %.2f
                           --Town: %s
                           ---Price: %.2f$""",
                getAgent().getFirstName(), getAgent().getLastName(), getId(),
                getApartment().getArea(),
                getApartment().getTown().getTownName(),
                getPrice());
    }
}
