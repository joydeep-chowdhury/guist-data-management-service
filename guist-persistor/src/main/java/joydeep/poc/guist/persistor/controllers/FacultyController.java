package joydeep.poc.guist.persistor.controllers;

import joydeep.poc.guist.persistor.daos.FacultyDao;
import joydeep.poc.guist.persistor.daos.PersistenceContract;
import joydeep.poc.guist.persistor.domains.Faculty;
import joydeep.poc.guist.persistor.dtos.FacultiesResponseDTO;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/faculties")
public class FacultyController {

    private final PersistenceContract<Faculty> facultyPersistenceContract;

    public FacultyController(@Qualifier("faculty") final PersistenceContract<Faculty> facultyPersistenceContract) {
        this.facultyPersistenceContract = facultyPersistenceContract;
    }

    @GetMapping
    public FacultiesResponseDTO faculties() {
        return new FacultiesResponseDTO(facultyPersistenceContract.retrieveAll());
    }

    @GetMapping(params = {"pageNo", "pageSize"})
    public FacultiesResponseDTO facultiesByPage(@RequestParam int pageNo, @RequestParam int pageSize) {
        return new FacultiesResponseDTO(facultyPersistenceContract.retrieveAllByPage(pageNo, pageSize));
    }

    @GetMapping(params = {"departmentName","designation"})
    public FacultiesResponseDTO facultiesByDepartmentNameAndDesignation(@RequestParam String departmentName,@RequestParam String designation){
        FacultyDao facultyDao= (FacultyDao) facultyPersistenceContract;
        return new FacultiesResponseDTO(facultyDao.retrieveByDepartmentNameAndDesignation(departmentName,designation));
    }

    @GetMapping(params = {"facultyName"})
    public FacultiesResponseDTO facultiesByFacultyName(@RequestParam String facultyName){
        FacultyDao facultyDao= (FacultyDao) facultyPersistenceContract;
        return new FacultiesResponseDTO(facultyDao.retrieveByFacultyName(facultyName));
    }

    @GetMapping(params = {"departmentName"})
    public FacultiesResponseDTO facultiesByDepartmentName(@RequestParam String departmentName){
        FacultyDao facultyDao= (FacultyDao) facultyPersistenceContract;
        return new FacultiesResponseDTO(facultyDao.retrieveByDepartmentName(departmentName));
    }

    @GetMapping(params = {"designation"})
    public FacultiesResponseDTO facultiesByDesignation(@RequestParam String designation){
        FacultyDao facultyDao= (FacultyDao) facultyPersistenceContract;
        return new FacultiesResponseDTO(facultyDao.retrieveByDesignation(designation));
    }
}

