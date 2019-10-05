package pl.north93.serializer.platform.format;

import pl.north93.serializer.platform.context.SerializationContext;
import pl.north93.serializer.platform.template.TemplateEngine;

public interface SerializationConfiguration<T extends SerializationContext>
{
    T createContext(TemplateEngine templateEngine);
}
