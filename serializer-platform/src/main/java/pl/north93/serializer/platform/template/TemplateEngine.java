package pl.north93.serializer.platform.template;

import java.lang.reflect.Type;

import pl.north93.serializer.platform.format.TypePredictor;
import pl.north93.serializer.platform.context.DeserializationContext;
import pl.north93.serializer.platform.context.SerializationContext;
import pl.north93.serializer.platform.reflect.ReflectionEngine;
import pl.north93.serializer.platform.template.filter.TemplateFilter;

public interface TemplateEngine extends ReflectionEngine
{
    boolean isNeedsDynamicResolution(Type type);

    boolean isTypePredictingSupported();

    TypePredictor<SerializationContext, DeserializationContext> getTypePredictor();

    void register(TemplateFilter filter, Template<?, ?, ?> template);

    Template<Object, SerializationContext, DeserializationContext> getTemplate(Type type);
}
