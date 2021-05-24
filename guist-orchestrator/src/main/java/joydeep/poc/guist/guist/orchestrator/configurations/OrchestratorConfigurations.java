package joydeep.poc.guist.guist.orchestrator.configurations;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "orchestrator")
public class OrchestratorConfigurations {

    private String persistorUrl;
    private String facultiesSubresource;
    private String departmentsSubresource;
    private String pageNoString;
    private String pageSizeString;
    private String departmentNameString;
    private String designationString;
    private String facultyName;

    public String getPersistorUrl() {
        return persistorUrl;
    }

    public void setPersistorUrl(String persistorUrl) {
        this.persistorUrl = persistorUrl;
    }

    public String getFacultiesSubresource() {
        return facultiesSubresource;
    }

    public void setFacultiesSubresource(String facultiesSubresource) {
        this.facultiesSubresource = facultiesSubresource;
    }

    public String getDepartmentsSubresource() {
        return departmentsSubresource;
    }

    public void setDepartmentsSubresource(String departmentsSubresource) {
        this.departmentsSubresource = departmentsSubresource;
    }

    public String getPageNoString() {
        return pageNoString;
    }

    public void setPageNoString(String pageNoString) {
        this.pageNoString = pageNoString;
    }

    public String getPageSizeString() {
        return pageSizeString;
    }

    public void setPageSizeString(String pageSizeString) {
        this.pageSizeString = pageSizeString;
    }

    public String getDepartmentNameString() {
        return departmentNameString;
    }

    public void setDepartmentNameString(String departmentNameString) {
        this.departmentNameString = departmentNameString;
    }

    public String getDesignationString() {
        return designationString;
    }

    public void setDesignationString(String designationString) {
        this.designationString = designationString;
    }

    public String getFacultyName() {
        return facultyName;
    }

    public void setFacultyName(String facultyName) {
        this.facultyName = facultyName;
    }
}
