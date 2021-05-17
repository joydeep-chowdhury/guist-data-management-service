package joydeep.poc.guist.persistor.repositories;


import joydeep.poc.guist.persistor.domains.Faculty;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FacultyRepository extends CrudRepository<Faculty, String> {
    List<Faculty> findAll();

    List<Faculty> findByFacultyDetails(List<String> facultyDetails);
}