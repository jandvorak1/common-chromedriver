package com.dvoraksw.cch;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import java.lang.reflect.Type;

class NavigateToRequestSerializer implements JsonSerializer<NavigateToRequest> {

  @Override
  public JsonElement serialize(
      NavigateToRequest source, Type type, JsonSerializationContext context) {
    // Serializing object
    var object = new JsonObject();
    object.addProperty("url", source.uri().toString());
    return object;
  }
}
