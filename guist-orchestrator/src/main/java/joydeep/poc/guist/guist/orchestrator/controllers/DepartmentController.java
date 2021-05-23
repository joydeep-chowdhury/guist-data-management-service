package joydeep.poc.guist.guist.orchestrator.controllers;


import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import joydeep.poc.guist.guist.orchestrator.domains.Department;
import joydeep.poc.guist.guist.orchestrator.domains.Faculty;
import joydeep.poc.guist.guist.orchestrator.services.PersistorClientService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Api(value = "GUIST Department Controller")
@RestController
@RequestMapping("/departments")
public class DepartmentController {

    private PersistorClientService<Department> departmentPersistorClientService;

    public DepartmentController(@Qualifier("department") PersistorClientService<Department> departmentPersistorClientService) {
        this.departmentPersistorClientService = departmentPersistorClientService;
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
}
