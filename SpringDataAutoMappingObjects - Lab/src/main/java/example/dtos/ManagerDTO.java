package example.dtos;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

public class ManagerDTO {
    private String firstName;
    private String lastName;
    private Set<EmployeeDTO> employees;

    public ManagerDTO() {
        this.employees = new HashSet<>();
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Set<EmployeeDTO> getEmployees() {
        return employees;
    }

    public void setEmployees(Set<EmployeeDTO> employees) {
        this.employees = employees;
    }

    @Override
    public String toString() {
        return String.format("%s %s | Employees: %d%n%s",
                this.firstName, this.lastName, this.employees.size(),
                this.employees
                        .stream()
                        .map(employee -> "    " + employee)
                        .collect(Collectors.joining(System.lineSeparator())));
    }
}
