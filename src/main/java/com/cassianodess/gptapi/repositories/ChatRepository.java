package com.cassianodess.gptapi.repositories;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cassianodess.gptapi.models.Chat;
@Repository
public interface ChatRepository extends JpaRepository<Chat, UUID> {
    
}
