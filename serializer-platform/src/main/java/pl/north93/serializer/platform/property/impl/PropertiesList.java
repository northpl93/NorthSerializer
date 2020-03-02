package pl.north93.serializer.platform.property.impl;

import java.lang.reflect.Field;
import java.lang.reflect.Parameter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.function.Function;

import org.apache.commons.lang3.reflect.FieldUtils;

import lombok.ToString;

@ToString
class PropertiesList
{
    private static final PropertiesList EMPTY = new PropertiesList(Collections.emptyList());
    private final Collection<ObjectPropertyImpl> properties;

    private PropertiesList(final Collection<ObjectPropertyImpl> properties)
    {
        this.properties = Collections.unmodifiableCollection(properties);
    }

    public static PropertiesList getEmpty()
    {
        return EMPTY;
    }

    public static PropertiesList createPropertiesList(final Class<?> clazz, final Function<Field, Boolean> filter)
    {
        final List<ObjectPropertyImpl> properties = new ArrayList<>();

        for (final Field field : FieldUtils.getAllFields(clazz))
        {
            field.setAccessible(true);
            if (filter.apply(field))
            {
                continue;
            }

            properties.add(new ObjectPropertyImpl(field));
        }

        return new PropertiesList(properties);
    }

    public Collection<? extends ObjectPropertyImpl> getProperties()
    {
        return this.properties;
    }

    public ObjectPropertyImpl matchPropertyToParameter(final Parameter parameter)
    {
        for (final ObjectPropertyImpl property : this.properties)
        {
            if (parameter.getType().equals(property.getType()) && parameter.getName().equals(property.getName()))
            {
                return property;
            }
        }

        return null;
    }
}
