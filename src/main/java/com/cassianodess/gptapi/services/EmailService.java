package com.cassianodess.gptapi.services;

import org.apache.commons.mail.DefaultAuthenticator;
import org.apache.commons.mail.SimpleEmail;

public class EmailService {

    public Boolean sendEmail(String message, String addressee, String subject) {

        try {
            SimpleEmail simpleEmail = new SimpleEmail();
            simpleEmail.setHostName("smtp.gmail.com");
            simpleEmail.setSmtpPort(587);
            simpleEmail.setAuthenticator(new DefaultAuthenticator("cassianomailsender@gmail.com", "iuojeooeryebeapj"));
            simpleEmail.setSSLOnConnect(true);

            simpleEmail.setFrom("cassianomailsender@gmail.com");
            simpleEmail.setSubject(subject);
            simpleEmail.setMsg(message);
            simpleEmail.addTo(addressee);
            simpleEmail.send();
            return true;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;

    }

}
