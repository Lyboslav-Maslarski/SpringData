package org.example.controller;

import com.google.gson.Gson;
import org.example.models.dtos.EmployeeExportDTO;
import org.example.services.EmployeeService;
import org.example.services.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
public class ExportController {

    private final ProjectService projectService;
    private final EmployeeService employeeService;
    private final Gson gson;

    @Autowired
    public ExportController(ProjectService projectService, EmployeeService employeeService, Gson gson) {
        this.projectService = projectService;
        this.employeeService = employeeService;
        this.gson = gson;
    }

    @GetMapping("/export/project-if-finished")
    public ModelAndView showFinishedProjects() {
        ModelAndView modelAndView = new ModelAndView("export/export-project-if-finished");

        String result = this.projectService.getFinishedProjects();

        modelAndView.addObject("projectsIfFinished", result);

        return modelAndView;
    }

    @GetMapping("/export/employees-above")
    private ModelAndView showEmployeesAbove() {
        ModelAndView modelAndView = new ModelAndView("export/export-employees-with-age");

        List<EmployeeExportDTO> employees = this.employeeService.getEmployeesAbove();

        StringBuilder sb = new StringBuilder();
        this.gson.toJson(employees, sb);

        modelAndView.addObject("employeesAbove", sb.toString());
        return modelAndView;
    }
}
