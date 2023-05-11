package example.model.entity;

import java.math.BigDecimal;

public interface ReducedBook {

    public String getTitle();

    public EditionType getEditionType();

    public AgeRestriction getAgeRestriction();

    public BigDecimal getPrice();


    default String String() {
        return getTitle() + " " + getEditionType() + " " + getAgeRestriction() + " " + getPrice();
    }
}
