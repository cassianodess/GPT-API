package com.cassianodess.gptapi.controllers;

import java.net.URI;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cassianodess.gptapi.models.Auth;
import com.cassianodess.gptapi.models.AuthResponse;
import com.cassianodess.gptapi.models.User;
import com.cassianodess.gptapi.services.AuthService;

@CrossOrigin
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthService service;

    @PostMapping("/register")
    public ResponseEntity<User> register(@RequestBody User user) {
        user = service.signUp(user);
        if(user != null) {
            return ResponseEntity.ok(user);
        }

        return ResponseEntity.badRequest().build();

    }

    @PostMapping
    public ResponseEntity<AuthResponse> login(@RequestBody Auth auth) {
        return ResponseEntity.ok(service.signIn(auth.email(), auth.password()));
    }

    @GetMapping("/activate/{id}")
    public ResponseEntity<Void> activateAccount(@PathVariable UUID id) {
        
        User user = service.activateAccount(id);
        if (user != null) {
            //TODO: replace locahost to production URI
            return ResponseEntity.status(HttpStatus.FOUND).location(URI.create(String.format("http://localhost:4200/home/%s", user.getId()))).build();
        }
        return ResponseEntity.badRequest().build();
    }

}
