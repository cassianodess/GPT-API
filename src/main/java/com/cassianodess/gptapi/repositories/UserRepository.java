package com.cassianodess.gptapi.repositories;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.cassianodess.gptapi.models.User;

@Repository
public interface UserRepository extends JpaRepository<User, UUID> {

    @Query(value = "select * from users u where u.email = ?1", nativeQuery = true)
    Optional<User> findByEmail(String email);

}
