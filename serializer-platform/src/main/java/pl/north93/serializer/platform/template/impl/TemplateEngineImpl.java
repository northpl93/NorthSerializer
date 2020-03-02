package pl.north93.serializer.platform.template.impl;

import static java.lang.reflect.Modifier.isAbstract;


import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.time.Duration;
import java.time.Instant;
import java.util.Date;
import java.util.Map;
import java.util.TreeMap;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import pl.north93.serializer.platform.context.DeserializationContext;
import pl.north93.serializer.platform.context.SerializationContext;
import pl.north93.serializer.platform.format.TypePredictor;
import pl.north93.serializer.platform.reflect.ReflectionEngine;
import pl.north93.serializer.platform.property.SerializableObject;
import pl.north93.serializer.platform.reflect.UnsupportedTypeException;
import pl.north93.serializer.platform.template.Template;
import pl.north93.serializer.platform.template.TemplateEngine;
import pl.north93.serializer.platform.template.TemplateFactory;
import pl.north93.serializer.platform.template.builtin.BooleanTemplate;
import pl.north93.serializer.platform.template.builtin.ByteTemplate;
import pl.north93.serializer.platform.template.builtin.DateTemplate;
import pl.north93.serializer.platform.template.builtin.DoubleTemplate;
import pl.north93.serializer.platform.template.builtin.DurationTemplate;
import pl.north93.serializer.platform.template.builtin.DynamicTemplate;
import pl.north93.serializer.platform.template.builtin.EnumTemplate;
import pl.north93.serializer.platform.template.builtin.FloatTemplate;
import pl.north93.serializer.platform.template.builtin.InstantTemplate;
import pl.north93.serializer.platform.template.builtin.IntegerTemplate;
import pl.north93.serializer.platform.template.builtin.LongTemplate;
import pl.north93.serializer.platform.template.builtin.ShortTemplate;
import pl.north93.serializer.platform.template.builtin.StringTemplate;
import pl.north93.serializer.platform.template.filter.ExactTypeIgnoreGenericFilter;
import pl.north93.serializer.platform.template.filter.TemplateFilter;

/*default*/ class TemplateEngineImpl implements TemplateEngine
{
    private final ReflectionEngine reflectionEngine;
    private final TypePredictor<SerializationContext, DeserializationContext> typePredictor;
    private final ReadWriteLock templatesLock = new ReentrantReadWriteLock();
    private final TemplateFactory templateFactory = new TemplateFactoryImpl();
    private final Map<TemplateFilter, Template<?, ?, ?>> templates = new TreeMap<>();

    @SuppressWarnings("unchecked")
    public TemplateEngineImpl(final ReflectionEngine reflectionEngine, final TypePredictor<?, ?> typePredictor)
    {
        this.reflectionEngine = reflectionEngine;
        this.typePredictor = (TypePredictor<SerializationContext, DeserializationContext>) typePredictor;

        // special default types
        this.register(new DynamicTemplate.DynamicTemplateFilter(), new DynamicTemplate());
        this.register(new EnumTemplate.EnumTemplateFilter(), new EnumTemplate());

        // simple default types
        this.register(new ExactTypeIgnoreGenericFilter(String.class), new StringTemplate());
        this.register(new ExactTypeIgnoreGenericFilter(Boolean.class), new BooleanTemplate());
        this.register(new ExactTypeIgnoreGenericFilter(Byte.class), new ByteTemplate());
        this.register(new ExactTypeIgnoreGenericFilter(Short.class), new ShortTemplate());
        this.register(new ExactTypeIgnoreGenericFilter(Integer.class), new IntegerTemplate());
        this.register(new ExactTypeIgnoreGenericFilter(Float.class), new FloatTemplate());
        this.register(new ExactTypeIgnoreGenericFilter(Double.class), new DoubleTemplate());
        this.register(new ExactTypeIgnoreGenericFilter(Long.class), new LongTemplate());

        // complex default types
        this.register(new ExactTypeIgnoreGenericFilter(Date.class), new DateTemplate());
        this.register(new ExactTypeIgnoreGenericFilter(Instant.class), new InstantTemplate());
        this.register(new ExactTypeIgnoreGenericFilter(Duration.class), new DurationTemplate());
    }

    @Override
    public boolean isNeedsDynamicResolution(final Type type)
    {
        final Class<?> clazz = this.reflectionEngine.getRawClassFromType(type);
        return clazz.isInterface() || isAbstract(clazz.getModifiers()) || clazz == Object.class;
    }

    @Override
    public boolean isTypePredictingSupported()
    {
        return this.typePredictor != null;
    }

    @Override
    public TypePredictor<SerializationContext, DeserializationContext> getTypePredictor()
    {
        return this.typePredictor;
    }

    @Override
    public void register(final TemplateFilter filter, final Template<?, ?, ?> template)
    {
        final Lock writeLock = this.templatesLock.writeLock();
        try
        {
            writeLock.lock();

            // TreeMap zapewnie od razu poprawne sortowanie po priorytecie
            this.templates.put(filter, template);
        }
        finally
        {
            writeLock.unlock();
        }
    }

    @Override
    public Template<Object, SerializationContext, DeserializationContext> getTemplate(final Type type)
    {
        // iterujemy od najwyzszego priorytetu do najnizszego - TreeMap
        final Lock readLock = this.templatesLock.readLock();
        try
        {
            readLock.lock();
            for (final Map.Entry<TemplateFilter, Template<?, ?, ?>> entry : this.templates.entrySet())
            {
                final TemplateFilter filter = entry.getKey();
                if (filter.isApplicableTo(this, type))
                {
                    return this.genericCast(entry.getValue());
                }
            }
        }
        finally
        {
            readLock.unlock();
        }

        if (type instanceof Class)
        {
            final Class<?> clazz = (Class<?>) type;
            return this.genericCast(this.templateFactory.createAndRegisterTemplate(this, clazz));
        }

        throw new UnsupportedTypeException(type);
    }

    @SuppressWarnings("unchecked")
    private Template<Object, SerializationContext, DeserializationContext> genericCast(final Template<?, ?, ?> template)
    {
        return (Template<Object, SerializationContext, DeserializationContext>) template;
    }

    @Override
    public Class<?> findClass(final String name)
    {
        return this.reflectionEngine.findClass(name);
    }

    @Override
    public Class<?> getRawClassFromType(final Type type)
    {
        return this.reflectionEngine.getRawClassFromType(type);
    }

    @Override
    public Type[] getGenericParameters(final Type type)
    {
        return this.reflectionEngine.getGenericParameters(type);
    }

    @Override
    public ParameterizedType createParameterizedType(final Class<?> clazz, final Type[] parameters)
    {
        return this.reflectionEngine.createParameterizedType(clazz, parameters);
    }

    @Override
    public <T> T instantiateClass(final Class<T> clazz)
    {
        return this.reflectionEngine.instantiateClass(clazz);
    }

    @Override
    public <T> SerializableObject<T> getSerializableObjectFrom(final Class<T> clazz)
    {
        return this.reflectionEngine.getSerializableObjectFrom(clazz);
    }
}
