package ru.aston.NotificationService.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import ru.aston.NotificationService.DTO.MessageRequest;
import ru.aston.NotificationService.services.Listener;

import java.util.Map;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@SpringBootTest
@AutoConfigureMockMvc
@DisplayNameGeneration (DisplayNameGenerator.Simple.class)
class MessageControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @MockitoBean
    private KafkaTemplate<String, String> kafkaTemplate;
    @MockitoBean
    private Listener kafkaListener;
    @MockitoBean
    private JavaMailSender javaMailSender;
    private final ObjectMapper objectMapper = new ObjectMapper();

    @DynamicPropertySource
    static void dynamicProperties(DynamicPropertyRegistry registry) {
        registry.add("kafka.enabled", () -> Boolean.FALSE);
    }

    @ParameterizedTest
    @MethodSource ("validMessages")
    void sendMessageShouldReturnResponse(MessageRequest message) throws Exception {
        var response = mockMvc
                .perform(post("/message")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(message)))
                .andReturn();
        Map<String, String> responseBody = objectMapper.readValue(response
                .getResponse()
                .getContentAsString(),Map.class);
        assertEquals(200,response
                .getResponse()
                .getStatus());
        assertTrue(responseBody
                .get("message")
                .contains(message.getTo()));
        verify(javaMailSender,times(1)).send(any(SimpleMailMessage.class));
    }

    @ParameterizedTest
    @MethodSource("invalidMessages")
    void sendMessageShouldThrowException(MessageRequest message) throws Exception {
        var response = mockMvc
                .perform(post("/message")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(message)))
                .andReturn();
        Map<String, String> responseBody = objectMapper.readValue(response
                .getResponse()
                .getContentAsString(),Map.class);
        assertEquals(400,response
                .getResponse()
                .getStatus());
    }

    private static Stream<Arguments> validMessages() {
        Arguments[] arguments = new Arguments[5];
        for (int i = 0;i < 5;i++) {
            MessageRequest messageRequest = new MessageRequest();
            messageRequest.setTo("testEmail" + i + "@gmail.com");
            messageRequest.setSubject("testSubject" + i);
            messageRequest.setFrom("testEmail" + (10 - i) + "@gmail.com");
            messageRequest.setMessage("testMessage" + i);
            arguments[i] = Arguments.of(messageRequest);
        }
        return Stream.of(arguments);
    }

    private static Stream<Arguments> invalidMessages() {
        return Stream.of(
                null,
                Arguments.of(new MessageRequest("1", "testEmail", "testSubject", "")),
                Arguments.of(new MessageRequest("", "", "", "")));
    }
}