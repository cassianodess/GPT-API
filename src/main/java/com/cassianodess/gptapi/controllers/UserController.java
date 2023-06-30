package com.cassianodess.gptapi.controllers;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cassianodess.gptapi.models.User;
import com.cassianodess.gptapi.services.UserService;

@CrossOrigin
@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService service;

    @GetMapping("/{id}")
    public ResponseEntity<User> findById(@PathVariable UUID id) {
        User user = service.findById(id);
        if (user != null && user.getIsActivate()) {
            return ResponseEntity.ok(user);
        }
        return ResponseEntity.badRequest().body(null);
    }

}
