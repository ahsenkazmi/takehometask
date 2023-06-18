package com.optymyze.takehometask.entities.user;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.NotNull;

import lombok.Data;

@Data
public class UserValidation {

	@NotNull(message = "First name is required")
	private String firstName;
	
	@NotNull(message = "surname is required")
	private String surname;
	
	@NotNull(message = "Position is required")
	private String position;
	
	@NotNull(message = "GitHub profile URL is required")
	private String gitHubProfileUrl;

}
