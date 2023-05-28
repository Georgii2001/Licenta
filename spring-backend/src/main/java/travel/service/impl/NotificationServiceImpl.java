package travel.service.impl;

import travel.entities.UserEntity;
import travel.service.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class NotificationServiceImpl implements NotificationService {
    private final JavaMailSender javaMailSender;

    @Override
    public void sendNotification(UserEntity userEntity) {
        SimpleMailMessage mail = new SimpleMailMessage();
        String mailBody = "..body";
        mail.setTo(userEntity.getEmail());
        mail.setFrom("georgiana@gmail.com");
        mail.setSubject("sub");
        mail.setText(mailBody);

        javaMailSender.send(mail);
    }
}
