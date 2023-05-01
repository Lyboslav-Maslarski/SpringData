package example;

import example.dtos.EmployeeDTO;
import example.dtos.EmployeeWithManagerDTO;
import example.dtos.ManagerDTO;
import example.entities.Address;
import example.entities.Employee;
import example.repositories.AddressRepository;
import example.repositories.EmployeeRepository;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDate;

@Component
public class ConsoleRunner implements CommandLineRunner {
    @Autowired
    private EmployeeRepository employeeRepository;
    @Autowired
    private AddressRepository addressRepository;

    @Override
    public void run(String... args) {
        Address address = new Address();
        address.setCity("Sofia");
        address.setCountry("Bulgaria");
        addressRepository.save(address);

        Employee employee = new Employee();
        employee.setFirstName("Pesho");
        employee.setLastName("Peshev");
        employee.setSalary(BigDecimal.valueOf(1500));
        employee.setBirthday(LocalDate.parse("1980-01-01"));
        employee.setAddress(address);
        employee.setOnHoliday(false);

        Employee employee2 = new Employee();
        employee2.setFirstName("Tosho");
        employee2.setLastName("Toshev");
        employee2.setSalary(BigDecimal.valueOf(2000));
        employee2.setBirthday(LocalDate.parse("1987-01-01"));
        employee2.setAddress(address);
        employee2.setOnHoliday(true);

        Employee employee3 = new Employee();
        employee3.setFirstName("Koko");
        employee3.setLastName("Koshev");
        employee3.setSalary(BigDecimal.valueOf(1999.99));
        employee3.setBirthday(LocalDate.parse("1968-01-01"));
        employee3.setAddress(address);
        employee3.setOnHoliday(false);

        Employee manager = new Employee();
        manager.setFirstName("Gosho");
        manager.setLastName("Geshev");
        manager.setSalary(BigDecimal.valueOf(4000));
        manager.setBirthday(LocalDate.parse("1960-01-01"));
        manager.setAddress(address);
        manager.setOnHoliday(false);

        manager.getEmployees().add(employee);
        manager.getEmployees().add(employee2);
        manager.getEmployees().add(employee3);
        employee.setManager(manager);
        employee2.setManager(manager);
        employee3.setManager(manager);

        this.employeeRepository.save(manager);

        employee = this.employeeRepository.findById(3).orElse(null);
        ModelMapper mapper = new ModelMapper();
        EmployeeDTO employeeDto = mapper.map(employee, EmployeeDTO.class);
        System.out.println(employeeDto);

        employee = this.employeeRepository.findById(1).orElse(null);
        ManagerDTO managerDto = mapper.map(employee, ManagerDTO.class);
        System.out.println(managerDto);

        TypeMap<Employee, EmployeeWithManagerDTO> typeMap = mapper.createTypeMap(Employee.class, EmployeeWithManagerDTO.class);
        typeMap.addMappings(m -> m.map(src -> src.getManager().getLastName(), EmployeeWithManagerDTO::setManagerLastName));
        this.employeeRepository
                .findAllByBirthdayBeforeOrderBySalaryDesc(LocalDate.parse("1990-01-01"))
                .stream()
                .map(typeMap::map)
                .forEach(System.out::println);
    }
}
