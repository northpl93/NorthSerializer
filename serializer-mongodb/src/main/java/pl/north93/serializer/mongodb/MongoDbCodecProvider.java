package pl.north93.serializer.mongodb;

import org.bson.BsonReader;
import org.bson.BsonWriter;
import org.bson.codecs.Codec;
import org.bson.codecs.configuration.CodecProvider;
import org.bson.codecs.configuration.CodecRegistry;

import pl.north93.serializer.platform.NorthSerializer;

public class MongoDbCodecProvider implements CodecProvider
{
    private final NorthSerializer<BsonWriter, BsonReader> serializer;

    public MongoDbCodecProvider(final NorthSerializer<BsonWriter, BsonReader> serializer)
    {
        this.serializer = serializer;
    }

    @Override
    public <T> Codec<T> get(final Class<T> clazz, final CodecRegistry registry)
    {
        return new MongoDbCodec<>(this.serializer, clazz);
    }
}
