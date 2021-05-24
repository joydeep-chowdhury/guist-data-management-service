package joydeep.poc.guist.guist.orchestrator.services;

import joydeep.poc.guist.guist.orchestrator.configurations.OrchestratorConfigurations;
import joydeep.poc.guist.guist.orchestrator.domains.Department;
import joydeep.poc.guist.guist.orchestrator.domains.Faculty;
import joydeep.poc.guist.guist.orchestrator.domains.FacultiesResponseDTO;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClient.ResponseSpec;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Qualifier("faculty")
public class FacultyClientService implements PersistorClientService<Faculty> {

    private WebClient webClient;
    private OrchestratorConfigurations orchestratorConfigurations;

    public FacultyClientService(WebClient webClient, OrchestratorConfigurations orchestratorConfigurations) {
        this.webClient = webClient;
        this.orchestratorConfigurations = orchestratorConfigurations;
    }

    @Override
    public List<Faculty> fetchAll() {
        FacultiesResponseDTO response = webClient.get().uri(orchestratorConfigurations.getFacultiesSubresource()).retrieve().bodyToMono(FacultiesResponseDTO.class).block();

        return response.getFacultyList();
    }

    @Override
    public List<Faculty> fetchByPage(int pageNo, int pageSize) {
        FacultiesResponseDTO response = webClient.get().uri(uriBuilder -> uriBuilder.path(orchestratorConfigurations.getFacultiesSubresource()).queryParam(orchestratorConfigurations.getPageNoString(), String.valueOf(pageNo)).queryParam(orchestratorConfigurations.getPageSizeString(), String.valueOf(pageSize)).build()).retrieve().bodyToMono(FacultiesResponseDTO.class).block();
        return response.getFacultyList();
    }

    public List<Faculty> retrieveByDepartmentNameAndDesignation(String departmentName, String designation) {
        FacultiesResponseDTO response = webClient.get().uri(uriBuilder -> uriBuilder.path(orchestratorConfigurations.getFacultiesSubresource()).queryParam(orchestratorConfigurations.getDepartmentNameString(), departmentName).queryParam(orchestratorConfigurations.getDesignationString(), designation).build()).retrieve().bodyToMono(FacultiesResponseDTO.class).block();
        return response.getFacultyList();
    }


    public List<Faculty> retrieveByFacultyName(String facultyName) {
        FacultiesResponseDTO response = webClient.get().uri(uriBuilder -> uriBuilder.path(orchestratorConfigurations.getFacultiesSubresource()).queryParam(orchestratorConfigurations.getFacultyName(), facultyName).build()).retrieve().bodyToMono(FacultiesResponseDTO.class).block();
        return response.getFacultyList();
    }

    public List<Faculty> retrieveByDepartmentName(String departmentName) {
        FacultiesResponseDTO response = webClient.get().uri(uriBuilder -> uriBuilder.path(orchestratorConfigurations.getFacultiesSubresource()).queryParam(orchestratorConfigurations.getDepartmentNameString(), departmentName).build()).retrieve().bodyToMono(FacultiesResponseDTO.class).block();
        return response.getFacultyList();
    }

    public List<Faculty> retrieveByDesignation(String designation) {
        FacultiesResponseDTO response = webClient.get().uri(uriBuilder -> uriBuilder.path(orchestratorConfigurations.getFacultiesSubresource()).queryParam(orchestratorConfigurations.getDesignationString(), designation).build()).retrieve().bodyToMono(FacultiesResponseDTO.class).block();
        return response.getFacultyList();
    }
}
