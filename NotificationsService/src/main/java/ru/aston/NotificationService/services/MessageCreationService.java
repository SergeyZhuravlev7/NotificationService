package ru.aston.NotificationService.services;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Service;
import ru.aston.NotificationService.entity.AbstractMessage;

@Service
public class MessageCreationService {

    public static final String SENDER_EMAIL = "bigbrooogo@ya.ru";

    public SimpleMailMessage createMailMessage(AbstractMessage message) {
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(message.getTo());
        mailMessage.setFrom(SENDER_EMAIL);
        mailMessage.setSubject(message.getSubject());
        mailMessage.setText(message.getMessage());
        return mailMessage;
    }
}
