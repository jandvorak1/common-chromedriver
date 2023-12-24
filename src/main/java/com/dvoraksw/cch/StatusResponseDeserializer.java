package com.dvoraksw.cch;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import java.lang.reflect.Type;

class StatusResponseDeserializer implements JsonDeserializer<StatusResponse> {
  @Override
  public StatusResponse deserialize(JsonElement json, Type type, JsonDeserializationContext context)
      throws JsonParseException {
    // Deserializing objects
    var object = json.getAsJsonObject();
    var value = object.asMap().get("value").getAsJsonObject();
    var build = value.asMap().get("build").getAsJsonObject();
    var os = value.asMap().get("os").getAsJsonObject();
    return new StatusResponse(
        value.get("message").getAsString(),
        build.get("version").getAsString(),
        os.get("arch").getAsString(),
        os.get("name").getAsString(),
        os.get("version").getAsString(),
        value.get("ready").getAsBoolean());
  }
}
