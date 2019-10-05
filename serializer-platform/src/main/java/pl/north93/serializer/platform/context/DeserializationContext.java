package pl.north93.serializer.platform.context;

import pl.north93.serializer.platform.template.TemplateEngine;
import pl.north93.serializer.platform.template.field.FieldInfo;

public abstract class DeserializationContext extends Context
{
    public DeserializationContext(final TemplateEngine templateEngine)
    {
        super(templateEngine);
    }

    public abstract boolean trySkipNull(FieldInfo field) throws Exception;

    // SIMPLE TYPES //

    public abstract String readString(FieldInfo field) throws Exception;

    public abstract Boolean readBoolean(FieldInfo field) throws Exception;

    public abstract Byte readByte(FieldInfo field) throws Exception;

    public abstract Short readShort(FieldInfo field) throws Exception;

    public abstract Integer readInteger(FieldInfo field) throws Exception;

    public abstract Float readFloat(FieldInfo field) throws Exception;

    public abstract Double readDouble(FieldInfo field) throws Exception;

    public abstract Long readLong(FieldInfo field) throws Exception;
}
