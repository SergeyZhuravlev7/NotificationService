package ru.aston.NotificationService.utils;

import ru.aston.NotificationService.DTO.MessageRequest;

import java.util.HashMap;
import java.util.Map;

public class ResponseConverter {

    public static Map<String, String> convertResponse(MessageRequest message) {
        Map<String, String> response = new HashMap<>();
        response.put("message","Successfully send mail to '" + message.getTo() + "'");
        return response;
    }
}
