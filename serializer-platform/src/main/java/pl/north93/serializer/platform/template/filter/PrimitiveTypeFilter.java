package pl.north93.serializer.platform.template.filter;

import java.lang.reflect.Type;

import lombok.ToString;
import pl.north93.serializer.platform.template.TemplateEngine;
import pl.north93.serializer.platform.template.TemplatePriority;

@ToString
public final class PrimitiveTypeFilter implements TemplateFilter
{
    private final Class<?> primitiveType;
    private final Class<?> objectType;

    public PrimitiveTypeFilter(final Class<?> primitiveType, final Class<?> objectType)
    {
        this.primitiveType = primitiveType;
        this.objectType = objectType;
    }

    @Override
    public int getPriority()
    {
        return TemplatePriority.NORMAL;
    }

    @Override
    public boolean isApplicableTo(final TemplateEngine templateEngine, final Type type)
    {
        return type == this.primitiveType || type == this.objectType;
    }
}
