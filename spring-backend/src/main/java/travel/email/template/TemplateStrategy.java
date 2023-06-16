package travel.email.template;

import freemarker.template.TemplateException;
import travel.email.models.EmailRequest;

import java.io.IOException;

public interface TemplateStrategy {

    String getType();

    String createEmailBody(EmailRequest emailRequest) throws TemplateException, IOException;
}
