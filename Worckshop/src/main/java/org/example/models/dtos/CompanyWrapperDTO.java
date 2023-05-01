package org.example.models.dtos;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement(name = "companies")
@XmlAccessorType(XmlAccessType.FIELD)
public class CompanyWrapperDTO {
    @XmlElement(name = "company")
    private List<CompanyImportDTO> companies;

    public CompanyWrapperDTO() {
    }

    public List<CompanyImportDTO> getCompanies() {
        return companies;
    }
}
