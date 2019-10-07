package pl.north93.serializer.platform.reflect.impl;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import lombok.AllArgsConstructor;
import lombok.ToString;

@ToString
@AllArgsConstructor
/*default*/ class NorthParameterizedType implements ParameterizedType
{
    private final Class<?> clazz;
    private final Type[] parameters;

    @Override
    public Type[] getActualTypeArguments()
    {
        return this.parameters;
    }

    @Override
    public Type getRawType()
    {
        return this.clazz;
    }

    @Override
    public Type getOwnerType()
    {
        return null;
    }
}
