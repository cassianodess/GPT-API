package com.cassianodess.gptapi.repositories;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cassianodess.gptapi.models.Message;

@Repository
public interface MessageRepository extends JpaRepository<Message, UUID> {
    
}
