package org.example.models.dtos;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement(name = "projects")
@XmlAccessorType(XmlAccessType.FIELD)
public class ProjectWrapperDTO {
    @XmlElement(name = "project")
    private List<ProjectImportDTO> projects;

    public ProjectWrapperDTO() {
    }

    public ProjectWrapperDTO(List<ProjectImportDTO> projects) {
        this.projects = projects;
    }

    public List<ProjectImportDTO> getProjects() {
        return projects;
    }
}
