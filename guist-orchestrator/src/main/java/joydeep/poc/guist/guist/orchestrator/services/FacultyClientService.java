package joydeep.poc.guist.guist.orchestrator.services;

import joydeep.poc.guist.guist.orchestrator.configurations.OrchestratorConfigurations;
import joydeep.poc.guist.guist.orchestrator.domains.Department;
import joydeep.poc.guist.guist.orchestrator.domains.Faculty;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClient.ResponseSpec;
import reactor.core.publisher.Mono;

import java.util.List;

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
        List<Faculty> facultyList = webClient.get().uri(orchestratorConfigurations.getFacultiesSubresource()).retrieve().bodyToMono(List.class).block();

        return facultyList;
    }

    @Override
    public List<Faculty> fetchByPage(int pageNo, int pageSize) {
        List<Faculty> facultyList = webClient.get().uri(uriBuilder -> uriBuilder.path(orchestratorConfigurations.getFacultiesSubresource()).queryParam(orchestratorConfigurations.getPageNoString(), String.valueOf(pageNo)).queryParam(orchestratorConfigurations.getPageSizeString(), String.valueOf(pageSize)).build()).retrieve().bodyToMono(List.class).block();
        return facultyList;
    }
}
