package pl.north93.serializer.mongodb;

import org.bson.BsonWriter;

import lombok.AllArgsConstructor;
import lombok.ToString;
import pl.north93.serializer.platform.format.SerializationConfiguration;
import pl.north93.serializer.platform.template.TemplateEngine;

@ToString
@AllArgsConstructor
public class MongoDbSerializationConfiguration implements SerializationConfiguration<MongoDbSerializationContext>
{
    private final BsonWriter bsonWriter;

    @Override
    public MongoDbSerializationContext createContext(final TemplateEngine templateEngine)
    {
        return new MongoDbSerializationContext(templateEngine, this.bsonWriter);
    }
}
