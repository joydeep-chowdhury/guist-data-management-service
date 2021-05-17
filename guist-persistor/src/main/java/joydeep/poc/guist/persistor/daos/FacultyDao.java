package joydeep.poc.guist.persistor.daos;

import joydeep.poc.guist.persistor.domains.Department;
import joydeep.poc.guist.persistor.domains.Faculty;
import joydeep.poc.guist.persistor.repositories.DepartmentRepository;
import joydeep.poc.guist.persistor.repositories.FacultyRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Qualifier("faculty")
public class FacultyDao implements PersistenceContract<Faculty> {

    private static final Logger logger= LoggerFactory.getLogger(FacultyDao.class);
    private final FacultyRepository facultyRepository;

    public FacultyDao(final FacultyRepository facultyRepository) {
        this.facultyRepository = facultyRepository;
    }


    @Override
    public void persist(Faculty faculty) {
        List<Faculty> facultyList = facultyRepository.findByFacultyDetails(faculty.getFacultyDetails());
        if (facultyList.size() > 0) {
            facultyRepository.deleteAll(facultyList);
            logger.info("Deleted identical faculties before persisting {}",facultyList);
        }
        facultyRepository.save(faculty);
        logger.info("Persisted {}",faculty);
    }

    @Override
    public List<Faculty> retrieveAll() {
        return facultyRepository.findAll();
    }
}
