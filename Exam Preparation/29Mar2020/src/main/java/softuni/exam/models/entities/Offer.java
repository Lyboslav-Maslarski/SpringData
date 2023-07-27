package softuni.exam.models.entities;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "offers")
public class Offer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private BigDecimal price;
    @Column(columnDefinition = "TEXT")
    private String description;
    private Boolean hasGoldStatus;
    private LocalDateTime addedOn;
    @ManyToOne
    private Car car;
    @ManyToOne
    private Seller seller;
    @ManyToMany
    private List<Picture> pictures;

    public Offer() {
    }

    public long getId() {
        return id;
    }

    public Offer setId(long id) {
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

    public String getDescription() {
        return description;
    }

    public Offer setDescription(String description) {
        this.description = description;
        return this;
    }

    public Boolean getHasGoldStatus() {
        return hasGoldStatus;
    }

    public Offer setHasGoldStatus(Boolean hasGoldStatus) {
        this.hasGoldStatus = hasGoldStatus;
        return this;
    }

    public LocalDateTime getAddedOn() {
        return addedOn;
    }

    public Offer setAddedOn(LocalDateTime addedOn) {
        this.addedOn = addedOn;
        return this;
    }

    public Car getCar() {
        return car;
    }

    public Offer setCar(Car car) {
        this.car = car;
        return this;
    }

    public Seller getSeller() {
        return seller;
    }

    public Offer setSeller(Seller seller) {
        this.seller = seller;
        return this;
    }

    public List<Picture> getPictures() {
        return pictures;
    }

    public Offer setPictures(List<Picture> pictures) {
        this.pictures = pictures;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Offer offer = (Offer) o;
        return Objects.equals(description, offer.description) && Objects.equals(addedOn, offer.addedOn);
    }

    @Override
    public int hashCode() {
        return Objects.hash(description, addedOn);
    }
}
