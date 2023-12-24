package com.dvoraksw.cch;

import com.google.gson.*;
import java.lang.reflect.Type;

class NewSessionRequestSerializer implements JsonSerializer<NewSessionRequest> {

  private static final int IMPLICIT_TIMEOUT_MILISECONDS = 0;
  private static final int PAGE_LOAD_TIMEOUT_MILISECONDS = 300000;
  private static final int SCRIPT_TIMEOUT_MILISECONDS = 300000;
  private static final boolean PROMPT_FOR_DOWNLOAD = false;
  private static final boolean ACCEPT_INSECURE_CERTS = true;

  @Override
  public JsonElement serialize(
      NewSessionRequest source, Type type, JsonSerializationContext context) {
    // Serializing section timeouts
    var timeouts = new JsonObject();
    timeouts.addProperty("implicit", IMPLICIT_TIMEOUT_MILISECONDS);
    timeouts.addProperty("pageLoad", PAGE_LOAD_TIMEOUT_MILISECONDS);
    timeouts.addProperty("script", SCRIPT_TIMEOUT_MILISECONDS);

    // Serializing section args
    var args = new JsonArray();
    source.args().forEach(args::add);

    // Serializing section download
    var download = new JsonObject();
    download.addProperty("prompt_for_download", PROMPT_FOR_DOWNLOAD);
    download.addProperty("default_directory", source.defaultDirectory());

    // Serialize section prefs
    var prefs = new JsonObject();
    prefs.add("download", download);

    // Serializing section goog:chromeOptions
    var googChromeOptions = new JsonObject();
    googChromeOptions.add("args", args);
    googChromeOptions.add("prefs", prefs);

    // Serialize section alwaysMatch
    var alwaysMatch = new JsonObject();
    alwaysMatch.addProperty("acceptInsecureCerts", ACCEPT_INSECURE_CERTS);
    alwaysMatch.add("timeouts", timeouts);
    alwaysMatch.add("goog:chromeOptions", googChromeOptions);

    // Serializing section capabilities
    var capabilites = new JsonObject();
    capabilites.add("alwaysMatch", alwaysMatch);

    // Serializing object
    var object = new JsonObject();
    object.add("capabilities", capabilites);
    return object;
  }
}
