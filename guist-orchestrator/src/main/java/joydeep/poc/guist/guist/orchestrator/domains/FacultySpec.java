package joydeep.poc.guist.guist.orchestrator.domains;

public enum FacultySpec {
    FACULTY_DETAILS("Faculty Details"),
    DEPARTMENT_NAME("Department Name"),
    DESIGNATION("Designation");

    FacultySpec(String attribute){
        this.attribute=attribute;
    }
    private String attribute;

    @Override
    public String toString() {
        return attribute;
    }
}
