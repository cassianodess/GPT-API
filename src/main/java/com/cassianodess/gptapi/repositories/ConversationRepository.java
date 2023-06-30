package com.cassianodess.gptapi.repositories;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cassianodess.gptapi.models.Conversation;

public interface ConversationRepository extends JpaRepository<Conversation, UUID> {
    
}
