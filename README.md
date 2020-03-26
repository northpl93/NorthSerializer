NorthSerializer
===============

It's a very simple serializer framework which turns objects into series of primitive types.
Currently there are implementations for MessagePack and BSON.

MessagePack
-----------

:warning: Please be aware that names of fields are NOT present in serializer output.
Fields order may change between subsequent compilations of the same source code.
It may cause deserialization errors and lose of data.
I recommend usage of that msgpack serializer only in caches.

```java
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
```