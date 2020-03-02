package pl.north93.serializer.platform.template.impl;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;
import pl.north93.serializer.platform.property.ObjectProperty;
import pl.north93.serializer.platform.template.Template;
import pl.north93.serializer.platform.template.field.FieldInfo;

@Getter
@ToString
@AllArgsConstructor
class TemplateElement
{
    private final FieldInfo fieldInfo;
    private final ObjectProperty property;
    private final Template template;
}
