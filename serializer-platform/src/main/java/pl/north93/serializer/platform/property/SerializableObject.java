package pl.north93.serializer.platform.property;

import java.util.Collection;

public interface SerializableObject<T>
{
    Collection<? extends ObjectProperty> getProperties();

    ObjectBuilder<T> createBuilder();
}
