package com.cassianodess.gptapi.services;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.cassianodess.gptapi.models.User;
import com.cassianodess.gptapi.repositories.UserRepository;

@Service
public class AuthService {

    @Autowired
    private UserRepository repository;

    @Autowired
    private EmailService emailService;

    @Autowired
    private PasswordEncoder bcrypt;

    public User signUp(User user) {
        try {

            if (repository.findByEmail(user.getEmail()).isPresent()) {
                throw new RuntimeException("email already exists");
            }

            String encryptedPassword = bcrypt.encode(user.getPassword());

            user.setPassword(encryptedPassword);
            user = repository.save(user);
    
            Boolean sent = emailService.sendEmail(
                String.format("Clique no link e ative sua conta: http://localhost:8080/api/auth/activate/%s", user.getId()),
                user.getEmail(),
                "Ativação da conta ChatGPT Crone"
            );
            if(sent) {
                return user;
            }
            repository.deleteById(user.getId());

        } catch (Exception e) {
            throw e;
        }
        return null;
    }

    public User signIn(String email, String password) {
        
        if ((!repository.findByEmail(email).isPresent()) || !bcrypt.matches(password, repository.findByEmail(email).get().getPassword()) || !repository.findByEmail(email).get().getIsActivate()) {
            throw new RuntimeException("invalid credentials or email not activated");
        }
        return repository.findByEmail(email).get();

    }

    public User activateAccount(UUID id) {
        User user = repository.findById(id).orElseThrow(() -> new RuntimeException("User not found"));
        user.setIsActivate(true);
        return repository.save(user);
    }
}
