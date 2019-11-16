package pl.north93.serializer.platform.template.impl;

import static org.junit.jupiter.api.Assertions.assertNotNull;


import org.junit.jupiter.api.Test;

import lombok.ToString;
import pl.north93.serializer.platform.reflect.impl.DefaultClassResolver;
import pl.north93.serializer.platform.reflect.impl.ReflectionEngineImpl;
import pl.north93.serializer.platform.template.TemplateEngine;

public class TemplateEngineImplTest
{
    private final TemplateEngine templateEngine = this.createTemplateEngine();

    private TemplateEngine createTemplateEngine()
    {
        return new TemplateEngineImpl(new ReflectionEngineImpl(new DefaultClassResolver()), null);
    }

    @ToString
    public static class ClassWithRecursiveField
    {
        public ClassWithRecursiveField field;
    }

    @Test
    public void templateWithRecursiveFieldShouldBeGenerated()
    {
        assertNotNull(this.templateEngine.getTemplate(ClassWithRecursiveField.class));
    }
}
