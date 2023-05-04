package org.example.models.dtos;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;
@XmlRootElement(name = "employees")
@XmlAccessorType(XmlAccessType.FIELD)
public class EmployeeWrapperDTO {
    @XmlElement(name = "employee")
    private List<EmployeeImportDTO> employees;

    public List<EmployeeImportDTO> getEmployees() {
        return employees;
    }
}
