package joydeep.poc.guist.guist.orchestrator.services;

import joydeep.poc.guist.guist.orchestrator.configurations.OrchestratorConfigurations;
import joydeep.poc.guist.guist.orchestrator.domains.Department;
import joydeep.poc.guist.guist.orchestrator.domains.DepartmentsResponseDTO;
import joydeep.poc.guist.guist.orchestrator.domains.Faculty;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;

@Service
@Qualifier("department")
public class DepartmentClientService implements PersistorClientService<Department> {

    private WebClient webClient;
    private OrchestratorConfigurations orchestratorConfigurations;

    public DepartmentClientService(WebClient webClient, OrchestratorConfigurations orchestratorConfigurations) {
        this.webClient = webClient;
        this.orchestratorConfigurations = orchestratorConfigurations;
    }

    @Override
    public List<Department> fetchAll() {
        DepartmentsResponseDTO response = webClient.get().uri(orchestratorConfigurations.getDepartmentsSubresource()).retrieve().bodyToMono(DepartmentsResponseDTO.class).block();

        return response.getDepartmentList();
    }

    @Override
    public List<Department> fetchByPage(int pageNo, int pageSize) {
        DepartmentsResponseDTO response = webClient.get().uri(uriBuilder -> uriBuilder.path(orchestratorConfigurations.getDepartmentsSubresource()).queryParam(orchestratorConfigurations.getPageNoString(), String.valueOf(pageNo)).queryParam(orchestratorConfigurations.getPageSizeString(), String.valueOf(pageSize)).build()).retrieve().bodyToMono(DepartmentsResponseDTO.class).block();
        return response.getDepartmentList();
    }

    public List<Department> retrieveByDepartmentName(String departmentName) {
        DepartmentsResponseDTO response = webClient.get().uri(uriBuilder -> uriBuilder.path(orchestratorConfigurations.getDepartmentsSubresource()).queryParam(orchestratorConfigurations.getDepartmentNameString(), departmentName).build()).retrieve().bodyToMono(DepartmentsResponseDTO.class).block();
        return response.getDepartmentList();
    }
}
