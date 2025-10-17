package ru.aston.NotificationService.services;

import ru.aston.NotificationService.entity.AbstractMessage;

public interface SenderInterface {

    void send(AbstractMessage message);
}
