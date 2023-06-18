package com.optymyze.takehometask.entities.githubrepository;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class GitHubServiceTest {
    @Autowired
    private GitHubService gitHubService;

    @Test
    void getRepositoryLanguages_invalidOwnerOrRepo_shouldReturnEmptySet() {
        // Arrange
        String owner = "invalid-owner";
        String repoName = "invalid-repo";

        // Act
        Set<String> languages = gitHubService.getRepositoryLanguages(owner, repoName);

        // Assert
        Assertions.assertNotNull(languages);
        Assertions.assertTrue(languages.isEmpty());
    }

    @Test
    void getRepositoryLanguages_validOwnerAndRepo_shouldReturnLanguages() {
        // Arrange
        String owner = "ahsenkazmi";
        String repoName = "testrepo";

        // Act
        Set<String> languages = gitHubService.getRepositoryLanguages(owner, repoName);

        // Assert
        Assertions.assertNotNull(languages);
        Assertions.assertFalse(languages.isEmpty());
        Assertions.assertEquals(3, languages.size());
        Assertions.assertTrue(languages.contains("Java"));
    }
}
