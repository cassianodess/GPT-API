package com.cassianodess.gptapi.services;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.cassianodess.gptapi.models.ChatGPTRequest;
import com.cassianodess.gptapi.models.ChatGPTResponse;

import reactor.core.publisher.Mono;

@Service
public class GPTService {

    private WebClient webClient;

    public GPTService(WebClient.Builder builder, @Value("${OPENAI_API_KEY}") String apiKey) {
        this.webClient = builder
          .baseUrl("https://api.openai.com/v1/completions")
          .defaultHeader("Content-Type", "application/json;charset=UTF-8")
          .defaultHeader("Authorization", String.format("Bearer %s", apiKey))
          .build();
    }

    public Mono<ChatGPTResponse> chatGPT(String prompt) {
        ChatGPTRequest request = new ChatGPTRequest("text-davinci-003", prompt, 0.7, 150, 1.0, 0.0, 0.0);
        return webClient.post().bodyValue(request).retrieve().bodyToMono(ChatGPTResponse.class);
    }


}
