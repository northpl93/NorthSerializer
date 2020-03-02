package pl.north93.serializer.platform.property;

import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import java.util.Optional;

public interface ObjectProperty
{
    String getName();

    Type getType();

    Type getGenericType();

    <T extends Annotation> Optional<T> getAnnotation(Class<T> type);

    <V> V getValue(Object instance);

    void setValue(Object instance, Object value);
}
