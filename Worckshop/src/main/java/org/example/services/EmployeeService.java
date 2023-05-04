package org.example.services;

import org.example.models.dtos.EmployeeExportDTO;
import org.example.models.dtos.EmployeeWrapperDTO;
import org.example.models.entities.Employee;
import org.example.models.entities.Project;
import org.example.repositories.EmployeeRepository;
import org.example.repositories.ProjectRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class EmployeeService {
    public static final String EMPLOYEES_XML = "src/main/resources/files/xmls/employees.xml";
    private final EmployeeRepository employeeRepository;
    private final ProjectRepository projectRepository;
    private final ModelMapper modelMapper;

    public EmployeeService(EmployeeRepository employeeRepository, ProjectRepository projectRepository, ModelMapper modelMapper) {
        this.employeeRepository = employeeRepository;
        this.projectRepository = projectRepository;
        this.modelMapper = modelMapper;
    }

    public boolean isImported() {
        return employeeRepository.count() > 0;
    }

    public String getEmployeesText() throws IOException {
        return Files.readString(Path.of(EMPLOYEES_XML));
    }

    public String importEmployees() throws JAXBException, FileNotFoundException {
        JAXBContext context = JAXBContext.newInstance(EmployeeWrapperDTO.class);
        Unmarshaller unmarshaller = context.createUnmarshaller();

        EmployeeWrapperDTO dto = (EmployeeWrapperDTO) unmarshaller.unmarshal(new FileReader(EMPLOYEES_XML));

        return dto.getEmployees().stream()
                .map(employeeImportDTO -> {
                    Employee employee = modelMapper.map(employeeImportDTO, Employee.class);

                    Project project = projectRepository.findByName(employeeImportDTO.getProject().getName()).get();
                    employee.setProject(project);

                    employeeRepository.save(employee);
                    return "Created Employee " + employee.getFirstName() + " " + employee.getLastName();
                }).collect(Collectors.joining(System.lineSeparator()));
    }

    public List<EmployeeExportDTO> getEmployeesAbove() {
        List<Employee> employees = this.employeeRepository.findByAgeGreaterThanOrderByProjectNameAsc(25);

        return employees.stream()
                .map(EmployeeExportDTO::new)
                .collect(Collectors.toList());
    }
}
