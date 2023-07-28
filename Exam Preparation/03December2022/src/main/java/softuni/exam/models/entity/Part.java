package softuni.exam.models.entity;

import javax.persistence.*;

@Entity
@Table(name = "parts")
public class Part {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "part_name",nullable = false)
    private String partName;
    @Column(nullable = false)
    private Double price;
    @Column(nullable = false)
    private Integer quantity;

    public Part() {
    }

    public Long getId() {
        return id;
    }

    public Part setId(Long id) {
        this.id = id;
        return this;
    }

    public String getPartName() {
        return partName;
    }

    public Part setPartName(String partName) {
        this.partName = partName;
        return this;
    }

    public Double getPrice() {
        return price;
    }

    public Part setPrice(Double price) {
        this.price = price;
        return this;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public Part setQuantity(Integer quantity) {
        this.quantity = quantity;
        return this;
    }
}
