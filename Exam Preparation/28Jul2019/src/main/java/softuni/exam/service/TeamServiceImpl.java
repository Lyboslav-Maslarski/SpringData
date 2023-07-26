package softuni.exam.service;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import softuni.exam.domain.dtos.TeamImportDTO;
import softuni.exam.domain.dtos.TeamsWrapperDTO;
import softuni.exam.domain.entities.Picture;
import softuni.exam.domain.entities.Team;
import softuni.exam.repository.PictureRepository;
import softuni.exam.repository.TeamRepository;
import softuni.exam.util.FileUtil;
import softuni.exam.util.ValidatorUtil;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.File;
import java.io.IOException;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TeamServiceImpl implements TeamService {

    private final TeamRepository teamRepository;
    private final PictureRepository pictureRepository;
    private final FileUtil fileUtil;
    private final ValidatorUtil validatorUtil;
    private final ModelMapper modelMapper;

    @Autowired
    public TeamServiceImpl(TeamRepository teamRepository, PictureRepository pictureRepository,
                           FileUtil fileUtil, ValidatorUtil validatorUtil, ModelMapper modelMapper) {
        this.teamRepository = teamRepository;
        this.pictureRepository = pictureRepository;
        this.fileUtil = fileUtil;
        this.validatorUtil = validatorUtil;
        this.modelMapper = modelMapper;
    }

    @Override
    public String importTeams() throws JAXBException {
        JAXBContext context = JAXBContext.newInstance(TeamsWrapperDTO.class);
        Unmarshaller unmarshaller = context.createUnmarshaller();
        TeamsWrapperDTO teamsWrapperDTO =
                (TeamsWrapperDTO) unmarshaller.unmarshal(new File("src/main/resources/files/xml/teams.xml"));

        return teamsWrapperDTO
                .getTeams()
                .stream()
                .map(this::importTeam)
                .collect(Collectors.joining(System.lineSeparator()));
    }

    private String importTeam(TeamImportDTO teamImportDTO) {
        if (!validatorUtil.isValid(teamImportDTO)) {
            return "Invalid team";
        }

        Optional<Picture> picture = pictureRepository.findByUrl(teamImportDTO.getPicture().getUrl());
        if (picture.isEmpty()) {
            return "Invalid team";
        }

        Team team = modelMapper.map(teamImportDTO, Team.class);
        team.setPicture(picture.get());
        teamRepository.save(team);

        return "Successfully imported - " + team.getName();
    }

    @Override
    public boolean areImported() {
        return teamRepository.count() > 0;
    }

    @Override
    public String readTeamsXmlFile() throws IOException {
        return fileUtil.readFile("src/main/resources/files/xml/teams.xml");
    }
}
