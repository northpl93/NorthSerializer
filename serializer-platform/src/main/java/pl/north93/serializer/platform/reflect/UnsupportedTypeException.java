package pl.north93.serializer.platform.reflect;

import java.lang.reflect.Type;

import lombok.Getter;

@Getter
public class UnsupportedTypeException extends RuntimeException
{
    private final Type type;

    public UnsupportedTypeException(final Type type)
    {
        super(type.getTypeName());
        this.type = type;
    }
}
