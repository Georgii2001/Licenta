package travel.email.service;

import freemarker.template.TemplateException;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import travel.email.models.EmailRequest;
import travel.email.template.TemplateFactory;
import travel.email.template.TemplateStrategy;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.IOException;

@Component
@RequiredArgsConstructor
public class EmailService {

    private final JavaMailSender emailSender;
    private final TemplateFactory templateFactory;

    @Async
    public void sendEmail(EmailRequest emailRequest, String type) {

        try {

            MimeMessage message = emailSender.createMimeMessage();

            message.addHeader("Content-type", "text/HTML; charset=UTF-8");
            message.addHeader("format", "flowed");
            message.addHeader("Content-Transfer-Encoding", "8bit");

            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

            TemplateStrategy templateStrategy = templateFactory.getTemplate(type);
            helper.setText("", templateStrategy.createEmailBody(emailRequest));

            message.addRecipient(Message.RecipientType.TO, new InternetAddress(emailRequest.getReceiver().getEmail()));
            message.setSubject(emailRequest.getSubject());
            message.saveChanges();

            emailSender.send(message);
        } catch (MessagingException | TemplateException | IOException e) {
            throw new RuntimeException(e);
        }
    }
}
