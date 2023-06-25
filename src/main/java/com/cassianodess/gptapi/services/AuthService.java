package com.cassianodess.gptapi.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cassianodess.gptapi.models.User;
import com.cassianodess.gptapi.repositories.UserRepository;

@Service
public class AuthService {

    @Autowired
    private UserRepository repository;

    public User signUp(User user) {
        if (repository.findByEmail(user.getEmail()).isPresent()) {
            throw new RuntimeException("email already exists");
        }
        user = repository.save(user);

        EmailService emailService = new EmailService();
        Boolean sent = emailService.sendEmail(String.format("Clique no link e ative sua conta: http://localhost:4200/activate/%s", user.getId()), user.getEmail(), "Ativação da conta ChatGPT Crone");

        return user;
    }

    public User signIn(String email, String password) {
        if ((!repository.findByEmail(email).isPresent()) || !(repository.findByEmail(email).get().getPassword().equals(password))) {
            throw new RuntimeException("invalid credentials");
        }
        return repository.findByEmail(email).get();

    }
}
