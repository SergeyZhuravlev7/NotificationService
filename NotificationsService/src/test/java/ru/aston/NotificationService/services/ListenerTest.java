package ru.aston.NotificationService.services;

import org.apache.kafka.clients.producer.ProducerRecord;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.context.bean.override.mockito.MockitoSpyBean;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.kafka.KafkaContainer;
import ru.aston.NotificationService.entity.AbstractMessage;

import java.util.concurrent.TimeUnit;

import static org.awaitility.Awaitility.await;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@SpringBootTest
@Testcontainers
class ListenerTest {

    @Container
    static KafkaContainer kafka = new KafkaContainer("apache/kafka:4.1.0");

    @DynamicPropertySource
    static void kafkaProps(DynamicPropertyRegistry registry) {
        registry.add("spring.kafka.bootstrap-servers", kafka::getBootstrapServers);
        registry.add("notifications.topic", () -> "test_notifications");
    }

    @Autowired
    KafkaTemplate<String, String> kafkaTemplate;

    @MockitoSpyBean
    EmailSenderImpl emailSenderImpl;

    @Test
    void shouldCallEmailSender_OnCreatedMessage() throws Exception {
        Thread.sleep(2000);
        kafkaTemplate.send(new ProducerRecord<>("test_notifications", "test@mail.com", "created"));
        kafkaTemplate.flush();

        await().atMost(2, TimeUnit.SECONDS)
               .untilAsserted(() ->
                       verify(emailSenderImpl, times(1)).send(any(AbstractMessage.class)));
    }

    @Test
    void shouldCallEmailSender_OnDeletedMessage() {
        kafkaTemplate.send(new ProducerRecord<>("test_notifications", "test@mail.com", "deleted"));
        kafkaTemplate.flush();

        await().atMost(2, TimeUnit.SECONDS)
               .untilAsserted(() ->
                       verify(emailSenderImpl, times(1)).send(any(AbstractMessage.class)));
    }
}
