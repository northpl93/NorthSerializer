package pl.north93.serializer.platform.property.impl;

interface InstantiationStrategy<T>
{
    T newInstance(InstantiationParameters parameters);
}