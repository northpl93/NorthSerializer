package pl.north93.serializer.msgpack.template;

import java.lang.reflect.Type;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang3.tuple.Pair;
import org.msgpack.core.MessageBufferPacker;
import org.msgpack.core.MessageUnpacker;

import pl.north93.serializer.msgpack.MsgPackDeserializationContext;
import pl.north93.serializer.msgpack.MsgPackSerializationContext;
import pl.north93.serializer.platform.context.DeserializationContext;
import pl.north93.serializer.platform.context.SerializationContext;
import pl.north93.serializer.platform.template.Template;
import pl.north93.serializer.platform.template.TemplateEngine;
import pl.north93.serializer.platform.template.field.CustomFieldInfo;
import pl.north93.serializer.platform.template.field.FieldInfo;

public class MsgPackMapTemplate implements Template<Map<Object, Object>, MsgPackSerializationContext, MsgPackDeserializationContext>
{
    @Override
    public void serialise(final MsgPackSerializationContext context, final FieldInfo field, final Map<Object, Object> object) throws Exception
    {
        final MessageBufferPacker packer = context.getPacker();
        final TemplateEngine templateEngine = context.getTemplateEngine();

        final Set<Map.Entry<Object, Object>> entries = object.entrySet();
        packer.packMapHeader(entries.size());

        final Pair<Type, Type> mapTypes = this.getMapTypes(templateEngine, field);

        final var keyTemplate = templateEngine.getTemplate(mapTypes.getKey());
        final var valueTemplate = templateEngine.getTemplate(mapTypes.getValue());

        for (final Map.Entry<Object, Object> entry : entries)
        {
            final CustomFieldInfo keyField = new CustomFieldInfo(null, mapTypes.getKey());
            keyTemplate.serialise(context, keyField, entry.getKey());

            final CustomFieldInfo valueField = new CustomFieldInfo(null, mapTypes.getValue());
            valueTemplate.serialise(context, valueField, entry.getValue());
        }
    }

    @Override
    public Map<Object, Object> deserialize(final MsgPackDeserializationContext context, final FieldInfo field) throws Exception
    {
        final MessageUnpacker unPacker = context.getUnPacker();
        final TemplateEngine templateEngine = context.getTemplateEngine();

        final int mapSize = unPacker.unpackMapHeader();
        final Map<Object, Object> map = this.instantiateMap(templateEngine, field.getType());

        final Pair<Type, Type> mapTypes = this.getMapTypes(templateEngine, field);

        final var keyTemplate = templateEngine.getTemplate(mapTypes.getKey());
        final var valueTemplate = templateEngine.getTemplate(mapTypes.getValue());

        for (int i = 0; i < mapSize; i++)
        {
            final CustomFieldInfo keyField = new CustomFieldInfo(null, mapTypes.getKey());
            final Object key = keyTemplate.deserialize(context, keyField);

            final CustomFieldInfo valueField = new CustomFieldInfo(null, mapTypes.getValue());
            final Object value = valueTemplate.deserialize(context, valueField);

            map.put(key, value);
        }

        return map;
    }

    private Pair<Type, Type> getMapTypes(final TemplateEngine templateEngine, final FieldInfo field)
    {
        final Type[] typeParameters = templateEngine.getTypeParameters(field.getType());
        return Pair.of(typeParameters[0], typeParameters[1]);
    }

    @SuppressWarnings("unchecked")
    private Map<Object, Object> instantiateMap(final TemplateEngine engine, final Type type)
    {
        final Class<Map<Object, Object>> mapClass = (Class<Map<Object, Object>>) engine.getRawClassFromType(type);
        return engine.instantiateClass(mapClass);
    }
}
