package pl.north93.serializer.platform.property;

public class NoInstantiationStrategyException extends RuntimeException
{
    public NoInstantiationStrategyException(final Class<?> type)
    {
        super("Not found any suitable instantiation strategy for " + type.getName());
    }
}
