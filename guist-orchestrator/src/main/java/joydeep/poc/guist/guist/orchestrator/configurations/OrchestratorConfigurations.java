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
}
