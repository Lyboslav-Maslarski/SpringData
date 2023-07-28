package softuni.exam.models.dto;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

@XmlAccessorType(XmlAccessType.FIELD)
public class OfferImportDTO {
    @NotNull
    @Positive
    @XmlElement
    private Double price;
    @NotNull
    @XmlElement
    private String publishedOn;
    @NotNull
    @XmlElement
    private AgentNameDTO agent;
    @NotNull
    @XmlElement
    private ApartmentIdDTO apartment;

    public OfferImportDTO() {
    }

    public Double getPrice() {
        return price;
    }

    public OfferImportDTO setPrice(Double price) {
        this.price = price;
        return this;
    }

    public String getPublishedOn() {
        return publishedOn;
    }

    public OfferImportDTO setPublishedOn(String publishedOn) {
        this.publishedOn = publishedOn;
        return this;
    }

    public AgentNameDTO getAgent() {
        return agent;
    }

    public OfferImportDTO setAgent(AgentNameDTO agent) {
        this.agent = agent;
        return this;
    }

    public ApartmentIdDTO getApartment() {
        return apartment;
    }

    public OfferImportDTO setApartment(ApartmentIdDTO apartment) {
        this.apartment = apartment;
        return this;
    }
}
