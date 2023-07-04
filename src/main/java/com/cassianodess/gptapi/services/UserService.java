package com.cassianodess.gptapi.services;

import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cassianodess.gptapi.models.Chat;
import com.cassianodess.gptapi.models.GPTRequestBody;
import com.cassianodess.gptapi.models.GPTResponse;
import com.cassianodess.gptapi.models.Message;
import com.cassianodess.gptapi.models.User;
import com.cassianodess.gptapi.repositories.UserRepository;

@Service
public class UserService {

    @Autowired
    private UserRepository repository;

    public User findById(UUID id) {
        return repository.findById(id).orElseThrow(() -> new RuntimeException("User not found"));
    }

    @Transactional
    public User saveChat(UUID userId, GPTRequestBody gptBody, GPTResponse messageResponse) {

        try {
            if (gptBody.chatId() == null) {
                User user = repository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));

                Message message = new Message();
                message.setQuestion(messageResponse.question());
                message.setResponse(messageResponse.response());
                
                Chat chat = new Chat();
                chat.getMessages().add(message);
                
                user.getChats().add(chat);
                
                return repository.save(user);

            } else {

                User user = repository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
                
                Message message = new Message();
                message.setQuestion(messageResponse.question());
                message.setResponse(messageResponse.response());

                user.getChats()
                .stream()
                .filter(_chat -> _chat.getId().equals(gptBody.chatId()))
                .collect(Collectors.toList())
                .get(0)
                .getMessages()
                .add(message);

                return repository.save(user);
            }

        } catch (Exception e) {
            System.out.println("ROLL BACK");
            throw e;
        }
        
    }

}
