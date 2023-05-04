package org.example.controller;

import org.example.services.CompanyService;
import org.example.services.EmployeeService;
import org.example.services.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.xml.bind.JAXBException;
import java.io.FileNotFoundException;
import java.io.IOException;

@Controller
public class ImportController {
    private final CompanyService companyService;
    private final ProjectService projectService;
    private final EmployeeService employeeService;

    @Autowired
    public ImportController(CompanyService companyService, EmployeeService employeeService, ProjectService projectService) {
        this.companyService = companyService;
        this.projectService = projectService;
        this.employeeService = employeeService;
    }

    @GetMapping("/import/xml")
    public ModelAndView index() {
        ModelAndView modelAndView = new ModelAndView("xml/import-xml");
        boolean[] areImported = new boolean[3];

        areImported[0] = companyService.isImported();
        areImported[1] = projectService.isImported();
        areImported[2] = employeeService.isImported();

        modelAndView.addObject("areImported", areImported);
        return modelAndView;
    }

    @GetMapping("/import/companies")
    public ModelAndView showImportCompanies() throws IOException {
        ModelAndView modelAndView = new ModelAndView("xml/import-companies");

        String companies = this.companyService.getCompaniesText();

        modelAndView.addObject("companies", companies);

        return modelAndView;
    }

    @PostMapping("/import/companies")
    public String importCompanies() throws JAXBException, FileNotFoundException {
        String result = this.companyService.importCompanies();

        System.out.println(result);

        return "redirect:/import/xml";
    }

    @GetMapping("/import/projects")
    public ModelAndView showImportProjects() throws IOException {
        ModelAndView modelAndView = new ModelAndView("xml/import-projects");

        String projects = this.projectService.getProjectsText();

        modelAndView.addObject("projects", projects);

        return modelAndView;
    }

    @PostMapping("/import/projects")
    public String importProjects() throws JAXBException, FileNotFoundException {
        String result = this.projectService.importProjects();

        System.out.println(result);

        return "redirect:/import/xml";
    }

    @GetMapping("/import/employees")
    public ModelAndView showImportEmployees() throws IOException {
        ModelAndView modelAndView = new ModelAndView("xml/import-employees");

        String employees = this.employeeService.getEmployeesText();

        modelAndView.addObject("employees", employees);

        return modelAndView;
    }

    @PostMapping("/import/employees")
    public String importEmployees() throws JAXBException, FileNotFoundException {
        String result = this.employeeService.importEmployees();

        System.out.println(result);

        return "redirect:/import/xml";
    }
}
