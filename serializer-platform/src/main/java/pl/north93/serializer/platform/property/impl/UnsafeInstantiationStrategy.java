package pl.north93.serializer.platform.property.impl;

import java.lang.reflect.Field;

import lombok.AllArgsConstructor;
import lombok.ToString;
import sun.misc.Unsafe;

@ToString
@AllArgsConstructor
class UnsafeInstantiationStrategy<T> implements InstantiationStrategy<T>
{
    private static final Unsafe UNSAFE;
    private final Class<T> clazz;

    @Override
    public T newInstance(final InstantiationParameters parameters)
    {
        try
        {
            return (T) UNSAFE.allocateInstance(this.clazz);
        }
        catch (final InstantiationException e)
        {
            throw new RuntimeException("Failed to instantiate object via Unsafe", e);
        }
    }

    static
    {
        try
        {
            final Field unsafeField = Unsafe.class.getDeclaredField("theUnsafe");
            unsafeField.setAccessible(true);
            UNSAFE = (Unsafe) unsafeField.get(null);
        }
        catch (final NoSuchFieldException | IllegalAccessException e)
        {
            throw new RuntimeException("Can't access Unsafe", e);
        }
    }
}
