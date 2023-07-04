package com.cassianodess.gptapi.models;

import java.util.UUID;

public record GPTRequestBody(UUID chatId, String question) { }
