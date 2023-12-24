package com.dvoraksw.cch;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import java.lang.reflect.Type;

class NewSessionResponseDeserializer implements JsonDeserializer<NewSessionResponse> {

  @Override
  public NewSessionResponse deserialize(
      JsonElement json, Type type, JsonDeserializationContext context) throws JsonParseException {
    // Deserializing objects
    var object = json.getAsJsonObject();
    var value = object.asMap().get("value").getAsJsonObject();
    var capabilities = value.asMap().get("capabilities").getAsJsonObject();
    var chrome = capabilities.asMap().get("chrome").getAsJsonObject();
    var googChromeOptions = capabilities.asMap().get("goog:chromeOptions").getAsJsonObject();
    var timeouts = capabilities.asMap().get("timeouts").getAsJsonObject();
    return new NewSessionResponse(
        capabilities.get("acceptInsecureCerts").getAsBoolean(),
        capabilities.get("browserName").getAsString(),
        capabilities.get("browserVersion").getAsString(),
        chrome.get("chromedriverVersion").getAsString(),
        chrome.get("userDataDir").getAsString(),
        capabilities.get("fedcm:accounts").getAsBoolean(),
        googChromeOptions.get("debuggerAddress").getAsString(),
        capabilities.get("networkConnectionEnabled").getAsBoolean(),
        capabilities.get("pageLoadStrategy").getAsString(),
        capabilities.get("platformName").getAsString(),
        capabilities.get("setWindowRect").getAsBoolean(),
        capabilities.get("strictFileInteractability").getAsBoolean(),
        timeouts.get("implicit").getAsInt(),
        timeouts.get("pageLoad").getAsInt(),
        timeouts.get("script").getAsInt(),
        capabilities.get("unhandledPromptBehavior").getAsString(),
        capabilities.get("webauthn:extension:credBlob").getAsBoolean(),
        capabilities.get("webauthn:extension:largeBlob").getAsBoolean(),
        capabilities.get("webauthn:extension:minPinLength").getAsBoolean(),
        capabilities.get("webauthn:extension:prf").getAsBoolean(),
        capabilities.get("webauthn:virtualAuthenticators").getAsBoolean(),
        value.get("sessionId").getAsString());
  }
}
