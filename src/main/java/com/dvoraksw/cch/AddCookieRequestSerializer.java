package com.dvoraksw.cch;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import java.lang.reflect.Type;
import java.time.ZoneOffset;

class AddCookieRequestSerializer implements JsonSerializer<AddCookieRequest> {

  @Override
  public JsonElement serialize(
      AddCookieRequest source, Type type, JsonSerializationContext context) {
    // Serializing cookie section
    var cookie = new JsonObject();
    cookie.addProperty("name", source.name());
    cookie.addProperty("value", source.value());
    cookie.addProperty("expiry", source.expiryTime().toEpochSecond(ZoneOffset.UTC));
    if (!source.path().isBlank()) {
      cookie.addProperty("path", source.path());
    }
    if (!source.domain().isBlank()) {
      cookie.addProperty("domain", source.domain());
    }
    if (source.secureOnlyFlag()) {
      cookie.addProperty("secure", true);
    }
    if (source.httpOnlyFlag()) {
      cookie.addProperty("httpOnly", true);
    }
    if (source.sameSite()) {
      cookie.addProperty("sameSite", true);
    }

    // Serializing object
    var object = new JsonObject();
    object.add("cookie", cookie);
    return object;
  }
}
