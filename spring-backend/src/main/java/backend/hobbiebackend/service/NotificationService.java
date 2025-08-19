package backend.hobbiebackend.service;

import backend.hobbiebackend.entities.UserEntity;
import org.springframework.scheduling.annotation.Async;

public interface NotificationService {
    @Async
    void sendNotification(UserEntity userEntity);
}
