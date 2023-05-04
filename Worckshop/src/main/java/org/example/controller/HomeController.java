package org.example.controller;

import org.example.services.CompanyService;
import org.example.services.EmployeeService;
import org.example.services.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class HomeController {
    private final CompanyService companyService;
    private final EmployeeService employeeService;
    private final ProjectService projectService;

    @Autowired
    public HomeController(CompanyService companyService, EmployeeService employeeService, ProjectService projectService) {
        this.companyService = companyService;
        this.employeeService = employeeService;
        this.projectService = projectService;
    }

    @GetMapping("/home")
    public ModelAndView index() {
        boolean areImported = companyService.isImported() && employeeService.isImported() && projectService.isImported();

        ModelAndView modelAndView = new ModelAndView("home");
        modelAndView.addObject("title", "Some page title");
        modelAndView.addObject("areImported", areImported);

        return modelAndView;
    }
}
