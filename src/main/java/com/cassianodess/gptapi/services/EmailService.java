package com.cassianodess.gptapi.services;

import org.apache.commons.mail.DefaultAuthenticator;
import org.apache.commons.mail.SimpleEmail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.ExchangeFilterFunction;
import org.springframework.web.reactive.function.client.ExchangeFilterFunctions;
import org.springframework.web.reactive.function.client.WebClient;

import com.cassianodess.gptapi.models.Email;
import com.cassianodess.gptapi.models.EmailResponse;

@Service
public class EmailService {

    private WebClient webClient;

    public EmailService(
        WebClient.Builder builder,  @Value("${EMAIL_SENDER_URL}") String EMAIL_SENDER_URL,
        @Value("${USERNAME_SECRET}") String USERNAME_SECRET, @Value("${PASSWORD_SECRET}") String PASSWORD_SECRET
        ) {
        this.webClient = builder
          .baseUrl(EMAIL_SENDER_URL)
          .defaultHeader("Content-Type", "application/json;charset=UTF-8")
          .filter(ExchangeFilterFunctions.basicAuthentication(USERNAME_SECRET, PASSWORD_SECRET))
          .build();

    }

    public Boolean sendEmail(Email email) {

        try {

            EmailResponse response = this.webClient.post().bodyValue(email).retrieve().bodyToMono(EmailResponse.class).block();
            if(response.getStatus() != 200) {
                return false;
            }
            return true;
            
        } catch (Exception e) {
            System.out.println(e);
            return false;
        }

    }

}
