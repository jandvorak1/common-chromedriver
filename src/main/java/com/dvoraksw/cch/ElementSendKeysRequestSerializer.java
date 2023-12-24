package com.dvoraksw.cch;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import java.lang.reflect.Type;

class ElementSendKeysRequestSerializer implements JsonSerializer<ElementSendKeysRequest> {

  @Override
  public JsonElement serialize(
      ElementSendKeysRequest source, Type type, JsonSerializationContext context) {
    // Serializing object
    var object = new JsonObject();
    object.addProperty("text", source.text());
    return object;
  }
}
