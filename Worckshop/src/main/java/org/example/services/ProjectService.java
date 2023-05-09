package org.example.services;

import org.example.models.dtos.ProjectWrapperDTO;
import org.example.models.entities.Company;
import org.example.models.entities.Project;
import org.example.repositories.CompanyRepository;
import org.example.repositories.ProjectRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.stream.Collectors;

@Service
public class ProjectService {
    public static final String PROJECTS_XML = "src/main/resources/files/xmls/projects.xml";
    private final ProjectRepository projectRepository;
    private final CompanyRepository companyRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public ProjectService(ProjectRepository projectRepository, CompanyRepository companyRepository,
                          @Qualifier("forObjectsWithDates") ModelMapper modelMapper) {
        this.projectRepository = projectRepository;
        this.companyRepository = companyRepository;
        this.modelMapper = modelMapper;
    }

    public boolean isImported() {
        return this.projectRepository.count() > 0;
    }

    public String getProjectsText() throws IOException {
        return Files.readString(Path.of(PROJECTS_XML));
    }

    public String importProjects() throws JAXBException, FileNotFoundException {
        JAXBContext context = JAXBContext.newInstance(ProjectWrapperDTO.class);
        Unmarshaller unmarshaller = context.createUnmarshaller();

        ProjectWrapperDTO dto = (ProjectWrapperDTO) unmarshaller.unmarshal(new FileReader(PROJECTS_XML));

        return dto.getProjects().stream()
                .map(projectImportDTO -> {
                    Project project = modelMapper.map(projectImportDTO, Project.class);

                    Company company = companyRepository.findByName(projectImportDTO.getCompany().getName()).get();
                    project.setCompany(company);

                    projectRepository.save(project);
                    return "Created Project " + project.getName();
                }).collect(Collectors.joining(System.lineSeparator()));
    }

    public String getFinishedProjects() {
        return this.projectRepository
                .findByIsFinishedTrue().stream()
                .map(Project::toString)
                .collect(Collectors.joining(System.lineSeparator()));
    }
}
