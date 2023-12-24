package com.dvoraksw.cch;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.Locale;

class Connector {

  public static String request(String method, URI uri, String body) {
    try (var client = HttpClient.newHttpClient()) {
      // Requesting stream
      var request =
          switch (method.toUpperCase(Locale.getDefault())) {
            case "POST" -> HttpRequest.newBuilder().POST(HttpRequest.BodyPublishers.ofString(body));
            case "DELETE" -> HttpRequest.newBuilder().DELETE();
            default -> HttpRequest.newBuilder().GET();
          };
      var response =
          client.send(
              request
                  .uri(uri)
                  .version(HttpClient.Version.HTTP_1_1)
                  .timeout(Duration.ofSeconds(30))
                  .expectContinue(false)
                  .build(),
              HttpResponse.BodyHandlers.ofString());
      return response.body();
    } catch (InterruptedException | IOException e) {
      // Handling errors
      throw new ChromeDriverException(e.getMessage());
    }
  }
}
