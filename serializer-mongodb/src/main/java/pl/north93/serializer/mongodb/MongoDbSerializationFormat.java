package pl.north93.serializer.mongodb;

import javax.annotation.Nullable;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.regex.Pattern;

import org.bson.BsonBinaryWriter;
import org.bson.BsonReader;
import org.bson.BsonWriter;
import org.bson.Document;
import org.bson.io.BasicOutputBuffer;
import org.bson.types.ObjectId;

import pl.north93.serializer.mongodb.template.MongoDbDocumentTemplate;
import pl.north93.serializer.mongodb.template.MongoDbListTemplate;
import pl.north93.serializer.mongodb.template.MongoDbMapTemplate;
import pl.north93.serializer.mongodb.template.MongoDbObjectIdTemplate;
import pl.north93.serializer.mongodb.template.MongoDbPatternTemplate;
import pl.north93.serializer.mongodb.template.MongoDbUuidTemplate;
import pl.north93.serializer.platform.format.DeserializationConfiguration;
import pl.north93.serializer.platform.format.SerializationConfiguration;
import pl.north93.serializer.platform.format.SerializationFormat;
import pl.north93.serializer.platform.format.TypePredictor;
import pl.north93.serializer.platform.template.TemplateEngine;
import pl.north93.serializer.platform.template.TemplatePriority;
import pl.north93.serializer.platform.template.filter.AnyInheritedTypeFilter;
import pl.north93.serializer.platform.template.filter.ExactTypeIgnoreGenericFilter;

public class MongoDbSerializationFormat implements SerializationFormat<BsonWriter, BsonReader, MongoDbSerializationContext, MongoDbDeserializationContext>
{
    private final MongoDbDeserializationConfiguration deserializationConfiguration;

    public MongoDbSerializationFormat()
    {
        this.deserializationConfiguration = new MongoDbDeserializationConfiguration();
    }

    @Override
    public void configure(final TemplateEngine templateEngine)
    {
        templateEngine.register(new ExactTypeIgnoreGenericFilter(UUID.class), new MongoDbUuidTemplate());
        templateEngine.register(new ExactTypeIgnoreGenericFilter(ObjectId.class), new MongoDbObjectIdTemplate());
        templateEngine.register(new ExactTypeIgnoreGenericFilter(Pattern.class), new MongoDbPatternTemplate());

        templateEngine.register(new AnyInheritedTypeFilter(List.class), new MongoDbListTemplate());

        templateEngine.register(new AnyInheritedTypeFilter(Map.class), new MongoDbMapTemplate());
        // dla Document musimy miec specjalna templatke bo inaczej posypie sie przez brak generic type
        templateEngine.register(new ExactTypeIgnoreGenericFilter(Document.class, TemplatePriority.HIGHEST), new MongoDbDocumentTemplate());
    }

    @Override
    public SerializationConfiguration<MongoDbSerializationContext> createDefaultSerializationConfig()
    {
        return new MongoDbSerializationConfiguration(new BsonBinaryWriter(new BasicOutputBuffer()));
    }

    @Override
    public DeserializationConfiguration<BsonReader, MongoDbDeserializationContext> createDefaultDeserializationConfig()
    {
        return this.deserializationConfiguration;
    }

    @Nullable
    @Override
    public TypePredictor<MongoDbSerializationContext, MongoDbDeserializationContext> getTypePredictor()
    {
        return new MongoDbTypePredictor();
    }
}
