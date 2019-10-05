package pl.north93.serializer.platform.template.impl;

import java.lang.reflect.Field;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import pl.north93.serializer.platform.template.ITemplateElement;
import pl.north93.serializer.platform.template.Template;
import pl.north93.serializer.platform.template.field.FieldInfo;

/*default*/ class TemplateElementFactory
{
    private final boolean useCompiledElements;

    public TemplateElementFactory(final boolean useCompiledElements)
    {
        this.useCompiledElements = useCompiledElements;
    }

    public ITemplateElement getTemplateElement(final Field field, final FieldInfo fieldInfo, final Template template)
    {
        if (this.useCompiledElements)
        {
            return null;
            //return ClassGenerator.INSTANCE.getTemplateElement(clazz, field, template);
        }
        else
        {
            return new MethodHandleTemplateElement(field, fieldInfo, template);
        }
    }

    @Override
    public String toString()
    {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString()).append("useCompiledElements", this.useCompiledElements).toString();
    }
}
