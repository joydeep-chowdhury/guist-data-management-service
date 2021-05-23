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
@RequestMapping("/departments")
public class DepartmentController {

    private final PersistenceContract<Department> departmentPersistenceContract;

    public DepartmentController(@Qualifier("department") final PersistenceContract<Department> departmentPersistenceContract) {
        this.departmentPersistenceContract = departmentPersistenceContract;
    }

    @GetMapping
    public List<Department> departments() {
        return departmentPersistenceContract.retrieveAll();
    }

    @GetMapping(params = {"pageNo", "pageSize"})
    public List<Department> departmentsByPage(@RequestParam int pageNo, @RequestParam int pageSize) {
        return departmentPersistenceContract.retrieveAllByPage(pageNo, pageSize);
    }
}
