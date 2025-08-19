package travel.email.template;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Component
public class TemplateFactory {

    Map<String, TemplateStrategy> templates;

    public TemplateFactory(List<TemplateStrategy> templates) {
        this.templates = new HashMap<>();
        templates.forEach(template -> {
            this.templates.put(template.getType(), template);
            log.info("templates: {}", template.getType());
        });
    }

    public TemplateStrategy getTemplate(String key) {
        this.templates.keySet().forEach(template -> log.info("templates: {}", template));

        return templates.get(key);
    }
}
