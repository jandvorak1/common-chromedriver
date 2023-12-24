package com.dvoraksw.cch;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import java.lang.reflect.Type;

class FindElementResponseDeserializer implements JsonDeserializer<FindElementResponse> {
  @Override
  public FindElementResponse deserialize(
      JsonElement json, Type type, JsonDeserializationContext context) throws JsonParseException {
    // Deserializing objects
    var object = json.getAsJsonObject();
    var value = object.asMap().get("value").getAsJsonObject();
    var element = value.entrySet().stream().findFirst();
    return element
        .map(it -> new FindElementResponse(it.getValue().getAsString(), it.getKey()))
        .orElseThrow(() -> new ChromeDriverException("Element is not available!"));
  }
}
