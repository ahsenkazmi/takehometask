package com.optymyze.takehometask.entities.user;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@Slf4j
public class UserService {
    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public Users createUser(Users user) {
        try {
            Users userObj = userRepository.save(user);
            log.info("user {} saved in db: ", userObj.toString());
            return userObj;
        } catch (Exception ex) {
            log.error("error occurred while creating user: {}", ex.getStackTrace());
            throw ex;
        }
    }

    public List<Users> getAllUsers() {
        return userRepository.findAll();
    }

    public Optional<Users> getUserById(Long id) {
        return userRepository.findById(id);
    }

    public Users updateUser(Users updatedUser) {
        Optional<Users> optionalUser = userRepository.findById(updatedUser.getId());
        if (optionalUser.isPresent()) {
            Users user = optionalUser.get();
            user.setFirstName(updatedUser.getFirstName());
            user.setSurname(updatedUser.getSurname());
            user.setPosition(updatedUser.getPosition());
            user.setGitHubProfileUrl(updatedUser.getGitHubProfileUrl());
            return userRepository.save(user);
        } else {
            throw new NoSuchElementException("User not found with id: " + updatedUser.getId());
        }
    }

    public Boolean deleteUser(Long id) {
        try {
            userRepository.deleteById(id);
            return true;
        } catch (IllegalArgumentException ex) {
            log.error("IllegalArgument passed");
            return false;
        } catch (EmptyResultDataAccessException ex) {
            log.error("User with id: {}, doesn't exist", id);
            return false;
        }
    }

    public void deleteAllUser() {
        userRepository.deleteAll();
    }
}
