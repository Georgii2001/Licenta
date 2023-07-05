package travel.email.template.impl;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;
import travel.email.models.EmailRequest;
import travel.email.template.TemplateStrategy;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static travel.email.constants.EmailConstants.*;
import static travel.email.constants.TemplateNames.INVITE_TO_NEW_TRIP;

@Slf4j
@Component
@NoArgsConstructor
public class InviteToNewTrip implements TemplateStrategy {

    @Autowired
    Configuration freemarkerConfig;

    @Value("${email.subject.inviteToNewTrip}")
    String subject;

    @Override
    public String getType() {
        return INVITE_TO_NEW_TRIP.name();
    }

    @Override
    public String createEmailBody(EmailRequest emailRequest) throws TemplateException, IOException {
        emailRequest.setSubject(subject);

        freemarkerConfig.setClassForTemplateLoading(InviteToNewTrip.class, TEMPLATE_PATH);
        Template template = freemarkerConfig.getTemplate(INVITE_TO_NEW_TRIP.getFtlName());

        Map<String, String> data = new HashMap<>();
        data.put(RECEIVER, emailRequest.getReceiver().getDisplayName());
        data.put(SENDER, emailRequest.getSender().getDisplayName());
        data.put(SENDER_EMAIL, emailRequest.getSender().getEmail());

        return FreeMarkerTemplateUtils.processTemplateIntoString(template, data);
    }
}
