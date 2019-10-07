package pl.north93.serializer.msgpack;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import pl.north93.serializer.platform.NorthSerializer;
import pl.north93.serializer.platform.template.impl.NorthSerializerImpl;

public class Example {
    private static final NorthSerializer<byte[], byte[]> serializer = createSerializer();

    private static NorthSerializer<byte[], byte[]> createSerializer() {
        return new NorthSerializerImpl<>(new MsgPackSerializationFormat());
    }

    public static void main(String... args) {
        final List<String> beforeSerialization = new ArrayList<>(Arrays.asList("example"));

        final byte[] bytes = serializer.serialize(List.class, beforeSerialization);
        final List<String> afterSerialization = serializer.deserialize(List.class, bytes);

        System.out.println(beforeSerialization.equals(afterSerialization));
        // true
    }
}
