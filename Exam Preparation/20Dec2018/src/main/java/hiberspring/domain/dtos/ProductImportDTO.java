package hiberspring.domain.dtos;

import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.*;

@XmlRootElement(name = "product")
@XmlAccessorType(XmlAccessType.FIELD)
public class ProductImportDTO {
    @NotNull
    @XmlAttribute
    private String name;
    @NotNull
    @XmlAttribute
    private Integer clients;
    @NotNull
    @XmlElement
    private String branch;

    public ProductImportDTO() {
    }

    public String getName() {
        return name;
    }

    public ProductImportDTO setName(String name) {
        this.name = name;
        return this;
    }

    public Integer getClients() {
        return clients;
    }

    public ProductImportDTO setClients(Integer clients) {
        this.clients = clients;
        return this;
    }

    public String getBranch() {
        return branch;
    }

    public ProductImportDTO setBranch(String branch) {
        this.branch = branch;
        return this;
    }
}
