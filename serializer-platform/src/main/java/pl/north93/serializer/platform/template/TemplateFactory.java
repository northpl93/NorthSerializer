package pl.north93.serializer.platform.template;

public interface TemplateFactory
{
    <T> Template<T, ?, ?> createTemplate(TemplateEngine templateEngine, Class<T> clazz);
}
