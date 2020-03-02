package pl.north93.serializer.platform.reflect;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import pl.north93.serializer.platform.property.SerializableObject;

public interface ReflectionEngine
{
    /**
     * Looks for a class with specified name.
     * Uses {@link ClassResolver}.
     *
     * @param name Name of class.
     * @return Class with specified name.
     */
    Class<?> findClass(String name);

    /**
     * Converts specified Type into Class.
     * Causes loss of a generic parameters.
     *
     * @param type {@link Type} to convert to a {@link Class}.
     * @return Class from specified Type.
     */
    Class<?> getRawClassFromType(Type type);

    /**
     * Tries to fetch generic parameters from specified Type.
     * If there is no information about generic parameters, it will return array of Object.
     *
     * @param type Type to fetch parameters from.
     * @return Generic parameters of specified Type.
     */
    Type[] getGenericParameters(Type type);

    /**
     * Creates new instance of Type with specified generic parameters.
     *
     * @param clazz Class with generic parameters.
     * @param parameters Values of generic parameters.
     * @return New instance of Type with specified generic information.
     */
    ParameterizedType createParameterizedType(Class<?> clazz, Type... parameters);

    <T> T instantiateClass(Class<T> clazz);

    <T> SerializableObject<T> getSerializableObjectFrom(Class<T> clazz);
}
