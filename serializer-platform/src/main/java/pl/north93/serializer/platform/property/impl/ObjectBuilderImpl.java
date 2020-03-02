package pl.north93.serializer.platform.property.impl;

import lombok.ToString;
import pl.north93.serializer.platform.property.ObjectBuilder;
import pl.north93.serializer.platform.property.ObjectProperty;

@ToString
class ObjectBuilderImpl<T> implements ObjectBuilder<T>
{
    private final SerializableObjectImpl<T> serializableObject;
    private final InstantiationParameters parameters = new InstantiationParameters();

    public ObjectBuilderImpl(final SerializableObjectImpl<T> serializableObject)
    {
        this.serializableObject = serializableObject;
    }

    @Override
    public void set(final ObjectProperty property, final Object value)
    {
        this.parameters.putParameter(property, value);
    }

    @Override
    public T instantiate()
    {
        final InstantiationStrategy<T> instantiationStrategy = this.serializableObject.getInstantiationStrategy();
        final T newInstance = instantiationStrategy.newInstance(this.parameters);

        this.parameters.putValuesIntoObject(newInstance);
        return newInstance;
    }
}
