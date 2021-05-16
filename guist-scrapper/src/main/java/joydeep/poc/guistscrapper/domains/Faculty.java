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

    public String getDepartmentName() {
        return departmentName;
    }

    @Override
    public String toString() {
        return "Faculty{" + "facultyDetails=" + facultyDetails + ", departmentName='" + departmentName + '\'' + ", designation='" + designation + '\'' + '}';
    }
}
