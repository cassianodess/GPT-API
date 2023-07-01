package com.cassianodess.gptapi.models;

public record ChatGPTRequest(String model, String prompt, Double temperature, Integer max_tokens, Double top_p, Double frequency_penalty, Double presence_penalty) { }