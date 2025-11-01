package ru.aston.NotificationService;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.scheduling.annotation.EnableScheduling;
import ru.aston.NotificationService.configs.RemoteConfigurationInitializer;

import java.util.UUID;

@Configuration
@EnableAspectJAutoProxy
@SpringBootApplication
@EnableScheduling
public class NotificationServiceApplication {

    public static void main(String[] args) {
        SpringApplication app = new SpringApplication(NotificationServiceApplication.class);
        app.addInitializers(new RemoteConfigurationInitializer());
        app.run(args);
    }

}
