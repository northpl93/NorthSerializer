package pl.north93.serializer.platform.reflect.impl;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;

import lombok.ToString;
import pl.north93.serializer.platform.property.SerializableObject;
import pl.north93.serializer.platform.property.impl.InstantiationManager;
import pl.north93.serializer.platform.reflect.ClassResolver;
import pl.north93.serializer.platform.reflect.ReflectionEngine;
import pl.north93.serializer.platform.reflect.UnsupportedTypeException;

@ToString
public class ReflectionEngineImpl implements ReflectionEngine
{
    private final ClassResolver classResolver;
    private final InstantiationManager instantiationManager = new InstantiationManager();

    public ReflectionEngineImpl(final ClassResolver classResolver)
    {
        this.classResolver = classResolver;

        // register some predefined instantiators for common Java types
        this.instantiationManager.registerPredefinedInstantiator(ArrayList.class, ArrayList::new);
        this.instantiationManager.registerPredefinedInstantiator(HashSet.class, HashSet::new);
        this.instantiationManager.registerPredefinedInstantiator(HashMap.class, HashMap::new);
    }

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

        throw new UnsupportedTypeException(type);
    }

    @Override
    public Type[] getGenericParameters(final Type type)
    {
        if (type instanceof Class)
        {
            final Class<?> clazz = (Class<?>) type;
            final Type[] types = new Type[clazz.getTypeParameters().length];
            Arrays.fill(types, Object.class);
            return types;
        }
        else if (type instanceof ParameterizedType)
        {
            final ParameterizedType parameterizedType = (ParameterizedType) type;
            return parameterizedType.getActualTypeArguments();
        }

        throw new UnsupportedTypeException(type);
    }

    @Override
    public ParameterizedType createParameterizedType(final Class<?> clazz, final Type[] parameters)
    {
        if (clazz.getTypeParameters().length != parameters.length)
        {
            // For example, we can't pass Map.class, [Integer] into this method because Map requires two parameters.
            throw new IllegalArgumentException("Amount of specified generic parameters doesn't match amount of class parameters.");
        }

        return new NorthParameterizedType(clazz, parameters);
    }

    @Override
    public <T> T instantiateClass(final Class<T> clazz)
    {
        return this.instantiationManager.instantiateClass(clazz);
    }

    @Override
    public <T> SerializableObject<T> getSerializableObjectFrom(final Class<T> clazz)
    {
        return this.instantiationManager.getSerializableObject(clazz);
    }
}
