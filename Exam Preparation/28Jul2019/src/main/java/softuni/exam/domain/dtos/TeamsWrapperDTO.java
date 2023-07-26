package softuni.exam.domain.dtos;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement(name = "teams")
@XmlAccessorType(XmlAccessType.FIELD)
public class TeamsWrapperDTO {
    @XmlElement(name = "team")
    private List<TeamImportDTO> teams;

    public TeamsWrapperDTO() {
    }

    public List<TeamImportDTO> getTeams() {
        return teams;
    }

    public TeamsWrapperDTO setTeams(List<TeamImportDTO> teams) {
        this.teams = teams;
        return this;
    }
}
