package com.optymyze.takehometask.entities.githubrepository;


import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import javax.annotation.PostConstruct;
import java.util.Collections;
import java.util.Map;
import java.util.Set;

@Slf4j
@Service
public class GitHubService {
    private WebClient webClient;

    @Value("${github.api.base-url}")
    private String baseUrl;

    @Value("${github.api.auth-token}")
    private String authToken;

    public GitHubService() {
        this.webClient = null;
    }

    @PostConstruct
    private void initializeWebClient() {
        this.webClient = WebClient.builder()
                .baseUrl(baseUrl)
                .defaultHeader(HttpHeaders.AUTHORIZATION, "Bearer " + authToken)
                .defaultHeader(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE)
                .build();
    }

    public Set<String> getRepositoryLanguages(String owner, String repoName) {
        String url = baseUrl + "/repos/" + owner + "/" + repoName + "/languages";

        try {
            Map<String, String> languagesMap = webClient.get()
                    .uri(url)
                    .retrieve()
                    .bodyToMono(new ParameterizedTypeReference<Map<String, String>>() {
                    })
                    .block();
            if (languagesMap != null) {
                return languagesMap.keySet();
            } else {
                log.info("languages set is empty");
                return Collections.emptySet();
            }
        } catch (WebClientResponseException.NotFound ex) {
            log.error("Repository not found: {}/{}", owner, repoName);
            return Collections.emptySet();
        } catch (Exception ex) {
            log.error("Error retrieving repository languages: {}/{}", owner, repoName, ex);
            return Collections.emptySet();
        }
    }
}
