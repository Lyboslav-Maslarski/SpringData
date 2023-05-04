package org.example.models.dtos;

import org.example.models.entities.Employee;

public class EmployeeExportDTO {
    private String fullName;
    private int age;
    private String projectName;

    public EmployeeExportDTO() {
    }

    public EmployeeExportDTO(Employee employee) {
        this.fullName = employee.getFirstName() + " " + employee.getLastName();
        this.age = employee.getAge();
        this.projectName = employee.getProject().getName();
    }

    public String getFullName() {
        return fullName;
    }

    public int getAge() {
        return age;
    }

    public String getProjectName() {
        return projectName;
    }
}
