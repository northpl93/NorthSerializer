package pl.north93.serializer.msgpack;

import javax.annotation.Nullable;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import pl.north93.serializer.msgpack.template.MsgPackArrayTemplate;
import pl.north93.serializer.msgpack.template.MsgPackListTemplate;
import pl.north93.serializer.msgpack.template.MsgPackMapTemplate;
import pl.north93.serializer.msgpack.template.MsgPackSetTemplate;
import pl.north93.serializer.msgpack.template.MsgPackUuidTemplate;
import pl.north93.serializer.platform.format.DeserializationConfiguration;
import pl.north93.serializer.platform.format.SerializationConfiguration;
import pl.north93.serializer.platform.format.SerializationFormat;
import pl.north93.serializer.platform.format.TypePredictor;
import pl.north93.serializer.platform.template.TemplateEngine;
import pl.north93.serializer.platform.template.filter.AnyInheritedTypeFilter;
import pl.north93.serializer.platform.template.filter.ExactTypeIgnoreGenericFilter;

public class MsgPackSerializationFormat implements SerializationFormat<byte[], byte[], MsgPackSerializationContext, MsgPackDeserializationContext>
{
    private final MsgPackSerializationConfiguration serializationConfiguration;
    private final MsgPackDeserializationConfiguration deserializationConfiguration;

    public MsgPackSerializationFormat()
    {
        this.serializationConfiguration = new MsgPackSerializationConfiguration();
        this.deserializationConfiguration = new MsgPackDeserializationConfiguration();
    }

    @Override
    public void configure(final TemplateEngine templateEngine)
    {
        templateEngine.register(new MsgPackArrayTemplate.ArrayTemplateFilter(), new MsgPackArrayTemplate());

        // zwykle typy
        templateEngine.register(new ExactTypeIgnoreGenericFilter(UUID.class), new MsgPackUuidTemplate());

        // kolekcje
        templateEngine.register(new AnyInheritedTypeFilter(List.class), new MsgPackListTemplate());
        templateEngine.register(new AnyInheritedTypeFilter(Set.class), new MsgPackSetTemplate());

        // mapy
        templateEngine.register(new AnyInheritedTypeFilter(Map.class), new MsgPackMapTemplate());
    }

    @Override
    public SerializationConfiguration<MsgPackSerializationContext> createDefaultSerializationConfig()
    {
        return this.serializationConfiguration;
    }

    @Override
    public DeserializationConfiguration<byte[], MsgPackDeserializationContext> createDefaultDeserializationConfig()
    {
        return this.deserializationConfiguration;
    }

    @Nullable
    @Override
    public TypePredictor<MsgPackSerializationContext, MsgPackDeserializationContext> getTypePredictor()
    {
        return null;
    }
}
