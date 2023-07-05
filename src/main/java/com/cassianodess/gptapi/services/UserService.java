package com.cassianodess.gptapi.services;

import java.util.List;
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
import com.cassianodess.gptapi.repositories.ChatRepository;
import com.cassianodess.gptapi.repositories.UserRepository;

@Service
public class UserService {

    @Autowired
    private UserRepository repository;

    @Autowired
    private ChatRepository chatRepository;

    public User findById(UUID id) {
        return repository.findById(id).orElseThrow(() -> new RuntimeException("User not found"));
    }

    @Transactional
    public Chat saveChat(UUID userId, GPTRequestBody gptBody, GPTResponse messageResponse) {

        try {
            if (gptBody.chatId() == null) {
                User user = repository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));

                Message message = new Message();
                message.setQuestion(messageResponse.question());
                message.setResponse(messageResponse.response());
                
                Chat chat = new Chat();
                chat.getMessages().add(message);
                
                user.getChats().add(chat);
                
                user = repository.save(user);

                return user.getChats().get(user.getChats().size()-1);

            } else {

                User user = repository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
                
                Message message = new Message();
                message.setQuestion(messageResponse.question());
                message.setResponse(messageResponse.response());

                Chat chat = (Chat)user.getChats()
                .stream()
                .filter(_chat -> _chat.getId().equals(gptBody.chatId()))
                .collect(Collectors.toList())
                .get(0);

                chat.getMessages()
                .add(message);

                repository.save(user);
                return chat;
            }

        } catch (Exception e) {
            System.out.println("ROLL BACK");
            throw e;
        }
        
    }

    public List<Chat> deleteChat(UUID userId, UUID chatId) {
        User user = repository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
        Chat chat = chatRepository.findById(chatId).orElseThrow(() -> new RuntimeException("Chat not found"));
        user.getChats().remove(chat);
        chatRepository.delete(chat);
        return repository.save(user).getChats();

    }

}
