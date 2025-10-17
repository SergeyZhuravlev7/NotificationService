package ru.aston.NotificationService.services;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.KafkaException;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import ru.aston.NotificationService.entity.CreatedMessage;
import ru.aston.NotificationService.entity.DeletedMessage;

@Component
public class Listener {

    private final EmailSenderImpl emailSenderImpl;

    @Autowired
    public Listener(EmailSenderImpl emailSenderImpl) {
        this.emailSenderImpl = emailSenderImpl;
    }

    @KafkaListener (topics = {"${notifications.topic}"}, groupId = "NotificationServices")
    public void onMessage(ConsumerRecord<String, String> consumerRecord) {
        switch (consumerRecord.value()) {
            case "created" -> emailSenderImpl.send(new CreatedMessage(consumerRecord.key()));
            case "deleted" -> emailSenderImpl.send(new DeletedMessage(consumerRecord.key()));
            default -> throw new KafkaException("Unknown record received");
        }
    }
}
