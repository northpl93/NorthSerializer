package pl.north93.serializer.msgpack.template;

import java.lang.reflect.Type;
import java.util.List;

import org.msgpack.core.MessageBufferPacker;
import org.msgpack.core.MessageUnpacker;

import pl.north93.serializer.msgpack.MsgPackDeserializationContext;
import pl.north93.serializer.msgpack.MsgPackSerializationContext;
import pl.north93.serializer.platform.template.Template;
import pl.north93.serializer.platform.template.TemplateEngine;
import pl.north93.serializer.platform.template.field.CustomFieldInfo;
import pl.north93.serializer.platform.template.field.FieldInfo;

public class MsgPackListTemplate implements Template<List<Object>, MsgPackSerializationContext, MsgPackDeserializationContext>
{
    @Override
    public void serialise(final MsgPackSerializationContext context, final FieldInfo field, final List object) throws Exception
    {
        final MessageBufferPacker packer = context.getPacker();

        final Type genericType = this.getGenericType(context.getTemplateEngine(), field.getType());
        final var objectSerializer = context.getTemplateEngine().getTemplate(genericType);

        final FieldInfo listFieldInfo = this.createListFieldInfo(genericType);

        packer.packArrayHeader(object.size());
        for (final Object entry : object)
        {
            objectSerializer.serialise(context, listFieldInfo, entry);
        }
    }

    @Override
    public List<Object> deserialize(final MsgPackDeserializationContext context, final FieldInfo field) throws Exception
    {
        final MessageUnpacker unPacker = context.getUnPacker();

        final Type genericType = this.getGenericType(context.getTemplateEngine(), field.getType());
        final var objectSerializer = context.getTemplateEngine().getTemplate(genericType);

        final FieldInfo listFieldInfo = this.createListFieldInfo(genericType);
        final List<Object> objects = this.instantiateList(context.getTemplateEngine(), field.getType());

        final int amount = unPacker.unpackArrayHeader();
        for (int i = 0; i < amount; i++)
        {
            objects.add(i, objectSerializer.deserialize(context, listFieldInfo));
        }

        return objects;
    }

    @SuppressWarnings("unchecked")
    private List<Object> instantiateList(final TemplateEngine templateEngine, final Type type)
    {
        final Class<List<Object>> listClass = (Class<List<Object>>) templateEngine.getRawClassFromType(type);
        return templateEngine.instantiateClass(listClass);
    }

    private FieldInfo createListFieldInfo(final Type type)
    {
        return new CustomFieldInfo(null, type);
    }

    private Type getGenericType(final TemplateEngine templateEngine, final Type type)
    {
        return templateEngine.getTypeParameters(type)[0];
    }
}
