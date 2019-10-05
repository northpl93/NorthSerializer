package pl.north93.serializer.platform.template.field;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

import java.lang.reflect.Type;

@Getter
@ToString
@AllArgsConstructor
public final class CustomFieldInfo implements FieldInfo
{
    private final String name;
    private final Type   type;
}
