package pl.north93.serializer.platform.property.impl;

import java.util.function.Supplier;

import lombok.AllArgsConstructor;

/**
 * Allows to predefine instantiation strategy using constructor reference.
 * @param <T> Type of instantiated class.
 */
@AllArgsConstructor
class SupplierInstantiationStrategy<T> implements InstantiationStrategy<T>
{
    private final Supplier<T> constructorReference;

    @Override
    public T newInstance(final InstantiationParameters parameters)
    {
        return this.constructorReference.get();
    }
}
