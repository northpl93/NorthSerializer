package pl.north93.serializer.platform.template.builtin;

import java.util.Date;

import pl.north93.serializer.platform.template.field.FieldInfo;
import pl.north93.serializer.platform.context.DeserializationContext;
import pl.north93.serializer.platform.context.SerializationContext;
import pl.north93.serializer.platform.template.Template;

public class DateTemplate implements Template<Date, SerializationContext, DeserializationContext>
{
    @Override
    public void serialise(final SerializationContext context, final FieldInfo field, final Date object) throws Exception
    {
        context.writeLong(field, object.getTime());
    }

    @Override
    public Date deserialize(final DeserializationContext context, final FieldInfo field) throws Exception
    {
        return new Date(context.readLong(field));
    }
}
