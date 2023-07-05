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

    @Autowired
    private GPTService GPTservice;

    public User findById(UUID id) {
        return repository.findById(id).orElseThrow(() -> new RuntimeException("User not found"));
    }

    @Transactional
    public Chat saveChat(UUID userId, GPTRequestBody body) {

        try {
            if (body.chatId() == null) {
                GPTResponse gptResponse = GPTservice.chatGPT(body.question())
                .map(response -> new GPTResponse(200, body.question(), response.choices().get(0).text().trim()))
                .block();

                User user = repository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));

                Message message = new Message();
                message.setQuestion(gptResponse.question());
                message.setResponse(gptResponse.response());
                
                Chat chat = new Chat();
                chat.getMessages().add(message);
                
                user.getChats().add(chat);
                
                user = repository.save(user);

                return user.getChats().get(user.getChats().size()-1);

            } else {

                User user = repository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
                
                Chat chat = (Chat)user.getChats()
                .stream()
                .filter(_chat -> _chat.getId().equals(body.chatId()))
                .collect(Collectors.toList())
                .get(0);

                String context = "";

                for(int i = 0; i< chat.getMessages().size(); i++) {
                    context += String.format("USER: %s\nBOT: %s\n", chat.getMessages().get(i).getQuestion(), chat.getMessages().get(i).getResponse());
                }

                context += String.format("USER: %s\n", body.question());
                
                GPTResponse gptResponse = GPTservice.chatGPT(context)
                .map(response -> new GPTResponse(200, body.question(), response.choices().get(0).text().trim().replace("BOT: ", "")))
                .block();
                
                Message message = new Message();
                message.setQuestion(gptResponse.question());
                message.setResponse(gptResponse.response());


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
