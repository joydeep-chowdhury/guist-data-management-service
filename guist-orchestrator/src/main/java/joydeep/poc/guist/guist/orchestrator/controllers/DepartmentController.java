package joydeep.poc.guist.guist.orchestrator.controllers;


import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import joydeep.poc.guist.guist.orchestrator.domains.Department;
import joydeep.poc.guist.guist.orchestrator.domains.Faculty;
import joydeep.poc.guist.guist.orchestrator.services.DepartmentClientService;
import joydeep.poc.guist.guist.orchestrator.services.DepartmentReportService;
import joydeep.poc.guist.guist.orchestrator.services.PersistorClientService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@Api(value = "GUIST Department Controller")
@RestController
@RequestMapping("/departments")
public class DepartmentController {

    private PersistorClientService<Department> departmentPersistorClientService;
    private DepartmentReportService departmentReportService;

    public DepartmentController(@Qualifier("department") PersistorClientService<Department> departmentPersistorClientService, DepartmentReportService departmentReportService) {
        this.departmentPersistorClientService = departmentPersistorClientService;
        this.departmentReportService = departmentReportService;
    }

    @ApiOperation(value = "Gets the list of departments")
    @GetMapping
    public List<Department> departments() {
        return departmentPersistorClientService.fetchAll();
    }

    @ApiOperation(value = "Gets the list of departments of particular page")
    @GetMapping(params = {"pageNo", "pageSize"})
    public List<Department> departmentsByPage(@RequestParam int pageNo, @RequestParam int pageSize) {
        return departmentPersistorClientService.fetchByPage(pageNo, pageSize);
    }

    @GetMapping(params = {"departmentName"})
    public List<Department> departmentsByDepartmentName(@RequestParam String departmentName) {
        DepartmentClientService departmentClientService = (DepartmentClientService) departmentPersistorClientService;
        return departmentClientService.retrieveByDepartmentName(departmentName);
    }


    @GetMapping(value = "/report")
    public void departmentsReport(HttpServletResponse servletResponse) throws IOException {
        departmentReportService.fetchAll(servletResponse);
    }


    @GetMapping(value = "/report", params = {"pageNo", "pageSize"})
    public void departmentsByPageReport(@RequestParam int pageNo, @RequestParam int pageSize, HttpServletResponse servletResponse) throws IOException {
        departmentReportService.fetchByPage(pageNo, pageSize, servletResponse);
    }

    @GetMapping(value = "/report", params = {"departmentName"})
    public void departmentsByDepartmentNameReport(@RequestParam String departmentName, HttpServletResponse servletResponse) throws IOException {
        departmentReportService.retrieveByDepartmentName(departmentName, servletResponse);
    }
}
