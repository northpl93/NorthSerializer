package pl.north93.serializer.platform.reflect.impl;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.UUID;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import pl.north93.serializer.platform.property.NoInstantiationStrategyException;
import pl.north93.serializer.platform.reflect.ReflectionEngine;

public class ReflectionEngineImplTest
{
    private final ReflectionEngine reflectionEngine = new ReflectionEngineImpl(new DefaultClassResolver());

    // helper field used in tests
    private static final Map<String, String> field = null;

    private Type getTypeOfHelperField()
    {
        try
        {
            final Field field = ReflectionEngineImplTest.class.getDeclaredField("field");
            return field.getGenericType(); // ParameterizedType Map<String, String>
        }
        catch (final Exception e)
        {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void getRawClassFromTypeShouldReturnProperClass()
    {
        final Type fieldType = this.getTypeOfHelperField();

        // this assertion should always pass, because it checks JDK behaviour
        Assertions.assertTrue(fieldType instanceof ParameterizedType);

        final Class<?> rawClassFromType = this.reflectionEngine.getRawClassFromType(fieldType);
        Assertions.assertEquals(Map.class, rawClassFromType);
    }

    @Test
    public void getGenericParametersShouldReturnProperParameters()
    {
        final Type fieldType = this.getTypeOfHelperField();

        final Type[] parameters = this.reflectionEngine.getGenericParameters(fieldType);
        Assertions.assertEquals(2, parameters.length);
        Assertions.assertEquals(String.class, parameters[0]);
        Assertions.assertEquals(String.class, parameters[1]);
    }

    @Test
    public void createParameterizedTypeShouldAcceptValidAmountOfParameters()
    {
        final ParameterizedType type = this.reflectionEngine.createParameterizedType(Map.class, String.class, String.class);

        Assertions.assertEquals(2, type.getActualTypeArguments().length);
    }

    @Test
    public void createParameterizedTypeShouldThrowIllegalArgumentException()
    {
        Assertions.assertThrows(IllegalArgumentException.class, () ->
        {
            // Map requires two parameters
            this.reflectionEngine.createParameterizedType(Map.class, String.class);
        });
    }

    @Test
    public void instantiateClassShouldProperlyHandleVariousJavaTypes()
    {
        final var arrayList = this.reflectionEngine.instantiateClass(ArrayList.class);
        Assertions.assertEquals(ArrayList.class, arrayList.getClass());

        final var hashSet = this.reflectionEngine.instantiateClass(HashSet.class);
        Assertions.assertEquals(HashSet.class, hashSet.getClass());

        final var hashMap = this.reflectionEngine.instantiateClass(HashMap.class);
        Assertions.assertEquals(HashMap.class, hashMap.getClass());
    }

    @Test
    public void instantiateClassShouldFailWithClassesWithoutDefaultConstructor()
    {
        Assertions.assertThrows(NoInstantiationStrategyException.class, () ->
        {
            this.reflectionEngine.instantiateClass(UUID.class);
        });
    }
}
