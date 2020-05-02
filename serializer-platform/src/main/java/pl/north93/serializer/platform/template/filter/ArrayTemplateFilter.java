package pl.north93.serializer.platform.template.filter;

import java.lang.reflect.Type;

import pl.north93.serializer.platform.template.TemplateEngine;
import pl.north93.serializer.platform.template.TemplatePriority;

public final class ArrayTemplateFilter implements TemplateFilter
{
    @Override
    public int getPriority()
    {
        return TemplatePriority.NORMAL;
    }

    @Override
    public boolean isApplicableTo(final TemplateEngine templateEngine, final Type type)
    {
        final Class<?> clazz = templateEngine.getRawClassFromType(type);
        return clazz.isArray();
    }
}