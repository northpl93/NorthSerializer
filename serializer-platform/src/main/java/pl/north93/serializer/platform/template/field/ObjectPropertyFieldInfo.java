package pl.north93.serializer.platform.template.field;

import java.lang.reflect.Type;
import java.util.Optional;

import lombok.ToString;
import pl.north93.serializer.platform.annotations.NorthField;
import pl.north93.serializer.platform.property.ObjectProperty;

@ToString
public final class ObjectPropertyFieldInfo implements FieldInfo
{
    private final String name;
    private final Type type;

    public ObjectPropertyFieldInfo(final ObjectProperty property)
    {
        final Optional<NorthField> customizations = property.getAnnotation(NorthField.class);

        this.name = computeName(property, customizations);
        this.type = computeType(property, customizations);
    }

    @Override
    public String getName()
    {
        return this.name;
    }

    @Override
    public Type getType()
    {
        return this.type;
    }

    private static String computeName(final ObjectProperty property, final Optional<NorthField> customizations)
    {
        return customizations.map(_customizations ->
        {
            final String customName = _customizations.name();
            if (customName.equals(NorthField.Default.DEFAULT_STRING))
            {
                return property.getName();
            }

            return customName;
        }).orElseGet(property::getName);
    }

    private static Type computeType(final ObjectProperty property, final Optional<NorthField> customizations)
    {
        return customizations.map(_customizations ->
        {
            final Class<?> customType = _customizations.type();
            if (customType.equals(NorthField.Default.class))
            {
                return property.getGenericType();
            }

            return customType;
        }).orElseGet(property::getGenericType);
    }
}
