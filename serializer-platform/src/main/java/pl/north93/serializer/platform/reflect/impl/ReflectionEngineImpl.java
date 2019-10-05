package pl.north93.serializer.platform.reflect.impl;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Arrays;

import lombok.AllArgsConstructor;
import lombok.ToString;
import pl.north93.serializer.platform.reflect.ClassResolver;
import pl.north93.serializer.platform.reflect.InstanceCreator;
import pl.north93.serializer.platform.reflect.ReflectionEngine;

@ToString
@AllArgsConstructor
public class ReflectionEngineImpl implements ReflectionEngine
{
    private final ClassResolver classResolver;
    private final InstantiationManager instantiationManager = new InstantiationManager();

    @Override
    public Class<?> findClass(final String name)
    {
        return this.classResolver.findClass(name);
    }

    @Override
    public Class<?> getRawClassFromType(final Type type)
    {
        if (type instanceof Class)
        {
            return (Class<?>) type;
        }
        else if (type instanceof ParameterizedType)
        {
            final ParameterizedType parameterizedType = (ParameterizedType) type;
            return (Class<?>) parameterizedType.getRawType();
        }

        throw new IllegalArgumentException(type.getTypeName());
    }

    @Override
    public Type[] getTypeParameters(final Type type)
    {
        if (type instanceof Class)
        {
            final Class clazz = (Class) type;
            final Type[] types = new Type[clazz.getTypeParameters().length];
            Arrays.fill(types, Object.class);
            return types;
        }
        else if (type instanceof ParameterizedType)
        {
            final ParameterizedType parameterizedType = (ParameterizedType) type;
            return parameterizedType.getActualTypeArguments();
        }

        throw new IllegalArgumentException(type.getTypeName());
    }

    @Override
    public Type createParameterizedType(final Class clazz, final Type[] parameters)
    {
        return new NorthParameterizedType(clazz, parameters);
    }

    @Override
    public <T> InstanceCreator<T> getInstanceCreator(final Class<T> clazz)
    {
        return this.instantiationManager.getInstanceCreator(clazz);
    }
}
