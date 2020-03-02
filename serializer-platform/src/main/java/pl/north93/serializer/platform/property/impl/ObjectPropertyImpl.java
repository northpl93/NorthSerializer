package pl.north93.serializer.platform.property.impl;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Type;
import java.util.Optional;

import lombok.ToString;
import pl.north93.serializer.platform.property.ObjectProperty;

@ToString(of = "field")
class ObjectPropertyImpl implements ObjectProperty
{
    private final Field field;

    public ObjectPropertyImpl(final Field field)
    {
        this.field = field;
    }

    @Override
    public String getName()
    {
        return this.field.getName();
    }

    @Override
    public Type getType()
    {
        return this.field.getType();
    }

    @Override
    public Type getGenericType()
    {
        return this.field.getGenericType();
    }

    @Override
    public <A extends Annotation> Optional<A> getAnnotation(final Class<A> type)
    {
        return Optional.ofNullable(this.field.getAnnotation(type));
    }

    @Override
    public <V> V getValue(final Object instance)
    {
        try
        {
            return (V) this.field.get(instance);
        }
        catch (final IllegalAccessException e)
        {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void setValue(final Object instance, final Object value)
    {
        try
        {
            this.field.set(instance, value);
        }
        catch (final IllegalAccessException e)
        {
            throw new RuntimeException(e);
        }
    }
}
