package joydeep.poc.guist.persistor.controllers;

import joydeep.poc.guist.persistor.daos.PersistenceContract;
import joydeep.poc.guist.persistor.domains.Department;
import joydeep.poc.guist.persistor.domains.Faculty;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/faculties")
public class FacultyController {

    private final PersistenceContract<Faculty> facultyPersistenceContract;

    public FacultyController(@Qualifier("faculty") final PersistenceContract<Faculty> facultyPersistenceContract) {
        this.facultyPersistenceContract = facultyPersistenceContract;
    }

    @GetMapping
    public List<Faculty> faculties() {
        return facultyPersistenceContract.retrieveAll();
    }

    @GetMapping(params = {"pageNo", "pageSize"})
    public List<Faculty> facultiesByPage(@RequestParam int pageNo, @RequestParam int pageSize) {
        return facultyPersistenceContract.retrieveAllByPage(pageNo, pageSize);
    }
}

