package joydeep.poc.guist.persistor.dtos;

import joydeep.poc.guist.persistor.domains.Faculty;

import java.util.List;

public class FacultiesResponseDTO {

    private List<Faculty> facultyList;

    public FacultiesResponseDTO() {
    }

    public FacultiesResponseDTO(List<Faculty> facultyList) {
        this.facultyList = facultyList;
    }

    public List<Faculty> getFacultyList() {
        return facultyList;
    }

    public void setFacultyList(List<Faculty> facultyList) {
        this.facultyList = facultyList;
    }
}
