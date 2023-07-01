package com.cassianodess.gptapi.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cassianodess.gptapi.models.GPTRequest;
import com.cassianodess.gptapi.models.GPTResponse;
import com.cassianodess.gptapi.services.GPTService;

@CrossOrigin
@RestController
@RequestMapping("/api/gpt")
public class GPTController {

    @Autowired
    private GPTService service;

    @PostMapping
    public ResponseEntity<GPTResponse> AskMe(@RequestBody GPTRequest body) {
        return service.chatGPT(body.question())
            .map(response -> ResponseEntity.ok(new GPTResponse(200, body.question(), response.choices().get(0).text())))
            .defaultIfEmpty(ResponseEntity.notFound().build()).block();
    }

    @GetMapping("/clear-cache")
    public String clearCache() {
        return "LIMPOU";
    }

}
