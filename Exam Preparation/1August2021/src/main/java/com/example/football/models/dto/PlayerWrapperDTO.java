package com.example.football.models.dto;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement(name = "players")
@XmlAccessorType(XmlAccessType.FIELD)
public class PlayerWrapperDTO {
    @XmlElement(name = "player")
    private List<PlayerImportDTO> list;

    public PlayerWrapperDTO(List<PlayerImportDTO> list) {
        this.list = list;
    }

    public PlayerWrapperDTO() {
    }

    public List<PlayerImportDTO> getList() {
        return list;
    }
}
