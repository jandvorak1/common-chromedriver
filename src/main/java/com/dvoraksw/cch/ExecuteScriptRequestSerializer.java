package com.dvoraksw.cch;

import com.google.gson.*;
import java.lang.reflect.Type;

class ExecuteScriptRequestSerializer implements JsonSerializer<ExecuteScriptRequest> {

  @Override
  public JsonElement serialize(
      ExecuteScriptRequest source, Type type, JsonSerializationContext context) {
    // Serializing section args
    var args = new JsonArray();
    source.args().forEach(args::add);

    // Serializing object
    var object = new JsonObject();
    object.addProperty("script", source.script());
    object.add("args", args);
    return object;
  }
}
