package pl.north93.serializer.mongodb;

import org.bson.BsonReader;
import org.bson.BsonWriter;
import org.bson.codecs.Codec;
import org.bson.codecs.DecoderContext;
import org.bson.codecs.EncoderContext;

import pl.north93.serializer.platform.NorthSerializer;

public class MongoDbCodec<T> implements Codec<T>
{
    private final NorthSerializer<BsonWriter, BsonReader> serializer;
    private final Class<T> type;

    public MongoDbCodec(final NorthSerializer<BsonWriter, BsonReader> serializer, final Class<T> type)
    {
        this.serializer = serializer;
        this.type = type;
    }

    @Override
    public T decode(final BsonReader bsonReader, final DecoderContext decoderContext)
    {
        return this.serializer.deserialize(this.type, bsonReader);
    }

    @Override
    public void encode(final BsonWriter bsonWriter, final Object o, final EncoderContext encoderContext)
    {
        this.serializer.serialize(this.type, o);
    }

    @Override
    public Class<T> getEncoderClass()
    {
        return this.type;
    }
}
