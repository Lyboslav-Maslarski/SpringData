package softuni.exam.models.entity;

import javax.persistence.*;

@Entity
@Table(name = "apartments")
public class Apartment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Enumerated(EnumType.STRING)
    @Column(name = "apartment_type", nullable = false)
    private ApartmentType apartmentType;
    @Column(nullable = false)
    private Double area;
    @ManyToOne
    private Town town;

    public Apartment() {
    }

    public Long getId() {
        return id;
    }

    public Apartment setId(Long id) {
        this.id = id;
        return this;
    }

    public ApartmentType getApartmentType() {
        return apartmentType;
    }

    public Apartment setApartmentType(ApartmentType apartmentType) {
        this.apartmentType = apartmentType;
        return this;
    }

    public Double getArea() {
        return area;
    }

    public Apartment setArea(Double area) {
        this.area = area;
        return this;
    }

    public Town getTown() {
        return town;
    }

    public Apartment setTown(Town town) {
        this.town = town;
        return this;
    }
}
