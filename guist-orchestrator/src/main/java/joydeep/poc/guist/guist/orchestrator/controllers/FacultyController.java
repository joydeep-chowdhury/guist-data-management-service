package joydeep.poc.guist.guist.orchestrator.controllers;


import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import joydeep.poc.guist.guist.orchestrator.domains.Faculty;
import joydeep.poc.guist.guist.orchestrator.services.FacultyClientService;
import joydeep.poc.guist.guist.orchestrator.services.FacultyReportService;
import joydeep.poc.guist.guist.orchestrator.services.PersistorClientService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@Api(value = "GUIST Faculty Controller")
@RestController
@RequestMapping("/faculties")
public class FacultyController {

    private PersistorClientService<Faculty> facultyPersistorClientService;
    private FacultyReportService facultyReportService;

    public FacultyController(@Qualifier("faculty") PersistorClientService<Faculty> facultyPersistorClientService, FacultyReportService facultyReportService) {
        this.facultyPersistorClientService = facultyPersistorClientService;
        this.facultyReportService = facultyReportService;
    }


    @GetMapping
    public List<Faculty> faculties() {
        return facultyPersistorClientService.fetchAll();
    }


    @GetMapping(params = {"pageNo", "pageSize"})
    public List<Faculty> facultiesByPage(@RequestParam int pageNo, @RequestParam int pageSize) {
        return facultyPersistorClientService.fetchByPage(pageNo, pageSize);
    }

    @GetMapping(params = {"departmentName", "designation"})
    public List<Faculty> facultiesByDepartmentNameAndDesignation(@RequestParam String departmentName, @RequestParam String designation) {
        FacultyClientService facultyClientService = (FacultyClientService) facultyPersistorClientService;
        return facultyClientService.retrieveByDepartmentNameAndDesignation(departmentName, designation);
    }

    @GetMapping(params = {"facultyName"})
    public List<Faculty> facultiesByFacultyName(@RequestParam String facultyName) {
        FacultyClientService facultyClientService = (FacultyClientService) facultyPersistorClientService;
        return facultyClientService.retrieveByFacultyName(facultyName);
    }

    @GetMapping(params = {"departmentName"})
    public List<Faculty> facultiesByDepartmentName(@RequestParam String departmentName) {
        FacultyClientService facultyClientService = (FacultyClientService) facultyPersistorClientService;
        return facultyClientService.retrieveByDepartmentName(departmentName);
    }

    @GetMapping(params = {"designation"})
    public List<Faculty> facultiesByDesignation(@RequestParam String designation) {
        FacultyClientService facultyClientService = (FacultyClientService) facultyPersistorClientService;
        return facultyClientService.retrieveByDesignation(designation);
    }


    @GetMapping("/report")
    public void facultiesReport(HttpServletResponse servletResponse) throws IOException {
        facultyReportService.fetchAll(servletResponse);
    }


    @GetMapping(value = "/report", params = {"pageNo", "pageSize"})
    public void facultiesByPageReport(@RequestParam int pageNo, @RequestParam int pageSize, HttpServletResponse servletResponse) throws IOException {
        facultyReportService.fetchByPage(pageNo, pageSize, servletResponse);
    }

    @GetMapping(value = "/report", params = {"departmentName", "designation"})
    public void facultiesByDepartmentNameAndDesignationReport(@RequestParam String departmentName, @RequestParam String designation, HttpServletResponse servletResponse) throws IOException {
        facultyReportService.retrieveByDepartmentNameAndDesignation(departmentName, designation, servletResponse);
    }

    @GetMapping(value = "/report", params = {"facultyName"})
    public void facultiesByFacultyNameReport(@RequestParam String facultyName, HttpServletResponse servletResponse) throws IOException {
        facultyReportService.retrieveByFacultyName(facultyName, servletResponse);
    }

    @GetMapping(value = "/report", params = {"departmentName"})
    public void facultiesByDepartmentNameReport(@RequestParam String departmentName, HttpServletResponse servletResponse) throws IOException {
        facultyReportService.retrieveByDepartmentName(departmentName, servletResponse);
    }

    @GetMapping(value = "/report", params = {"designation"})
    public void facultiesByDesignationReport(@RequestParam String designation, HttpServletResponse servletResponse) throws IOException {
        facultyReportService.retrieveByDesignation(designation, servletResponse);
    }


}

