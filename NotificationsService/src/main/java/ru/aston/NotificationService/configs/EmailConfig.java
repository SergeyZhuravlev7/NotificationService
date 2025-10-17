package ru.aston.NotificationService.configs;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import java.util.Properties;

@Configuration
public class EmailConfig {

    @Value ("${notifications.sender_email}")
    private String notificationsSenderEmail;

    @Value ("${notifications.sender_password}")
    private String notificationsSenderPassword;

    @Bean
    public JavaMailSender javaMailSender() {
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        mailSender.setHost("smtp.yandex.ru");
        mailSender.setPort(587);
        mailSender.setUsername(notificationsSenderEmail);
        mailSender.setPassword(notificationsSenderPassword);

        Properties props = mailSender.getJavaMailProperties();
        props.put("mail.transport.protocol","smtp");
        props.put("mail.smtp.auth","true");
        props.put("mail.smtp.starttls.enable","true");

        mailSender.setJavaMailProperties(props);
        return mailSender;
    }
}
