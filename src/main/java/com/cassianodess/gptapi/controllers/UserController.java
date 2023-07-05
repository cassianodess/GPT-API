package com.cassianodess.gptapi.controllers;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cassianodess.gptapi.models.Chat;
import com.cassianodess.gptapi.models.GPTRequestBody;
import com.cassianodess.gptapi.models.GPTResponse;
import com.cassianodess.gptapi.models.User;
import com.cassianodess.gptapi.services.GPTService;
import com.cassianodess.gptapi.services.UserService;

@CrossOrigin
@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService service;

    @Autowired
    private GPTService GPTservice;

    @GetMapping("/{id}")
    public ResponseEntity<User> findById(@PathVariable UUID id) {
        User user = service.findById(id);
        if (user != null && user.getIsActivate()) {
            return ResponseEntity.ok(user);
        }
        return ResponseEntity.badRequest().body(null);
    }

    @PostMapping("/{id}/gpt")
    public ResponseEntity<Chat> AskMe(@RequestBody GPTRequestBody body, @PathVariable UUID id) {
        GPTResponse gptResponse = GPTservice.chatGPT(body.question())
        .map(response -> new GPTResponse(200, body.question(),
        response.choices().get(0).text().trim()))
        .block();

        return ResponseEntity.ok(service.saveChat(id, body, gptResponse));

    }

    @DeleteMapping("/{id}/gpt/delete/{chatId}")
    public ResponseEntity<List<Chat>> deleteChat(@PathVariable UUID id, @PathVariable UUID chatId) {
        return ResponseEntity.ok(service.deleteChat(id, chatId));
    }

}
