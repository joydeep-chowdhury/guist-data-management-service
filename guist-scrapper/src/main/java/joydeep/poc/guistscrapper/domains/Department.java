package joydeep.poc.guistscrapper.domains;

public class Department {

    private String departmentName;
    private String departmentDetails;

    public Department() {
    }

    public Department(String departmentName, String departmentDetails) {
        this.departmentName = departmentName;
        this.departmentDetails = departmentDetails;
    }

    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }

    public String getDepartmentDetails() {
        return departmentDetails;
    }

    public void setDepartmentDetails(String departmentDetails) {
        this.departmentDetails = departmentDetails;
    }

    @Override
    public String toString() {
        return "Department{" + "departmentName='" + departmentName + '\'' + ", departmentDetails='" + departmentDetails + '\'' + '}';
    }
}
