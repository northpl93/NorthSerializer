package pl.north93.serializer.platform.property;

public interface ObjectBuilder<T>
{
    void set(ObjectProperty property, Object value);

    T instantiate();
}
