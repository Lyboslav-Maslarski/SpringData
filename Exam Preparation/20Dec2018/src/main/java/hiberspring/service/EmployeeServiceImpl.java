package hiberspring.service;

import hiberspring.domain.dtos.EmployeeImportDTO;
import hiberspring.domain.dtos.EmployeesWrapperDTO;
import hiberspring.domain.entities.Branch;
import hiberspring.domain.entities.Employee;
import hiberspring.domain.entities.EmployeeCard;
import hiberspring.repository.BranchRepository;
import hiberspring.repository.EmployeeCardRepository;
import hiberspring.repository.EmployeeRepository;
import hiberspring.util.FileUtil;
import hiberspring.util.ValidationUtil;
import hiberspring.util.XmlParser;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.xml.bind.JAXBException;
import java.io.IOException;
import java.util.Comparator;
import java.util.Optional;
import java.util.stream.Collectors;

import static hiberspring.common.Constants.*;

@Service
public class EmployeeServiceImpl implements EmployeeService {
    private final EmployeeRepository employeeRepository;
    private final BranchRepository branchRepository;
    private final EmployeeCardRepository employeeCardRepository;
    private final FileUtil fileUtil;
    private final XmlParser xmlParser;
    private final ModelMapper modelMapper;
    private final ValidationUtil validationUtil;

    @Autowired
    public EmployeeServiceImpl(EmployeeRepository employeeRepository, BranchRepository branchRepository,
                               EmployeeCardRepository employeeCardRepository, FileUtil fileUtil,
                               XmlParser xmlParser, ModelMapper modelMapper, ValidationUtil validationUtil) {
        this.employeeRepository = employeeRepository;
        this.branchRepository = branchRepository;
        this.employeeCardRepository = employeeCardRepository;
        this.fileUtil = fileUtil;
        this.xmlParser = xmlParser;
        this.modelMapper = modelMapper;
        this.validationUtil = validationUtil;
    }

    @Override
    public Boolean employeesAreImported() {
        return employeeRepository.count() > 0;
    }

    @Override
    public String readEmployeesXmlFile() throws IOException {
        return fileUtil.readFile(PATH_TO_FILES + "employees.xml");
    }

    @Override
    public String importEmployees() throws JAXBException {
        EmployeesWrapperDTO employeesWrapperDTO = xmlParser.parseXml(EmployeesWrapperDTO.class, PATH_TO_FILES + "employees.xml");

        return employeesWrapperDTO
                .getEmployees()
                .stream()
                .map(this::importEmployee)
                .collect(Collectors.joining(System.lineSeparator()));
    }

    private String importEmployee(EmployeeImportDTO employeeImportDTO) {
        if (!validationUtil.isValid(employeeImportDTO)) {
            return INCORRECT_DATA_MESSAGE;
        }

        Employee employee = modelMapper.map(employeeImportDTO, Employee.class);

        Optional<Branch> branch = branchRepository.findByName(employeeImportDTO.getBranch());
        if (branch.isEmpty()) {
            return INCORRECT_DATA_MESSAGE;
        }

        Optional<EmployeeCard> employeeCard = employeeCardRepository.findByNumber(employeeImportDTO.getCard());
        if (employeeCard.isEmpty()) {
            return INCORRECT_DATA_MESSAGE;
        }

        if (employeeRepository.findByCardNumber(employeeImportDTO.getCard()).isPresent()) {
            return INCORRECT_DATA_MESSAGE;
        }

        employee.setCard(employeeCard.get());
        employee.setBranch(branch.get());
        employeeRepository.save(employee);

        return String.format(SUCCESSFUL_IMPORT_MESSAGE, employee.getClass().getSimpleName(), employee.getFullName());
    }

    @Override
    public String exportProductiveEmployees() {
        return employeeRepository.findAllWorkingInBranchWithAtLeastOneProduct()
                .stream()
                .sorted(Comparator.comparing(Employee::getFullName).thenComparingInt(e -> e.getPosition().length()))
                .map(Employee::toString)
                .collect(Collectors.joining("-------------------------" + System.lineSeparator()));
    }
}
