package pl.north93.serializer.platform.template.filter;

import java.lang.reflect.Type;

import lombok.ToString;
import pl.north93.serializer.platform.template.TemplateEngine;
import pl.north93.serializer.platform.template.TemplatePriority;

@ToString
public final class AnyInheritedTypeFilter implements TemplateFilter
{
    private final Class<?> type;

    public AnyInheritedTypeFilter(final Class<?> type)
    {
        this.type = type;
    }

    @Override
    public int getPriority()
    {
        return TemplatePriority.HIGH;
    }

    @Override
    public boolean isApplicableTo(final TemplateEngine templateEngine, final Type type)
    {
        final Class<?> other = templateEngine.getRawClassFromType(type);
        return this.type.isAssignableFrom(other);
    }
}
