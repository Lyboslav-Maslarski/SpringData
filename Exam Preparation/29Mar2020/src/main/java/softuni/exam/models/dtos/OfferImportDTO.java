package softuni.exam.models.dtos;

import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import java.math.BigDecimal;

@XmlAccessorType(XmlAccessType.FIELD)
public class OfferImportDTO {
    @XmlElement
    @Size(min = 5)
    private String description;
    @XmlElement
    @Positive
    private BigDecimal price;
    @XmlElement(name = "added-on")
    private String addedOn;
    @XmlElement(name = "has-gold-status")
    private boolean hasGoldStatus;
    @XmlElement
    private CarIdDTO car;
    @XmlElement
    private SellerIdDTO seller;

    public OfferImportDTO() {
    }

    public String getDescription() {
        return description;
    }

    public OfferImportDTO setDescription(String description) {
        this.description = description;
        return this;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public OfferImportDTO setPrice(BigDecimal price) {
        this.price = price;
        return this;
    }

    public String getAddedOn() {
        return addedOn;
    }

    public OfferImportDTO setAddedOn(String addedOn) {
        this.addedOn = addedOn;
        return this;
    }

    public boolean isHasGoldStatus() {
        return hasGoldStatus;
    }

    public OfferImportDTO setHasGoldStatus(boolean hasGoldStatus) {
        this.hasGoldStatus = hasGoldStatus;
        return this;
    }

    public CarIdDTO getCar() {
        return car;
    }

    public OfferImportDTO setCar(CarIdDTO car) {
        this.car = car;
        return this;
    }

    public SellerIdDTO getSeller() {
        return seller;
    }

    public OfferImportDTO setSeller(SellerIdDTO seller) {
        this.seller = seller;
        return this;
    }
}
