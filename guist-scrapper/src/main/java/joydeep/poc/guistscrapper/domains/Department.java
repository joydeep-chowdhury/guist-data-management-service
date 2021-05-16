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

    @Override
    public String toString() {
        return "Department{" + "departmentName='" + departmentName + '\'' + ", departmentDetails='" + departmentDetails + '\'' + '}';
    }
}
