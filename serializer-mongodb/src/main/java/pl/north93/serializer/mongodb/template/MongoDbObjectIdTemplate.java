package pl.north93.serializer.mongodb.template;

import org.bson.types.ObjectId;

import pl.north93.serializer.mongodb.MongoDbDeserializationContext;
import pl.north93.serializer.mongodb.MongoDbSerializationContext;
import pl.north93.serializer.platform.FieldInfo;
import pl.north93.serializer.platform.template.Template;

public class MongoDbObjectIdTemplate implements Template<ObjectId, MongoDbSerializationContext, MongoDbDeserializationContext>
{
    @Override
    public void serialise(final MongoDbSerializationContext context, final FieldInfo field, final ObjectId object) throws Exception
    {
        context.writeNameIfNeeded(field);
        context.getWriter().writeObjectId(object);
    }

    @Override
    public ObjectId deserialize(final MongoDbDeserializationContext context, final FieldInfo field) throws Exception
    {
        return context.readObjectId(field);
    }
}
