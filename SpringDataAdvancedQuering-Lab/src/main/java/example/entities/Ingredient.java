package example.entities;
import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Set;

@Entity
@Table(name = "ingredients")
@NamedQueries({
        @NamedQuery(name = "Ingredient.increaseAllIngredientsPriceBy20PercentsNamedQuery",
                query = "UPDATE Ingredient AS i SET i.price = i.price * 1.2"),
        @NamedQuery(name = "Ingredient.increaseIngredientsPriceBy20PercentsFromListNamedQuery",
                query = "UPDATE Ingredient AS i SET i.price = i.price * 1.2 WHERE i.name IN :names")
})
public class Ingredient extends BaseEntity {

    private String name;
    private BigDecimal price;
    private Set<Shampoo> shampoos;

    public Ingredient() {
    }

    @Column(name = "name")
    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Column(name = "price")
    public BigDecimal getPrice() {
        return this.price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    @ManyToMany(mappedBy = "ingredients",
            fetch = FetchType.EAGER,
            cascade = CascadeType.ALL)
    public Set<Shampoo> getShampoos() {
        return this.shampoos;
    }

    public void setShampoos(Set<Shampoo> shampoos) {
        this.shampoos = shampoos;
    }

    @Override
    public String toString() {
        return getName();
    }
}
