package com.cassianodess.gptapi.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.cassianodess.gptapi.models.User;
import com.cassianodess.gptapi.repositories.UserRepository;

@Service
public class UserService {

    @Autowired
    private UserRepository repository;

    public User findById(String id) {
        return repository.findById(id).orElseThrow(() -> new RuntimeException("User not found"));
    }

}
