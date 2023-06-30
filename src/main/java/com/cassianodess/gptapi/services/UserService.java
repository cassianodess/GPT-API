package com.cassianodess.gptapi.services;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.cassianodess.gptapi.models.User;
import com.cassianodess.gptapi.repositories.UserRepository;

@Service
public class UserService {

    @Autowired
    private UserRepository repository;

    public User findById(UUID id) {
        return repository.findById(id).orElseThrow(() -> new RuntimeException("User not found"));
    }

}
