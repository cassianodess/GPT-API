package com.cassianodess.gptapi.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cassianodess.gptapi.models.User;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    
}
