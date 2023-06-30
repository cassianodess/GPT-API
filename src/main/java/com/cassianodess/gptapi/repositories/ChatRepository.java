package com.cassianodess.gptapi.repositories;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cassianodess.gptapi.models.Chat;

public interface ChatRepository extends JpaRepository<Chat, UUID> {
    
}
