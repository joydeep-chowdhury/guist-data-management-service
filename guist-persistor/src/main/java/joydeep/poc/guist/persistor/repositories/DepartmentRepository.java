package joydeep.poc.guist.persistor.repositories;

import joydeep.poc.guist.persistor.domains.Department;
import joydeep.poc.guist.persistor.domains.Faculty;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DepartmentRepository extends CrudRepository<Department, String> {
    List<Department> findAll();

    List<Department> findByDepartmentName(String departmentName);

    List<Department> findAll(Pageable pageable);
}
