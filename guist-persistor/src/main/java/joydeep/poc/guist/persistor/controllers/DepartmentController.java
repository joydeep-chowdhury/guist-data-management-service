package joydeep.poc.guist.persistor.controllers;

import joydeep.poc.guist.persistor.daos.DepartmentDao;
import joydeep.poc.guist.persistor.daos.PersistenceContract;
import joydeep.poc.guist.persistor.domains.Department;
import joydeep.poc.guist.persistor.dtos.DepartmentsResponseDTO;
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
    public DepartmentsResponseDTO departments() {
        return new DepartmentsResponseDTO(departmentPersistenceContract.retrieveAll());
    }

    @GetMapping(params = {"pageNo", "pageSize"})
    public DepartmentsResponseDTO departmentsByPage(@RequestParam int pageNo, @RequestParam int pageSize) {
        return new DepartmentsResponseDTO(departmentPersistenceContract.retrieveAllByPage(pageNo, pageSize));
    }

    @GetMapping(params = {"departmentName"})
    public DepartmentsResponseDTO departmentsByDepartmentName(@RequestParam String departmentName) {
        DepartmentDao departmentDao = (DepartmentDao) departmentPersistenceContract;
        return new DepartmentsResponseDTO(departmentDao.retrieveByDepartmentName(departmentName));
    }
}
