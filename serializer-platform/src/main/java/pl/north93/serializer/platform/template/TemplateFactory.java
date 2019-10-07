package pl.north93.serializer.platform.template;

public interface TemplateFactory
{
    <T> Template<T, ?, ?> createAndRegisterTemplate(TemplateEngine templateEngine, Class<T> clazz);
}
