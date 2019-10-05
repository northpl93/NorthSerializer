package pl.north93.serializer.platform.format;

import pl.north93.serializer.platform.context.DeserializationContext;
import pl.north93.serializer.platform.template.TemplateEngine;

public interface DeserializationConfiguration<OUTPUT, T extends DeserializationContext>
{
    T createContext(TemplateEngine templateEngine, OUTPUT serializedData);
}
