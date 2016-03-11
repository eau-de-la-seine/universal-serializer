package fr.ekinci.universalserializer;

import java.lang.reflect.Type;
import com.google.gson.Gson;
import fr.ekinci.universalserializer.exception.SerializationException;
import fr.ekinci.universalserializer.exception.UnserializationException;

/**
 * JSON serialization (it use Gson)
 * 
 * @author Gokan EKINCI
 */
public class JsonSerializer implements Serializer<String> {
    // This attribute is used for unmarshaling
    private Type objectType;
    private Gson gson = new Gson();
    
    public JsonSerializer(Type objectType){
        this.objectType = objectType;
    }
    
    @Override
    public <J> String serialize(J objectToSerialize) throws SerializationException {
        return gson.toJson(objectToSerialize);
    }

    @Override
    public <J> J unserialize(String objectToUnserialize) throws UnserializationException {
        return (J) gson.fromJson(objectToUnserialize, objectType);
    }
}
