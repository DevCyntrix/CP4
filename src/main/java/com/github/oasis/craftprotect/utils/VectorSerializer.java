package com.github.oasis.craftprotect.utils;

import com.google.gson.*;
import org.bukkit.util.Vector;

import java.lang.reflect.Type;

public class VectorSerializer implements JsonDeserializer<Vector>, JsonSerializer<Vector> {
    @Override
    public Vector deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext context) throws JsonParseException {
        if (!jsonElement.isJsonObject())
            return null;
        JsonObject obj = jsonElement.getAsJsonObject();
        return new Vector(obj.get("x").getAsDouble(), obj.get("y").getAsDouble(), obj.get("z").getAsDouble());
    }

    @Override
    public JsonElement serialize(Vector vector, Type type, JsonSerializationContext context) {
        JsonObject object = new JsonObject();
        object.addProperty("x", vector.getX());
        object.addProperty("y", vector.getY());
        object.addProperty("z", vector.getZ());
        return object;
    }
}
