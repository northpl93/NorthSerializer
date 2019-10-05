package pl.north93.serializer.msgpack;

import org.msgpack.core.MessageBufferPacker;
import org.msgpack.core.MessagePack;

import pl.north93.serializer.platform.format.SerializationConfiguration;
import pl.north93.serializer.platform.template.TemplateEngine;

public class MsgPackSerializationConfiguration implements SerializationConfiguration<MsgPackSerializationContext>
{
    @Override
    public MsgPackSerializationContext createContext(final TemplateEngine templateEngine)
    {
        final MessageBufferPacker packer = MessagePack.newDefaultBufferPacker();
        return new MsgPackSerializationContext(templateEngine, packer);
    }
}
