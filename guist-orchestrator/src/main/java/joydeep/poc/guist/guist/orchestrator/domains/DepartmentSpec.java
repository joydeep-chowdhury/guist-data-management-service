package joydeep.poc.guist.guist.orchestrator.domains;

public enum DepartmentSpec {

    DEPARTMENT_NAME("Department Name"),
    DEPARTMENT_DETAILS("Department Details");


    DepartmentSpec(String attribute) {
        this.attribute = attribute;
    }

    String attribute;

    @Override
    public String toString() {
        return attribute;
    }
}
