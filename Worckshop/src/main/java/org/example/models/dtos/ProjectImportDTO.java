package org.example.models.dtos;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import java.math.BigDecimal;

@XmlAccessorType(XmlAccessType.FIELD)
public class ProjectImportDTO {
    @XmlElement
    private String name;
    @XmlElement
    private String description;
    @XmlElement(name = "start-date")
    private String startDate;
    @XmlElement(name = "is-finished")
    private boolean isFinished;
    @XmlElement
    private BigDecimal payment;
    @XmlElement
    private CompanyImportDTO company;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public boolean isFinished() {
        return isFinished;
    }

    public void setFinished(boolean finished) {
        isFinished = finished;
    }

    public BigDecimal getPayment() {
        return payment;
    }

    public void setPayment(BigDecimal payment) {
        this.payment = payment;
    }

    public CompanyImportDTO getCompany() {
        return company;
    }

    public void setCompany(CompanyImportDTO company) {
        this.company = company;
    }
}
