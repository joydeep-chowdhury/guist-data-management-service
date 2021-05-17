package joydeep.poc.guist.persistor.controllers;

import joydeep.poc.guist.persistor.daos.PersistenceContract;
import joydeep.poc.guist.persistor.domains.Department;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
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
}
