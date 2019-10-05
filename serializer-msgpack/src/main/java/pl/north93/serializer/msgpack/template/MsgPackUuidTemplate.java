package pl.north93.serializer.msgpack.template;

import java.util.UUID;

import pl.north93.serializer.msgpack.MsgPackDeserializationContext;
import pl.north93.serializer.msgpack.MsgPackSerializationContext;
import pl.north93.serializer.platform.template.field.FieldInfo;
import pl.north93.serializer.platform.template.Template;

public class MsgPackUuidTemplate implements Template<UUID, MsgPackSerializationContext, MsgPackDeserializationContext>
{
    @Override
    public void serialise(final MsgPackSerializationContext context, final FieldInfo field, final UUID object) throws Exception
    {
        context.enterObject(field);

        context.writeLong(null, object.getMostSignificantBits());
        context.writeLong(null, object.getLeastSignificantBits());

        context.exitObject(field);
    }

    @Override
    public UUID deserialize(final MsgPackDeserializationContext context, final FieldInfo field) throws Exception
    {
        try
        {
            context.enterObject(field);
            return new UUID(context.readLong(null), context.readLong(null));
        }
        finally
        {
            context.exitObject(field);
        }
    }
}
