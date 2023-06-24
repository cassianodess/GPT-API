package com.cassianodess.gptapi.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cassianodess.gptapi.repositories.UserRepository;

@Service
public class UserService {
    
    @Autowired
    private UserRepository repository;
    
        

}
