package joydeep.poc.guist.guist.orchestrator.domains;


public class Department {
    private String _id;
    private String departmentName;
    private String departmentDetails;

    public Department() {
    }

    public Department(String departmentName, String departmentDetails) {
        this.departmentName = departmentName;
        this.departmentDetails = departmentDetails;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
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
