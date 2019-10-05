package pl.north93.serializer.platform.format;

import javax.annotation.Nullable;

import pl.north93.serializer.platform.context.DeserializationContext;
import pl.north93.serializer.platform.context.SerializationContext;
import pl.north93.serializer.platform.template.TemplateEngine;

public interface SerializationFormat<OUTPUT, S extends SerializationContext, D extends DeserializationContext>
{
    void configure(TemplateEngine templateEngine);

    SerializationConfiguration<S> createDefaultSerializationConfig();

    DeserializationConfiguration<OUTPUT, D> createDefaultDeserializationConfig();

    @Nullable
    TypePredictor<S, D> getTypePredictor();
}
