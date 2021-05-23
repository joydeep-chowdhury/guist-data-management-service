package joydeep.poc.guist.guist.orchestrator.configurations;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientConfigurations {

    private OrchestratorConfigurations orchestratorConfigurations;

    public WebClientConfigurations(OrchestratorConfigurations orchestratorConfigurations) {
        this.orchestratorConfigurations = orchestratorConfigurations;
    }

    @Bean
    public WebClient webClient() {
        return WebClient.create(orchestratorConfigurations.getPersistorUrl());
    }

}
