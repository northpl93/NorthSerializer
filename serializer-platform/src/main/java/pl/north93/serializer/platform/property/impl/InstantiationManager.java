package pl.north93.serializer.platform.property.impl;

import static java.lang.reflect.Modifier.isAbstract;
import static java.lang.reflect.Modifier.isStatic;
import static java.lang.reflect.Modifier.isTransient;


import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Parameter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.WeakHashMap;

import lombok.ToString;
import pl.north93.serializer.platform.annotations.NorthTransient;
import pl.north93.serializer.platform.property.NoInstantiationStrategyException;
import pl.north93.serializer.platform.property.ObjectProperty;
import pl.north93.serializer.platform.property.SerializableObject;

@ToString
public class InstantiationManager
{
    private final Map<Class<?>, InstantiationStrategy<?>> instantiationStrategies = new WeakHashMap<>();
    private final Map<Class<?>, SerializableObjectImpl<?>> serializableObjects = new WeakHashMap<>();

    public <T> T instantiateClass(final Class<T> clazz)
    {
        final InstantiationStrategy<T> strategy = this.getInstantiationStrategy(clazz, PropertiesList.getEmpty());
        return strategy.newInstance(new InstantiationParameters());
    }

    @SuppressWarnings("unchecked")
    public <T> SerializableObject<T> getSerializableObject(final Class<T> clazz)
    {
        return (SerializableObject<T>) this.serializableObjects.computeIfAbsent(clazz, this::createSerializableObject);
    }

    private <T> SerializableObjectImpl<T> createSerializableObject(final Class<T> clazz)
    {
        final PropertiesList propertiesList = PropertiesList.createPropertiesList(clazz, this::shouldSkipField);
        final InstantiationStrategy<T> instantiationStrategy = this.getInstantiationStrategy(clazz, propertiesList);

        return new SerializableObjectImpl<>(propertiesList, instantiationStrategy);
    }

    private boolean shouldSkipField(final Field field)
    {
        // Skip fields with NorthTransient annotation
        // Ignore static and transient fields
        final int modifiers = field.getModifiers();
        return field.isAnnotationPresent(NorthTransient.class) || isTransient(modifiers) || isStatic(modifiers);
    }

    @SuppressWarnings("unchecked")
    private <T> InstantiationStrategy<T> getInstantiationStrategy(final Class<T> clazz, final PropertiesList propertiesList)
    {
        if (clazz.isInterface() || isAbstract(clazz.getModifiers()))
        {
            throw new IllegalArgumentException("There is no way to instantiate interface or abstract class");
        }

        return (InstantiationStrategy<T>) this.instantiationStrategies.computeIfAbsent(clazz, targetClass ->
        {
            return this.createInstantiationStrategy(targetClass, propertiesList);
        });
    }

    private <T> InstantiationStrategy<T> createInstantiationStrategy(final Class<T> clazz, final PropertiesList propertiesList)
    {
        for (final Constructor<?> constructor : clazz.getConstructors())
        {
            final InstantiationStrategy<T> strategy = this.createStrategyForConstructor(propertiesList, constructor);
            if (strategy == null)
            {
                continue;
            }

            return strategy;
        }

        throw new NoInstantiationStrategyException(clazz);
    }

    private <T> InstantiationStrategy<T> createStrategyForConstructor(final PropertiesList propertiesList, final Constructor constructor)
    {
        final List<ObjectProperty> properties = new ArrayList<>();
        for (final Parameter parameter : constructor.getParameters())
        {
            final ObjectPropertyImpl property = propertiesList.matchPropertyToParameter(parameter);
            if (property == null)
            {
                return null;
            }

            properties.add(property);
        }

        return new ConstructorInstantiationStrategy<>((Constructor<T>) constructor, properties);
    }
}
