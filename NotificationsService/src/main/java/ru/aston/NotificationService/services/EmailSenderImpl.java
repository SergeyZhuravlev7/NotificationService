package ru.aston.NotificationService.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import ru.aston.NotificationService.entity.AbstractMessage;

@Service
public class EmailSenderImpl implements SenderInterface {

    private final JavaMailSender mailSender;
    private final MessageCreationService messageCreationService;

    @Autowired
    public EmailSenderImpl(JavaMailSender mailSender,MessageCreationService messageCreationService) {
        this.mailSender = mailSender;
        this.messageCreationService = messageCreationService;
    }

    public void send(AbstractMessage message) {
        SimpleMailMessage simpleMailMessage = messageCreationService.createMailMessage(message);
        mailSender.send(simpleMailMessage);
        System.out.println("Sending email to " + message.getTo());
    }

}
