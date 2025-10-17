package ru.aston.NotificationService.entity;

public class DeletedMessage extends AbstractMessage {

    private static final String from = "NotificationService";
    private static final String subject = "Deleted notification";
    private static final String message = "Здравствуйте! Ваш аккаунт был удалён.";

    public DeletedMessage(String to) {
        super(to,from,subject,message);
    }

}
