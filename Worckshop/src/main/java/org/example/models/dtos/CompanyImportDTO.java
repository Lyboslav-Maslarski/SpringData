package org.example.models.dtos;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;

@XmlAccessorType(XmlAccessType.FIELD)
public class CompanyImportDTO {
    @XmlAttribute
    private String name;

    public CompanyImportDTO() {
    }

    public String getName() {
        return name;
    }
}
