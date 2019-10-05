package pl.north93.serializer.platform.reflect.impl;

import pl.north93.serializer.platform.reflect.ClassResolver;

public class DefaultClassResolver implements ClassResolver
{
    @Override
    public Class<?> findClass(final String name)
    {
        try
        {
            return Class.forName(name);
        }
        catch (final ClassNotFoundException e)
        {
            throw new RuntimeException("Serializer can't find " + name + " class", e);
        }
    }
}
