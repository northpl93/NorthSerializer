package pl.north93.serializer.platform.reflect;

import java.lang.reflect.Type;

public interface ReflectionEngine
{
    /**
     * Szuka klasy o podanej nazwie.
     *
     * @param name Nazwa klasy.
     *
     * @return Klasa o podanej nazwie.
     */
    Class<?> findClass(String name);

    /**
     * Próbuje przeksztalcic podany Type na Class.
     * Spowoduje to ewentualną ukratę typu generycznego.
     *
     * @param type Type do skonwertowania na Class.
     *
     * @return Class pobrane z danego Type.
     */
    Class<?> getRawClassFromType(Type type);

    Type[] getTypeParameters(Type type);

    Type createParameterizedType(Class clazz, Type[] parameters);

    <T> InstanceCreator<T> getInstanceCreator(Class<T> clazz);

    default <T> T instantiateClass(final Class<T> clazz)
    {
        final InstanceCreator<T> instanceCreator = this.getInstanceCreator(clazz);
        return instanceCreator.newInstance();
    }
}
