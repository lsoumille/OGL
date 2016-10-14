package fr.unice.polytech.ogl.islda.parameters;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import fr.unice.polytech.ogl.islda.model.Objective;

import java.lang.reflect.Type;

/**
 * @author Pascal Tung
 */
public class TransformParamSerializer implements JsonSerializer<TransformParameters> {
    @Override
    public JsonElement serialize(TransformParameters transformParameters, Type type, JsonSerializationContext jsonSerializationContext) {
        JsonObject json = new JsonObject();

        for (Objective res : transformParameters.getResources()) {
            json.addProperty(res.getResource(), res.getAmount());
        }

        return json;
    }
}
