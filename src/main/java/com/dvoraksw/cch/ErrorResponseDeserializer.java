package com.dvoraksw.cch;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import java.lang.reflect.Type;

class ErrorResponseDeserializer implements JsonDeserializer<ErrorResponse> {
  @Override
  public ErrorResponse deserialize(JsonElement json, Type type, JsonDeserializationContext context)
      throws JsonParseException {
    // Deserializing objects
    var object = json.getAsJsonObject();
    var value = object.asMap().get("value").getAsJsonObject();
    return new ErrorResponse(
        value.get("error").getAsString(),
        value.get("message").getAsString(),
        value.get("stacktrace").getAsString());
  }
}
