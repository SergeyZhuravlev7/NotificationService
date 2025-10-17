package ru.aston.NotificationService.entity;

public class CreatedMessage extends AbstractMessage {

    private static final String from = "NotificationService";
    private static final String subject = "Created notification";
    private static final String message = "Здравствуйте! Ваш аккаунт на сайте " + from + " был успешно создан.";

    public CreatedMessage(String to) {
        super(to,from,subject,message);
    }

}
