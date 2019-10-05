package pl.north93.serializer.platform.format;

import javax.annotation.Nullable;

import java.lang.reflect.Type;

import pl.north93.serializer.platform.context.DeserializationContext;
import pl.north93.serializer.platform.context.SerializationContext;
import pl.north93.serializer.platform.template.Template;
import pl.north93.serializer.platform.template.TemplateEngine;
import pl.north93.serializer.platform.template.field.FieldInfo;

public interface TypePredictor<S extends SerializationContext, D extends DeserializationContext>
{
    boolean isTypePredictable(TemplateEngine templateEngine, Type type);

    @Nullable
    Template<Object, S, D> predictType(D deserializationContext, FieldInfo field);
}
