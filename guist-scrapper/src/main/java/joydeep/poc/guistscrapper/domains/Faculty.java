package joydeep.poc.guistscrapper.domains;

import java.util.List;

public class Faculty {
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
