package pl.north93.serializer.msgpack;

import pl.north93.serializer.platform.format.DeserializationConfiguration;
import pl.north93.serializer.platform.template.TemplateEngine;

public class MsgPackDeserializationConfiguration implements DeserializationConfiguration<byte[], MsgPackDeserializationContext>
{
    @Override
    public MsgPackDeserializationContext createContext(final TemplateEngine templateEngine, final byte[] serializedData)
    {
        return new MsgPackDeserializationContext(templateEngine, serializedData);
    }
}
