package pl.north93.serializer.platform;

import java.lang.reflect.Type;

import pl.north93.serializer.platform.context.DeserializationContext;
import pl.north93.serializer.platform.context.SerializationContext;
import pl.north93.serializer.platform.format.DeserializationConfiguration;
import pl.north93.serializer.platform.format.SerializationConfiguration;
import pl.north93.serializer.platform.format.SerializationFormat;
import pl.north93.serializer.platform.template.TemplateEngine;

public interface NorthSerializer<OUTPUT>
{
    OUTPUT serialize(Type type, Object object, SerializationConfiguration<?> configuration);

    default OUTPUT serialize(final Object object)
    {
        final var config = this.getSerializationFormat().createDefaultSerializationConfig();
        return this.serialize(object.getClass(), object, config);
    }

    default OUTPUT serialize(final Type type, final Object object)
    {
        final var config = this.getSerializationFormat().createDefaultSerializationConfig();
        return this.serialize(type, object, config);
    }

    <T> T deserialize(Type type, OUTPUT serialized, DeserializationConfiguration<OUTPUT, ?> configuration);

    default <T> T deserialize(final Type type, final OUTPUT serialized)
    {
        final var defaultDeserializationConfig = this.getSerializationFormat().createDefaultDeserializationConfig();
        return this.deserialize(type, serialized, defaultDeserializationConfig);
    }

    SerializationFormat<OUTPUT, ? extends SerializationContext, ? extends DeserializationContext> getSerializationFormat();

    TemplateEngine getTemplateEngine();
}
