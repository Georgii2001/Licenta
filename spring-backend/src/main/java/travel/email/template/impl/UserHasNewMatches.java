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
import static travel.email.constants.TemplateNames.USER_HAS_NEW_MATCHES;

@Slf4j
@Component
@NoArgsConstructor
public class UserHasNewMatches implements TemplateStrategy {

    @Autowired
    Configuration freemarkerConfig;

    @Value("${email.subject.userHasNewMatches}")
    String subject;

    @Override
    public String getType() {
        return USER_HAS_NEW_MATCHES.name();
    }

    @Override
    public String createEmailBody(EmailRequest emailRequest) throws TemplateException, IOException {
        emailRequest.setSubject(subject);

        freemarkerConfig.setClassForTemplateLoading(UserHasNewMatches.class, TEMPLATE_PATH);
        Template template = freemarkerConfig.getTemplate(USER_HAS_NEW_MATCHES.getFtlName());

        Map<String, String> data = new HashMap<>();
        data.put(RECEIVER, emailRequest.getReceiver().getUsername());

        return FreeMarkerTemplateUtils.processTemplateIntoString(template, data);
    }
}
