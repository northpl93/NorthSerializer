package pl.north93.serializer.platform.reflect.impl;

import lombok.ToString;
import pl.north93.serializer.platform.reflect.InstanceCreator;

@ToString
/*default*/ class UnsafeCreator<T> implements InstanceCreator<T>
{
    private final Class<T> clazz;

    public UnsafeCreator(final Class<T> clazz)
    {
        this.clazz = clazz;
    }

    @SuppressWarnings("unchecked")
    @Override
    public T newInstance()
    {
        try
        {
            return (T) UnsafeAccess.getUnsafe().allocateInstance(this.clazz);
        }
        catch (final InstantiationException e)
        {
            throw new RuntimeException(e);
        }
    }
}
