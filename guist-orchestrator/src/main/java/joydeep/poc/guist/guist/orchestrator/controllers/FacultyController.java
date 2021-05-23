package joydeep.poc.guist.guist.orchestrator.controllers;


import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import joydeep.poc.guist.guist.orchestrator.domains.Faculty;
import joydeep.poc.guist.guist.orchestrator.services.FacultyClientService;
import joydeep.poc.guist.guist.orchestrator.services.PersistorClientService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Api(value = "GUIST Faculty Controller")
@RestController
@RequestMapping("/faculties")
public class FacultyController {

    private PersistorClientService<Faculty> facultyPersistorClientService;

    public FacultyController(@Qualifier("faculty") PersistorClientService<Faculty> facultyPersistorClientService) {
        this.facultyPersistorClientService = facultyPersistorClientService;
    }

    @ApiOperation(value = "Gets the list of faculties")
    @GetMapping
    public List<Faculty> faculties() {
        return facultyPersistorClientService.fetchAll();
    }

    @ApiOperation(value = "Gets the list of faculties in paginated format")
    @GetMapping(params = {"pageNo", "pageSize"})
    public List<Faculty> facultiesByPage(@RequestParam int pageNo, @RequestParam int pageSize) {
        return facultyPersistorClientService.fetchByPage(pageNo, pageSize);
    }
}

