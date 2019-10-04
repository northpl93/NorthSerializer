package pl.north93.serializer.mongodb.test;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.bson.BsonReader;
import org.bson.json.JsonReader;
import org.bson.json.JsonWriter;
import org.junit.jupiter.api.Test;
import pl.north93.serializer.mongodb.MongoDbCodec;
import pl.north93.serializer.mongodb.MongoDbSerializationFormat;
import pl.north93.serializer.platform.NorthSerializer;
import pl.north93.serializer.platform.impl.NorthSerializerImpl;

import java.io.StringWriter;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;

public class MongoDbSimpleObjectsTest
{
    private final NorthSerializer<BsonReader> serializer = new NorthSerializerImpl<>(new MongoDbSerializationFormat());

    @Data
    @AllArgsConstructor
    public static class SimpleObject
    {
        String string;
        Integer integer;
        Double aDouble;
    }

    @Test
    public void testSimpleObjectSerialization()
    {
        final SimpleObject beforeSerialization = new SimpleObject("testString", 10, 5.0D);

        final StringWriter stringWriter = new StringWriter();
        MongoDbCodec.writer.set(new JsonWriter(stringWriter));
        this.serializer.serialize(SimpleObject.class, beforeSerialization);
        System.out.println(stringWriter);
        final Object deserialized = this.serializer.deserialize(SimpleObject.class, new JsonReader(stringWriter.toString()));

        assertSame(SimpleObject.class, deserialized.getClass());
        assertEquals(beforeSerialization, deserialized);
    }

    @Test
    public void testSimpleObjectDynamicSerialization()
    {
        final SimpleObject beforeSerialization = new SimpleObject("testString", 10, 5.0D);

        final StringWriter stringWriter = new StringWriter();
        MongoDbCodec.writer.set(new JsonWriter(stringWriter));
        this.serializer.serialize(Object.class, beforeSerialization);
        System.out.println(stringWriter);
        final Object deserialized = this.serializer.deserialize(Object.class, new JsonReader(stringWriter.toString()));

        assertSame(SimpleObject.class, deserialized.getClass());
        assertEquals(beforeSerialization, deserialized);
    }
}
