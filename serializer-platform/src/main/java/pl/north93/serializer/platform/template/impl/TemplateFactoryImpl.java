package pl.north93.serializer.platform.template.impl;

import java.util.LinkedList;
import java.util.List;

import lombok.ToString;
import pl.north93.serializer.platform.annotations.NorthCustomTemplate;
import pl.north93.serializer.platform.property.ObjectProperty;
import pl.north93.serializer.platform.property.SerializableObject;
import pl.north93.serializer.platform.template.Template;
import pl.north93.serializer.platform.template.TemplateEngine;
import pl.north93.serializer.platform.template.TemplateFactory;
import pl.north93.serializer.platform.template.field.ObjectPropertyFieldInfo;
import pl.north93.serializer.platform.template.filter.ExactTypeIgnoreGenericFilter;

@ToString(onlyExplicitlyIncluded = true)
/*default*/ class TemplateFactoryImpl implements TemplateFactory
{
    @Override
    public <T> Template<T, ?, ?> createAndRegisterTemplate(final TemplateEngine templateEngine, final Class<T> clazz)
    {
        final NorthCustomTemplate customTemplate = clazz.getAnnotation(NorthCustomTemplate.class);
        if (customTemplate != null)
        {
            @SuppressWarnings("unchecked")
            final Template<T, ?, ?> template = templateEngine.instantiateClass(customTemplate.value());
            return this.register(templateEngine, clazz, template);
        }

        return this.generateAndRegisterTemplate(templateEngine, clazz);
    }

    private <T> Template<T, ?, ?> generateAndRegisterTemplate(final TemplateEngine templateEngine, final Class<T> clazz)
    {
        final List<TemplateElement> elements = new LinkedList<>();
        final SerializableObject<T> serializableObject = templateEngine.getSerializableObjectFrom(clazz);

        final TemplateImpl<T> template = new TemplateImpl<>(serializableObject, elements);
        this.register(templateEngine, clazz, template);

        for (final ObjectProperty property : serializableObject.getProperties())
        {
            final ObjectPropertyFieldInfo fieldInfo = new ObjectPropertyFieldInfo(property);

            final var fieldTemplate = this.getTemplateForProperty(templateEngine, property);
            elements.add(new TemplateElement(fieldInfo, property, fieldTemplate));
        }

        return template;
    }

    private Template getTemplateForProperty(final TemplateEngine templateEngine, final ObjectProperty property)
    {
        return property.getAnnotation(NorthCustomTemplate.class).map(northCustomTemplate ->
        {
            return (Template) templateEngine.instantiateClass(northCustomTemplate.value());
        }).orElseGet(() ->
        {
            return templateEngine.getTemplate(property.getType());
        });
    }

    private <T> Template<T, ?, ?> register(final TemplateEngine templateEngine, final Class<?> clazz, final Template<T, ?, ?> template)
    {
        templateEngine.register(new ExactTypeIgnoreGenericFilter(clazz), template);
        return template;
    }
}
