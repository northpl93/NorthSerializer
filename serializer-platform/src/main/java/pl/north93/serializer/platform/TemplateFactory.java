package pl.north93.serializer.platform;

import pl.north93.serializer.platform.template.Template;
import pl.north93.serializer.platform.template.TemplateEngine;

public interface TemplateFactory
{
    <T> Template<T, ?, ?> createTemplate(TemplateEngine templateEngine, Class<T> clazz);
}
