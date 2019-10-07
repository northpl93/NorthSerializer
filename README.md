NorthSerializer
===============

Very simple serializer framework which turns objects into series of primitive types.
Currently there are implementations for MessagePack and BSON.

MessagePack
-----------
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