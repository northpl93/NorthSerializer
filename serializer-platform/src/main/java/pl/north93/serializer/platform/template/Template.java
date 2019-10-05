package pl.north93.serializer.platform.template;

import pl.north93.serializer.platform.template.field.FieldInfo;
import pl.north93.serializer.platform.context.DeserializationContext;
import pl.north93.serializer.platform.context.SerializationContext;

public interface Template<T, S extends SerializationContext, D extends DeserializationContext>
{
    void serialise(S context, FieldInfo field, T object) throws Exception;

    T deserialize(D context, FieldInfo field) throws Exception;
}
