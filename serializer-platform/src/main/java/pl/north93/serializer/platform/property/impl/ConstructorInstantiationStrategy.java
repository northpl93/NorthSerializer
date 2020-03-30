package pl.north93.serializer.platform.property.impl;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.ToString;
import pl.north93.serializer.platform.property.ObjectProperty;

@ToString
@AllArgsConstructor
class ConstructorInstantiationStrategy<T> implements InstantiationStrategy<T>
{
    private final Constructor<T> constructor;
    private final List<ObjectProperty> properties;

    @Override
    public T newInstance(final InstantiationParameters parameters)
    {
        final Object[] arguments = new Object[this.properties.size()];

        for (int i = 0; i < this.properties.size(); i++)
        {
            final ObjectProperty property = this.properties.get(i);
            arguments[i] = parameters.pullValue(property);
        }

        try
        {
            return this.constructor.newInstance(arguments);
        }
        catch (final InstantiationException | IllegalAccessException | InvocationTargetException e)
        {
            throw new IllegalStateException(e); // todo
        }
    }
}
