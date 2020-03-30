package pl.north93.serializer.platform.property.impl;

import java.util.HashMap;
import java.util.Map;

import lombok.ToString;
import pl.north93.serializer.platform.property.ObjectProperty;

@ToString
final class InstantiationParameters
{
    private final Map<ObjectProperty, Object> parameters = new HashMap<>();

    public void putParameter(final ObjectProperty property, final Object value)
    {
        this.parameters.put(property, value);
    }

    /**
     * Gets value of a specified property and removes it from internal map.
     * Throws an exception when property has no assigned value.
     *
     * @param property The property whose value is to be pulled.
     * @return The value of specified property.
     */
    public Object pullValue(final ObjectProperty property)
    {
        if (this.parameters.containsKey(property))
        {
            // we MUST use containsKey, because there may be null values
            return this.parameters.remove(property);
        }

        throw new IllegalStateException(); //todo
    }

    public void putValuesIntoObject(final Object instance)
    {
        for (final Map.Entry<ObjectProperty, Object> entry : this.parameters.entrySet())
        {
            entry.getKey().setValue(instance, entry.getValue());
        }

        this.parameters.clear();
    }
}
