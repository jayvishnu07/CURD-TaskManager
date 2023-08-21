package com.backend.Service;

import com.backend.Entity.Users;
import com.backend.ExceptionHandler.TaskNotFoundException;
import com.backend.Repository.UsersRepository;
import com.backend.SuccessMessage.SuccessMessageHandlerForUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServicesImplementation implements UserServices {

    @Autowired
    private UsersRepository usersRepository;
    @Autowired
    private final SuccessMessageHandlerForUser successMessageHandlerForUser;

    public UserServicesImplementation(SuccessMessageHandlerForUser successMessageHandlerForUser) {
        this.successMessageHandlerForUser = successMessageHandlerForUser;
    }

    @Override
    public ResponseEntity<Object> addUser(Users users) {

        return null;
    }

    @Override
    public ResponseEntity<Object> getOTP(Users data) {

        return null;
    }

    @Override
    public Users validateAndGetUserExtra(String username) {
        return getUserExtra(username).orElseThrow(() -> new TaskNotFoundException(username));
    }

    public Optional<Users> getUserExtra(String username) {
        return usersRepository.findByUserNameAllIgnoreCase(username);
    }

}
