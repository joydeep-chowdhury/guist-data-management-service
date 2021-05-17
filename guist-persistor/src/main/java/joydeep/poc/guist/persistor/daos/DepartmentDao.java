package joydeep.poc.guist.persistor.daos;

import joydeep.poc.guist.persistor.consumers.KafkaConsumer;
import joydeep.poc.guist.persistor.domains.Department;
import joydeep.poc.guist.persistor.repositories.DepartmentRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Qualifier("department")
public class DepartmentDao implements PersistenceContract<Department> {
    private static final Logger logger= LoggerFactory.getLogger(DepartmentDao.class);
    private final DepartmentRepository departmentRepository;

    public DepartmentDao(final DepartmentRepository departmentRepository) {
        this.departmentRepository = departmentRepository;
    }

    @Override
    public void persist(Department department) {
        List<Department> departmentList = departmentRepository.findByDepartmentName(department.getDepartmentName());
        if (departmentList.size() > 0) {
            departmentRepository.deleteAll(departmentList);
            logger.info("Deleted identical departments before persisting {}",departmentList);
        }
        departmentRepository.save(department);
        logger.info("Persisted {}",department);
    }

    @Override
    public List<Department> retrieveAll() {
        return departmentRepository.findAll();
    }
}
