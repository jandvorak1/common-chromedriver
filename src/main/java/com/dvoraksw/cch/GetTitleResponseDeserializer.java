package com.dvoraksw.cch;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import java.lang.reflect.Type;

class GetTitleResponseDeserializer implements JsonDeserializer<GetTitleResponse> {
  @Override
  public GetTitleResponse deserialize(
      JsonElement json, Type type, JsonDeserializationContext context) throws JsonParseException {
    // Deserializing objects
    var object = json.getAsJsonObject();
    return new GetTitleResponse(object.get("value").getAsString());
  }
}
