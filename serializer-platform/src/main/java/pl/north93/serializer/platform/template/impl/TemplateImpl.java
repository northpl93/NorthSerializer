package pl.north93.serializer.platform.template.impl;

import java.util.List;

import lombok.ToString;
import pl.north93.serializer.platform.context.DeserializationContext;
import pl.north93.serializer.platform.context.SerializationContext;
import pl.north93.serializer.platform.property.ObjectBuilder;
import pl.north93.serializer.platform.property.SerializableObject;
import pl.north93.serializer.platform.template.Template;
import pl.north93.serializer.platform.template.field.FieldInfo;

@ToString
@SuppressWarnings("unchecked")
/*default*/ class TemplateImpl<T> implements Template<T, SerializationContext, DeserializationContext>
{
    private final SerializableObject<T> serializableObject;
    private final List<TemplateElement> structure;

    public TemplateImpl(final SerializableObject<T> serializableObject, final List<TemplateElement> structure)
    {
        this.serializableObject = serializableObject;
        this.structure = structure;
    }

    @Override
    public void serialise(final SerializationContext context, final FieldInfo field, final T object) throws Exception
    {
        context.enterObject(field);
        for (final TemplateElement templateElement : this.structure)
        {
            final Object value = templateElement.getProperty().getValue(object);
            if (value != null)
            {
                final Template template = templateElement.getTemplate();
                template.serialise(context, templateElement.getFieldInfo(), value);
            }
            else
            {
                context.writeNull(templateElement.getFieldInfo());
            }
        }
        context.exitObject(field);
    }

    @Override
    public T deserialize(final DeserializationContext context, final FieldInfo field) throws Exception
    {
        context.enterObject(field);
        try
        {
            final ObjectBuilder<T> builder = this.serializableObject.createBuilder();
            for (final TemplateElement templateElement : this.structure)
            {
                if (context.trySkipNull(templateElement.getFieldInfo()))
                {
                    continue; // next value is null so we not try to deserialize it
                }

                final Template template = templateElement.getTemplate();
                builder.set(templateElement.getProperty(), template.deserialize(context, templateElement.getFieldInfo()));
            }

            return builder.instantiate();
        }
        catch (final Exception e)
        {
            throw new RuntimeException("Exception thrown while processing template " + this, e);
        }
        finally
        {
            context.exitObject(field);
        }
    }
}
