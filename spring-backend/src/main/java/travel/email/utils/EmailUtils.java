package travel.email.utils;

import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import travel.email.models.EmailRequest;
import travel.email.service.EmailService;
import travel.entities.Notifications;
import travel.entities.UserEntity;
import travel.repostiory.NotificationsRepository;

import static travel.email.constants.EmailConstants.MESSAGE_WAS_ALREADY_SENT;
import static travel.email.constants.EmailConstants.NEW_MESSAGE_SENT;

@Component
@RequiredArgsConstructor
public class EmailUtils {

    private final NotificationsRepository notificationsRepository;
    private final EmailService emailService;

    public String notifyUser(EmailRequest emailRequest, String type) {

        UserEntity sender = emailRequest.getSender();
        UserEntity receiver = emailRequest.getReceiver();

        Notifications notifications = notificationsRepository.findUserNotificationByType(sender, receiver, type);

        if (notifications == null) {
            emailService.sendEmail(emailRequest, type);
            insertNotification(sender, receiver, type);
            return NEW_MESSAGE_SENT;
        }
        return MESSAGE_WAS_ALREADY_SENT;
    }

    private void insertNotification(UserEntity sender, UserEntity receiver, String notificationType) {
        Notifications notifications = new Notifications();

        notifications.setUserSender(sender);
        notifications.setUserReceiver(receiver);
        notifications.setNotificationType(notificationType);

        notificationsRepository.save(notifications);
    }
}
