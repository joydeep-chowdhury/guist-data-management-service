package joydeep.poc.guist.guist.orchestrator.domains;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;


public class Faculty {

    private String _id;
    private List<String> facultyDetails;
    private String departmentName;
    private String designation;

    public Faculty() {
    }

    public Faculty(List<String> facultyDetails, String departmentName, String designation) {
        this.facultyDetails = facultyDetails;
        this.departmentName = departmentName;
        this.designation = designation;
    }


    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public List<String> getFacultyDetails() {
        return facultyDetails;
    }

    public void setFacultyDetails(List<String> facultyDetails) {
        this.facultyDetails = facultyDetails;
    }

    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }

    public String getDesignation() {
        return designation;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }

    @Override
    public String toString() {
        return "Faculty{" + "facultyDetails=" + facultyDetails + ", departmentName='" + departmentName + '\'' + ", designation='" + designation + '\'' + '}';
    }
}
