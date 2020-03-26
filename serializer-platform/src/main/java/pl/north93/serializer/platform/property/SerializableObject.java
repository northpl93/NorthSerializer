package pl.north93.serializer.platform.property;

import java.util.Collection;

/**
 * Represents Java class in more abstracted way.
 */
public interface SerializableObject<T>
{
    /**
     * Returns list of properties in this serializable object.
     * Please note, that properties don't have to represent a class's field!
     */
    Collection<? extends ObjectProperty> getProperties();

    ObjectBuilder<T> createBuilder();
}
