package pl.north93.serializer.platform.property.impl;

import java.util.Collection;

import lombok.ToString;
import pl.north93.serializer.platform.property.ObjectBuilder;
import pl.north93.serializer.platform.property.ObjectProperty;
import pl.north93.serializer.platform.property.SerializableObject;

@ToString
class SerializableObjectImpl<T> implements SerializableObject<T>
{
    private final PropertiesList properties;
    private final InstantiationStrategy<T> instantiationStrategy;

    public SerializableObjectImpl(final PropertiesList properties, final InstantiationStrategy<T> instantiationStrategy)
    {
        this.properties = properties;
        this.instantiationStrategy = instantiationStrategy;
    }

    @Override
    public Collection<? extends ObjectProperty> getProperties()
    {
        return this.properties.getProperties();
    }

    public InstantiationStrategy<T> getInstantiationStrategy()
    {
        return this.instantiationStrategy;
    }

    @Override
    public ObjectBuilder<T> createBuilder()
    {
        return new ObjectBuilderImpl<>(this);
    }
}
