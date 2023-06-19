package com.optymyze.takehometask.entities.user;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;


@Data
@NoArgsConstructor
@Entity
public class Users {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String firstName;
    private String surname;
    private String position;
    private String gitHubProfileUrl;

    public Users(String firstName, String surname, String position, String gitHubProfileUrl) {
        this.firstName = firstName;
        this.surname = surname;
        this.position = position;
        this.gitHubProfileUrl= gitHubProfileUrl;
    }
}
