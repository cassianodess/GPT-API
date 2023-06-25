package com.cassianodess.gptapi.controllers;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cassianodess.gptapi.models.Auth;
import com.cassianodess.gptapi.models.User;
import com.cassianodess.gptapi.services.AuthService;
import com.cassianodess.gptapi.services.EmailService;

@CrossOrigin
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthService service;

    @PostMapping("/register")
    public ResponseEntity<User> register(@RequestBody User user) {
        return ResponseEntity.ok(service.signUp(user));

    }

    @PostMapping
    public ResponseEntity<User> login(@RequestBody Auth auth) {
        return ResponseEntity.ok(service.signIn(auth.email(), auth.password()));
    }

    @GetMapping("/activate/{id}")
    public ResponseEntity<User> activateAccount(@PathVariable String id) {

        User user = service.activateAccount(id);
        if (user != null) {
            return ResponseEntity.ok(user);
        }
        return ResponseEntity.badRequest().body(null);
    }

}
