package joydeep.poc.guist.guist.orchestrator.domains;

import java.util.List;

public class DepartmentsResponseDTO {

    private List<Department> departmentList;

    public DepartmentsResponseDTO() {
    }

    public DepartmentsResponseDTO(List<Department> departmentList) {
        this.departmentList = departmentList;
    }

    public List<Department> getDepartmentList() {
        return departmentList;
    }

    public void setDepartmentList(List<Department> departmentList) {
        this.departmentList = departmentList;
    }
}
