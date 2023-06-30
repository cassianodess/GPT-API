package com.cassianodess.gptapi.models;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @Column(nullable = false)
    private String email;
    
    @Column(nullable = false)
    private String password;
    
    private Boolean isActivate = false;
    
    @OneToMany(mappedBy = "user")
    private List<Chat> chats = new ArrayList<Chat>();
}
