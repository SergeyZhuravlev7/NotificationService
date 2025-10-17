package ru.aston.NotificationService.controllers;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.aston.NotificationService.DTO.MessageRequest;
import ru.aston.NotificationService.exceptions.InvalidMessageException;
import ru.aston.NotificationService.services.EmailSenderImpl;

import java.util.Map;

import static ru.aston.NotificationService.utils.ErrorMessageConverter.convertToMessage;
import static ru.aston.NotificationService.utils.ResponseConverter.convertResponse;

@RestController
@RequestMapping ("/message")
public class MessageController {

    private final EmailSenderImpl emailSenderImpl;

    @Autowired
    public MessageController(EmailSenderImpl emailSenderImpl) {
        this.emailSenderImpl = emailSenderImpl;
    }

    @PostMapping
    public ResponseEntity<Map<String, String>> sendMessage(@RequestBody @Valid MessageRequest message,BindingResult bindingResult) {
        if (bindingResult.hasErrors()) throw new InvalidMessageException(convertToMessage(bindingResult));
        emailSenderImpl.send(message);
        return new ResponseEntity<>(convertResponse(message),HttpStatus.OK);
    }
}

