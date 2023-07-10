package com.cassianodess.gptapi.services;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.cassianodess.gptapi.models.AuthResponse;
import com.cassianodess.gptapi.models.Email;
import com.cassianodess.gptapi.models.User;
import com.cassianodess.gptapi.repositories.UserRepository;

@Service
public class AuthService implements UserDetailsService {

    @Autowired
    private UserRepository repository;

    @Autowired
    private EmailService emailService;

    @Autowired
    private PasswordEncoder bcrypt;

    @Value("${SECRET}")
    private String secret;

    public User signUp(User user) {
        try {

            if (repository.findByEmail(user.getEmail()).isPresent()) {
                throw new RuntimeException("email already exists");
            }

            String encryptedPassword = bcrypt.encode(user.getPassword());

            user.setPassword(encryptedPassword);
            user = repository.save(user);
    
            Boolean sent = emailService.sendEmail(new Email(
                user.getEmail(),
                "Ativação da conta ChatGPT Crone",
                String.format("Clique no link e ative sua conta: http://localhost:8080/api/auth/activate/%s", user.getId())
            ));

            if(sent) {
                return user;
            }
            
            repository.deleteById(user.getId());

        } catch (Exception e) {
            throw e;
        }
        return null;
    }

    public AuthResponse signIn(String email, String password) {
        
        if ((!repository.findByEmail(email).isPresent()) || !bcrypt.matches(password, repository.findByEmail(email).get().getPassword()) || !repository.findByEmail(email).get().getIsActivate()) {
            throw new RuntimeException("invalid credentials or email not activated");
        }
        User user =  repository.findByEmail(email).get();
        return new AuthResponse(generateJWT(user), user);

    }

    public User activateAccount(UUID id) {
        User user = repository.findById(id).orElseThrow(() -> new RuntimeException("User not found"));
        user.setIsActivate(true);
        return repository.save(user);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return repository.findByEmail(username).get();
    }

    public String generateJWT(User user) {
        try {

            Instant expiresAt = LocalDateTime.now().plusHours(24).toInstant(ZoneOffset.of("-03:00"));

            Algorithm algorithm = Algorithm.HMAC256(secret);
            String token = JWT
            .create()
            .withIssuer("auth-gpt")
            .withSubject(user.getEmail())
            .withExpiresAt(expiresAt)
            .sign(algorithm);
            return token;
        } catch (JWTCreationException e) {
            throw new RuntimeException("error while generating token", e);
        }
    }

    

}
