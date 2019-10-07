package pl.north93.serializer.platform;

import java.lang.reflect.Type;

import pl.north93.serializer.platform.context.DeserializationContext;
import pl.north93.serializer.platform.context.SerializationContext;
import pl.north93.serializer.platform.format.DeserializationConfiguration;
import pl.north93.serializer.platform.format.SerializationConfiguration;
import pl.north93.serializer.platform.format.SerializationFormat;
import pl.north93.serializer.platform.template.TemplateEngine;

public interface NorthSerializer<OUT, IN>
{
    OUT serialize(Type type, Object object, SerializationConfiguration<?> configuration);

    default OUT serialize(final Object object)
    {
        final var config = this.getSerializationFormat().createDefaultSerializationConfig();
        return this.serialize(object.getClass(), object, config);
    }

    default OUT serialize(final Type type, final Object object)
    {
        final var config = this.getSerializationFormat().createDefaultSerializationConfig();
        return this.serialize(type, object, config);
    }

    <T> T deserialize(Type type, IN serialized, DeserializationConfiguration<IN, ?> configuration);

    default <T> T deserialize(final Type type, final IN serialized)
    {
        final var defaultDeserializationConfig = this.getSerializationFormat().createDefaultDeserializationConfig();
        return this.deserialize(type, serialized, defaultDeserializationConfig);
    }

    SerializationFormat<OUT, IN, ? extends SerializationContext, ? extends DeserializationContext> getSerializationFormat();

    TemplateEngine getTemplateEngine();
}
