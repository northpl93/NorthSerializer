package pl.north93.serializer.msgpack.template;

import org.msgpack.core.MessageBufferPacker;
import org.msgpack.core.MessageUnpacker;

import pl.north93.serializer.msgpack.MsgPackDeserializationContext;
import pl.north93.serializer.msgpack.MsgPackSerializationContext;
import pl.north93.serializer.platform.template.Template;
import pl.north93.serializer.platform.template.field.FieldInfo;

public class MsgPackFastByteArrayTemplate implements Template<byte[], MsgPackSerializationContext, MsgPackDeserializationContext>
{
    @Override
    public void serialise(final MsgPackSerializationContext context, final FieldInfo field, final byte[] array) throws Exception
    {
        final MessageBufferPacker packer = context.getPacker();

        packer.packArrayHeader(array.length);
        packer.addPayload(array);
    }

    @Override
    public byte[] deserialize(final MsgPackDeserializationContext context, final FieldInfo field) throws Exception
    {
        final MessageUnpacker unPacker = context.getUnPacker();

        final int size = unPacker.unpackArrayHeader();
        return unPacker.readPayload(size);
    }
}
