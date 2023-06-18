package com.optymyze.takehometask.entities.user;

import com.optymyze.takehometask.entities.user.UserRepository;
import com.optymyze.takehometask.entities.user.UserService;
import com.optymyze.takehometask.entities.user.Users;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.EmptyResultDataAccessException;

import java.util.List;
import java.util.NoSuchElementException;

@SpringBootTest
class UserServiceTest {
    private final UserService userService;

    final String firstName = "Ahsen";
    final String surname = "Kazmi";
    final String position = "Developer";
    final String gitHubProfileUrl = "https://github.com/ahsenkazmi";

    @Autowired
    public UserServiceTest(UserService userService) {
        this.userService = userService;
    }

    @BeforeEach
    @AfterEach
    void cleanUp(){
        userService.deleteAllUser();
    }

    @Test
    void createUser_shouldReturnInseredData(){
        // Arrange
        Users user = new Users(firstName, surname, position, gitHubProfileUrl);
        // Act
        Users userObj = userService.createUser(user);
        // Assert
        Assertions.assertEquals(firstName, userObj.getFirstName());
    }

    @Test
    void getAllUsers_shouldReturnAllUsers(){
        // Arrange
        String firstName_2 = "Hasnain";
        String gitHubProfileUrl_2="https://github.com/ahsenkazmi";
        Users user_1 = new Users(firstName, surname, position, gitHubProfileUrl);
        Users user_2 = new Users(firstName_2, surname, position, gitHubProfileUrl_2);
        userService.createUser(user_1);
        userService.createUser(user_2);
        // Act
        List<Users> users = userService.getAllUsers();
        // Assert
        Assertions.assertEquals(2, users.size());
    }


    @Test
    void updateUser_userNotFound_shouldThrowNoSuchElementException(){
        // Arrange
        Users user = new Users(firstName, surname, position, gitHubProfileUrl);
        Users userObj = userService.createUser(user);
        userObj.setPosition("Senior Developer");
        userObj.setId(999l);

        // Act and Assert
        Assertions.assertThrows(NoSuchElementException.class, () -> {
            userService.updateUser(userObj);
        });
    }
    @Test
    void updateUser_shouldUpdateUserInDb(){
        // Arrange
        String updatedPosition = "Developer";
        Users user = new Users(firstName, surname, position, gitHubProfileUrl);
        Users userObj = userService.createUser(user);
        userObj.setPosition(updatedPosition);
        // Act
        Users updatedUser = userService.createUser(user);
        // Assert
        Assertions.assertEquals(updatedPosition, updatedUser.getPosition());
    }

    @Test
    void deleteUser_nonExistingUser_shouldReturnFalse() {
        // Arrange
        Long nonExistingUserId = 999L;
        // Act
        boolean result = userService.deleteUser(nonExistingUserId);
        // Assert
        Assertions.assertFalse(result);
    }

    @Test
    void deleteUser_invalidUserId_shouldReturnFalse() {
        // Arrange
        Long invalidUserId = -1L;
        // Act
        boolean result = userService.deleteUser(invalidUserId);
        // Assert
        Assertions.assertFalse(result);
    }

    @Test
    void deleteUser_existingUser_shouldReturnTrue() {
        // Arrange
        Users user = new Users(firstName, surname, position, gitHubProfileUrl);
        Users savedUser = userService.createUser(user);
        // Act
        boolean result = userService.deleteUser(savedUser.getId());
        // Assert
        Assertions.assertTrue(result);
    }

}
