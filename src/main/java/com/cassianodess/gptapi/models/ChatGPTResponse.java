package com.cassianodess.gptapi.models;

import java.util.List;

public record ChatGPTResponse(List<Choice> choices) {}
