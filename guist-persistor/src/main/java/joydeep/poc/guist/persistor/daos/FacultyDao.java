package joydeep.poc.guist.persistor.daos;

import joydeep.poc.guist.persistor.domains.Department;
import joydeep.poc.guist.persistor.domains.Faculty;
import joydeep.poc.guist.persistor.repositories.DepartmentRepository;
import joydeep.poc.guist.persistor.repositories.FacultyRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.stream.Collectors;

@Repository
@Qualifier("faculty")
public class FacultyDao implements PersistenceContract<Faculty> {

    private static final Logger logger = LoggerFactory.getLogger(FacultyDao.class);
    private final FacultyRepository facultyRepository;

    public FacultyDao(final FacultyRepository facultyRepository) {
        this.facultyRepository = facultyRepository;
    }


    @Override
    public void persist(Faculty faculty) {
        List<Faculty> facultyList = facultyRepository.findByFacultyDetails(faculty.getFacultyDetails());
        if (facultyList.size() > 0) {
            facultyRepository.deleteAll(facultyList);
            logger.info("Deleted identical faculties before persisting {}", facultyList);
        }
        facultyRepository.save(faculty);
        logger.info("Persisted {}", faculty);
    }

    @Override
    public List<Faculty> retrieveAll() {
        return facultyRepository.findAll();
    }

    @Override
    public List<Faculty> retrieveAllByPage(int pageNo, int pageSize) {
        return facultyRepository.findAll(PageRequest.of(pageNo, pageSize));
    }

    public List<Faculty> retrieveByDepartmentNameAndDesignation(String departmentName, String designation) {
        return facultyRepository.findByDepartmentNameAndDesignation(departmentName, designation);
    }


    public List<Faculty> retrieveByFacultyName(String facultyName) {
        return facultyRepository.findAll().stream().filter(faculty -> {
            List<String> facultyDetails = faculty.getFacultyDetails();
            List<String> filteredDetails = facultyDetails.stream().filter(str -> str.contains(facultyName)).collect(Collectors.toList());
            if (filteredDetails.size() == 0) {
                return false;
            } else {
                return true;
            }
        }).collect(Collectors.toList());
    }

    public List<Faculty> retrieveByDepartmentName(String departmentName) {
        return facultyRepository.findByDepartmentName(departmentName);
    }

    public List<Faculty> retrieveByDesignation(String designation) {
        return facultyRepository.findByDesignation(designation);
    }
}
