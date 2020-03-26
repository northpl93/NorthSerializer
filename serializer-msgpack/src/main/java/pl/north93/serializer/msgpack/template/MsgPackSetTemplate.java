package pl.north93.serializer.msgpack.template;

import java.lang.reflect.Type;
import java.util.Set;

import org.msgpack.core.MessageBufferPacker;
import org.msgpack.core.MessageUnpacker;

import pl.north93.serializer.msgpack.MsgPackDeserializationContext;
import pl.north93.serializer.msgpack.MsgPackSerializationContext;
import pl.north93.serializer.platform.template.Template;
import pl.north93.serializer.platform.template.TemplateEngine;
import pl.north93.serializer.platform.template.field.CustomFieldInfo;
import pl.north93.serializer.platform.template.field.FieldInfo;

public class MsgPackSetTemplate implements Template<Set<Object>, MsgPackSerializationContext, MsgPackDeserializationContext>
{
    @Override
    public void serialise(final MsgPackSerializationContext context, final FieldInfo field, final Set object) throws Exception
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
    public Set<Object> deserialize(final MsgPackDeserializationContext context, final FieldInfo field) throws Exception
    {
        final MessageUnpacker unPacker = context.getUnPacker();

        final Type genericType = this.getGenericType(context.getTemplateEngine(), field.getType());
        final var objectSerializer = context.getTemplateEngine().getTemplate(genericType);

        final FieldInfo listFieldInfo = this.createListFieldInfo(genericType);
        final Set<Object> objects = this.instantiateSet(context.getTemplateEngine(), field.getType());

        final int amount = unPacker.unpackArrayHeader();
        for (int i = 0; i < amount; i++)
        {
            objects.add(objectSerializer.deserialize(context, listFieldInfo));
        }

        return objects;
    }

    @SuppressWarnings("unchecked")
    private Set<Object> instantiateSet(final TemplateEngine templateEngine, final Type type)
    {
        final Class<Set<Object>> setClass = (Class<Set<Object>>) templateEngine.getRawClassFromType(type);
        return templateEngine.instantiateClass(setClass);
    }

    private FieldInfo createListFieldInfo(final Type type)
    {
        return new CustomFieldInfo(null, type);
    }

    private Type getGenericType(final TemplateEngine templateEngine, final Type type)
    {
        return templateEngine.getGenericParameters(type)[0];
    }
}
