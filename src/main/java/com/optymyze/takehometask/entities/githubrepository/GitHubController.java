package com.optymyze.takehometask.entities.githubrepository;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;

@Slf4j
@RestController
@RequestMapping("/api/github")
public class GitHubController {
    private final GitHubService gitHubService;

    @Autowired
    public GitHubController(GitHubService gitHubService) {
        this.gitHubService = gitHubService;
    }

    @GetMapping("/repositories/{username}/{repositoryName}/languages")
    public ResponseEntity<Set<String>> getRepositoryLanguages(@PathVariable String username, @PathVariable String repositoryName) {
        Set<String> repositoryLanguages = gitHubService.getRepositoryLanguages(username, repositoryName);
        log.info("repository languages: {}", repositoryLanguages.toString());
        if (repositoryLanguages.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(repositoryLanguages);
        }
        return ResponseEntity.status(HttpStatus.OK).body(repositoryLanguages);
    }
}
