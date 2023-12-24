package com.dvoraksw.cch;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import java.lang.reflect.Type;

class FindElementRequestSerializer implements JsonSerializer<FindElementRequest> {

  @Override
  public JsonElement serialize(
      FindElementRequest source, Type type, JsonSerializationContext context) {
    // Serializing object
    var object = new JsonObject();
    object.addProperty("using", source.using().valueOf());
    object.addProperty("value", source.value());
    return object;
  }
}
