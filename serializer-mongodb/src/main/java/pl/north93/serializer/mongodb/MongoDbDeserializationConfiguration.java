package pl.north93.serializer.mongodb;

import org.bson.BsonReader;

import lombok.ToString;
import pl.north93.serializer.platform.format.DeserializationConfiguration;
import pl.north93.serializer.platform.template.TemplateEngine;

@ToString
public class MongoDbDeserializationConfiguration implements DeserializationConfiguration<BsonReader, MongoDbDeserializationContext>
{
    @Override
    public MongoDbDeserializationContext createContext(final TemplateEngine templateEngine, final BsonReader serializedData)
    {
        return new MongoDbDeserializationContext(templateEngine, serializedData);
    }
}
