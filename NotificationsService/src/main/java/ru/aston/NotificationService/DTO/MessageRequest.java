package ru.aston.NotificationService.DTO;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import ru.aston.NotificationService.entity.AbstractMessage;

public class MessageRequest extends AbstractMessage {

    @NotNull
    @Size (min = 1, max = 30)
    @Email
    private String to;
    @NotNull
    @Size (min = 1, max = 30)
    private String from;
    @NotNull
    @Size (min = 1, max = 30)
    private String subject;
    @NotNull
    @Size (min = 1, max = 100)
    private String message;

    public MessageRequest() {
        super();
    }

    public MessageRequest(String to,String from,String subject,String message) {
        super(to,from,subject,message);
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "MessageDTORequest{" +
                "to='" + to + '\'' +
                ", from='" + from + '\'' +
                ", subject='" + subject + '\'' +
                ", message='" + message + '\'' +
                '}';
    }
}
