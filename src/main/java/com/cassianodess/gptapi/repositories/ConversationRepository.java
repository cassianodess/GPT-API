package com.cassianodess.gptapi.repositories;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cassianodess.gptapi.models.Conversation;

@Repository
public interface ConversationRepository extends JpaRepository<Conversation, UUID> {
    
}
