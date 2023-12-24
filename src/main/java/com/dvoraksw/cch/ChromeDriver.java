package com.dvoraksw.cch;

import com.google.gson.GsonBuilder;
import java.io.IOException;
import java.net.*;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.util.Base64;
import java.util.List;
import java.util.Locale;

/** The class implements the most commonly used functions of the chromedriver. */
public class ChromeDriver implements AutoCloseable {
  private static final int STARTING_TIMEOUT_SECONDS = 30;
  private static final int DEFAULT_PORT = 9115;
  private final Process process;
  private final int port;

  /**
   * Creates a new instance of the chrome driver with default parameters. Requires chromedriver in
   * the path. The default port number is 9115.
   *
   * @since 0.1.0
   */
  public ChromeDriver() {
    // Starting web driver process
    this(getDefaultDriver(), DEFAULT_PORT);
  }

  /**
   * Creates a new instance of the chrome driver with the specified port number. Requires
   * chromedriver in the path.
   *
   * @param port port number
   * @since 0.1.0
   */
  public ChromeDriver(int port) {
    // Starting web driver process
    this(getDefaultDriver(), port);
  }

  /**
   * Creates a new instance of the chrome driver with the specified path. The default port number is
   * 9115."
   *
   * @param driver path to the chrome driver.
   * @since 0.1.0
   */
  public ChromeDriver(Path driver) {
    // Starting web driver process
    this(driver, DEFAULT_PORT);
  }

  /**
   * Creates a new instance of the chrome driver.
   *
   * @param driver path to the chrome driver.
   * @param port port number
   * @since 0.1.0
   */
  public ChromeDriver(Path driver, int port) {
    try {
      // Starting web driver process
      this.port = port;
      var command = new String[] {driver.toString(), "--port=".concat(String.valueOf(port))};
      process = Runtime.getRuntime().exec(command);

      // Checking if webdriver is ready
      var timeout = LocalDateTime.now();
      while (true) {
        try {
          if (status()) {
            break;
          }
        } catch (ChromeDriverException ignore) {
        }
        if (LocalDateTime.now().isAfter(timeout.plusSeconds(STARTING_TIMEOUT_SECONDS))) {
          throw new ChromeDriverException("Cannot start webdriver!");
        }
      }
    } catch (IOException e) {
      // Handling errors
      throw new ChromeDriverException(e);
    }
  }

  /**
   * Creates a new session of the chrome driver."
   *
   * @param args list of chromedriver arguments.
   * @return session identifier
   * @since 0.1.0
   */
  public String newSession(List<String> args) {
    // Creating new web driver session
    var uri = URI.create(String.format("http://localhost:%s/session", port));
    var gson =
        new GsonBuilder()
            .disableHtmlEscaping()
            .registerTypeAdapter(NewSessionRequest.class, new NewSessionRequestSerializer())
            .registerTypeAdapter(NewSessionResponse.class, new NewSessionResponseDeserializer())
            .registerTypeAdapter(ErrorResponse.class, new ErrorResponseDeserializer())
            .create();
    var defaultDirectory = System.getProperty("java.io.tmpdir").replace("\\", "\\\\");
    var properties =
        new NewSessionRequest(FileSystems.getDefault().getPath(defaultDirectory).toString(), args);
    var response = Connector.request("POST", uri, gson.toJson(properties));
    if (response.contains("error")) {
      var error = gson.fromJson(response, ErrorResponse.class);
      throw new ChromeDriverException(error.message());
    } else {
      return gson.fromJson(response, NewSessionResponse.class).sessionId();
    }
  }

  /**
   * Returns the current state of the chrome driver."
   *
   * @return true if the chromedriver is ready to create a new session
   * @since 0.1.0
   */
  public boolean status() {
    // Getting web driver status
    var uri = URI.create(String.format("http://localhost:%s/status", port));
    var gson =
        new GsonBuilder()
            .disableHtmlEscaping()
            .registerTypeAdapter(StatusResponse.class, new StatusResponseDeserializer())
            .registerTypeAdapter(ErrorResponse.class, new ErrorResponseDeserializer())
            .create();
    var response = Connector.request("GET", uri, null);
    if (response.contains("error")) {
      var error = gson.fromJson(response, ErrorResponse.class);
      throw new ChromeDriverException(error.message());
    } else {
      return gson.fromJson(response, StatusResponse.class).ready();
    }
  }

  /**
   * Navigates to the desired URL.
   *
   * @param sessionId - session identifier
   * @param navigateTo - desired URL
   * @since 0.1.0
   */
  public void navigateTo(String sessionId, URI navigateTo) {
    // Navigating to specified url
    var uri = URI.create(String.format("http://localhost:%s/session/%s/url", port, sessionId));
    var gson =
        new GsonBuilder()
            .disableHtmlEscaping()
            .registerTypeAdapter(NavigateToRequest.class, new NavigateToRequestSerializer())
            .registerTypeAdapter(ErrorResponse.class, new ErrorResponseDeserializer())
            .create();
    var properties = new NavigateToRequest(navigateTo);
    var response = Connector.request("POST", uri, gson.toJson(properties));
    if (response.contains("error")) {
      var error = gson.fromJson(response, ErrorResponse.class);
      throw new ChromeDriverException(error.message());
    }
  }

  /**
   * Finds the element on the current page by selector.
   *
   * @param sessionId session identifier
   * @param using location strategy
   * @param value element selector
   * @return element identifier
   * @since 0.1.0
   */
  public String findElement(String sessionId, LocationStrategy using, String value) {
    // Navigating to specified url
    var uri = URI.create(String.format("http://localhost:%s/session/%s/element", port, sessionId));
    var gson =
        new GsonBuilder()
            .disableHtmlEscaping()
            .registerTypeAdapter(FindElementRequest.class, new FindElementRequestSerializer())
            .registerTypeAdapter(FindElementResponse.class, new FindElementResponseDeserializer())
            .registerTypeAdapter(ErrorResponse.class, new ErrorResponseDeserializer())
            .create();
    var properties = new FindElementRequest(using, value);
    var response = Connector.request("POST", uri, gson.toJson(properties));
    if (response.contains("error")) {
      var error = gson.fromJson(response, ErrorResponse.class);
      throw new ChromeDriverException(error.message());
    } else {
      return gson.fromJson(response, FindElementResponse.class).id();
    }
  }

  /**
   * Clears the text in the element.
   *
   * @param sessionId session identifier
   * @param elementId element identifier
   * @since 0.1.0
   */
  public void elementClear(String sessionId, String elementId) {
    // Clearing specified element
    var uri =
        URI.create(
            String.format(
                "http://localhost:%s/session/%s/element/%s/clear", port, sessionId, elementId));
    var gson =
        new GsonBuilder()
            .disableHtmlEscaping()
            .registerTypeAdapter(ErrorResponse.class, new ErrorResponseDeserializer())
            .create();
    var response = Connector.request("POST", uri, "{}");
    if (response.contains("error")) {
      var error = gson.fromJson(response, ErrorResponse.class);
      throw new ChromeDriverException(error.message());
    }
  }

  /**
   * Clicks on the element.
   *
   * @param sessionId session identifier
   * @param elementId element identifier
   * @since 0.1.0
   */
  public void elementClick(String sessionId, String elementId) {
    // Clicking to specified element
    var uri =
        URI.create(
            String.format(
                "http://localhost:%s/session/%s/element/%s/click", port, sessionId, elementId));
    var gson =
        new GsonBuilder()
            .disableHtmlEscaping()
            .registerTypeAdapter(ErrorResponse.class, new ErrorResponseDeserializer())
            .create();
    var response = Connector.request("POST", uri, "{}");
    if (response.contains("error")) {
      var error = gson.fromJson(response, ErrorResponse.class);
      throw new ChromeDriverException(error.message());
    }
  }

  /**
   * Checks if the element is displayed on the screen.
   *
   * @param sessionId session identifier
   * @param elementId element identifier
   * @return true if the element is displayed
   * @since 0.1.0
   */
  public boolean elementDisplayed(String sessionId, String elementId) {
    // CHecking if element is displayed
    var uri =
        URI.create(
            String.format(
                "http://localhost:%s/session/%s/element/%s/displayed", port, sessionId, elementId));
    var gson =
        new GsonBuilder()
            .disableHtmlEscaping()
            .registerTypeAdapter(
                ElementDisplayedResponse.class, new ElementDisplayedResponseDeserializer())
            .registerTypeAdapter(ErrorResponse.class, new ErrorResponseDeserializer())
            .create();
    var response = Connector.request("GET", uri, null);
    if (response.contains("error")) {
      var error = gson.fromJson(response, ErrorResponse.class);
      throw new ChromeDriverException(error.message());
    } else {
      return gson.fromJson(response, ElementDisplayedResponse.class).value();
    }
  }

  /**
   * Writes the specified text into the element.
   *
   * @param sessionId session identifier
   * @param elementId element identifier
   * @param text specified text
   * @since 0.1.0
   */
  public void elementSendKeys(String sessionId, String elementId, String text) {
    // Sending text to specified element
    var uri =
        URI.create(
            String.format(
                "http://localhost:%s/session/%s/element/%s/value", port, sessionId, elementId));
    var gson =
        new GsonBuilder()
            .disableHtmlEscaping()
            .registerTypeAdapter(
                ElementSendKeysRequest.class, new ElementSendKeysRequestSerializer())
            .registerTypeAdapter(ErrorResponse.class, new ErrorResponseDeserializer())
            .create();
    var properties = new ElementSendKeysRequest(text);
    var response = Connector.request("POST", uri, gson.toJson(properties));
    if (response.contains("error")) {
      var error = gson.fromJson(response, ErrorResponse.class);
      throw new ChromeDriverException(error.message());
    }
  }

  /**
   * Executes a script on the current page.
   *
   * @param sessionId session identifier
   * @param script source code of the script
   * @param args script arguments
   * @return script return value
   * @since 0.1.0
   */
  public String executeScript(String sessionId, String script, List<String> args) {
    // Executing javascript at current page
    var uri =
        URI.create(String.format("http://localhost:%s/session/%s/execute/sync", port, sessionId));
    var gson =
        new GsonBuilder()
            .disableHtmlEscaping()
            .registerTypeAdapter(ExecuteScriptRequest.class, new ExecuteScriptRequestSerializer())
            .registerTypeAdapter(
                ExecuteScriptResponse.class, new ExecuteScriptResponseDeserializer())
            .registerTypeAdapter(ErrorResponse.class, new ErrorResponseDeserializer())
            .create();
    var properties = new ExecuteScriptRequest(script, args);
    var response = Connector.request("POST", uri, gson.toJson(properties));
    if (response.contains("error")) {
      var error = gson.fromJson(response, ErrorResponse.class);
      throw new ChromeDriverException(error.message());
    } else {
      return gson.fromJson(response, ExecuteScriptResponse.class).value();
    }
  }

  /**
   * Reads the value of the element attribute.
   *
   * @param sessionId session identifier
   * @param elementId element identifier
   * @param attribute attribute name
   * @return attribute value
   * @since 0.1.0
   */
  public String getElementAttribute(String sessionId, String elementId, String attribute) {
    // Getting element attribute
    var uri =
        URI.create(
            String.format(
                "http://localhost:%s/session/%s/element/%s/attribute/%s",
                port, sessionId, elementId, attribute));
    var gson =
        new GsonBuilder()
            .disableHtmlEscaping()
            .registerTypeAdapter(
                GetElementAttributeResponse.class, new GetElementAttributeResponseDeserializer())
            .registerTypeAdapter(ErrorResponse.class, new ErrorResponseDeserializer())
            .create();
    var response = Connector.request("GET", uri, null);
    if (response.contains("error")) {
      var error = gson.fromJson(response, ErrorResponse.class);
      throw new ChromeDriverException(error.message());
    } else {
      return gson.fromJson(response, GetElementAttributeResponse.class).value();
    }
  }

  /**
   * Reads the value of the element property.
   *
   * @param sessionId session identifier
   * @param elementId element identifier
   * @param property property name
   * @return property value
   * @since 0.1.0
   */
  public String getElementProperty(String sessionId, String elementId, String property) {
    // Getting element property
    var uri =
        URI.create(
            String.format(
                "http://localhost:%s/session/%s/element/%s/property/%s",
                port, sessionId, elementId, property));
    var gson =
        new GsonBuilder()
            .disableHtmlEscaping()
            .registerTypeAdapter(
                GetElementPropertyResponse.class, new GetElementPropertyResponseDeserializer())
            .registerTypeAdapter(ErrorResponse.class, new ErrorResponseDeserializer())
            .create();
    var response = Connector.request("GET", uri, null);
    if (response.contains("error")) {
      var error = gson.fromJson(response, ErrorResponse.class);
      throw new ChromeDriverException(error.message());
    } else {
      return gson.fromJson(response, GetElementPropertyResponse.class).value();
    }
  }

  /**
   * Reads the text of the element.
   *
   * @param sessionId session identifier
   * @param elementId element identifier
   * @return text of the element
   * @since 0.1.0
   */
  public String getElementText(String sessionId, String elementId) {
    // Getting element text
    var uri =
        URI.create(
            String.format(
                "http://localhost:%s/session/%s/element/%s/text", port, sessionId, elementId));
    var gson =
        new GsonBuilder()
            .disableHtmlEscaping()
            .registerTypeAdapter(
                GetElementTextResponse.class, new GetElementTextResponseDeserializer())
            .registerTypeAdapter(ErrorResponse.class, new ErrorResponseDeserializer())
            .create();
    var response = Connector.request("GET", uri, null);
    if (response.contains("error")) {
      var error = gson.fromJson(response, ErrorResponse.class);
      throw new ChromeDriverException(error.message());
    } else {
      return gson.fromJson(response, GetElementTextResponse.class).value();
    }
  }

  /**
   * Reads the title of the current page.
   *
   * @param sessionId session identifier
   * @return title of the current page
   * @since 0.1.0
   */
  public String getTitle(String sessionId) {
    // Getting page title
    var uri = URI.create(String.format("http://localhost:%s/session/%s/title", port, sessionId));
    var gson =
        new GsonBuilder()
            .disableHtmlEscaping()
            .registerTypeAdapter(GetTitleResponse.class, new GetTitleResponseDeserializer())
            .registerTypeAdapter(ErrorResponse.class, new ErrorResponseDeserializer())
            .create();
    var response = Connector.request("GET", uri, null);
    if (response.contains("error")) {
      var error = gson.fromJson(response, ErrorResponse.class);
      throw new ChromeDriverException(error.message());
    } else {
      return gson.fromJson(response, GetTitleResponse.class).value();
    }
  }

  /**
   * Creates a jpeg file with a screenshot of the current page.
   *
   * @param sessionId session identifier
   * @param path jpeg file path
   * @since 0.1.0
   */
  public void takeScreenshot(String sessionId, Path path) {
    // Getting current page screenshot and save it to jpg file
    var uri =
        URI.create(String.format("http://localhost:%s/session/%s/screenshot", port, sessionId));
    var gson =
        new GsonBuilder()
            .disableHtmlEscaping()
            .registerTypeAdapter(
                TakeScreenshotResponse.class, new TakeScreenshotResponseDeserializer())
            .registerTypeAdapter(ErrorResponse.class, new ErrorResponseDeserializer())
            .create();
    var response = Connector.request("GET", uri, null);
    if (response.contains("error")) {
      var error = gson.fromJson(response, ErrorResponse.class);
      throw new ChromeDriverException(error.message());
    } else {
      var base64 = gson.fromJson(response, GetTitleResponse.class).value();
      byte[] data = Base64.getDecoder().decode(base64);
      try (var stream = Files.newOutputStream(path)) {
        stream.write(data);
      } catch (IOException e) {
        throw new ChromeDriverException(e);
      }
    }
  }

  /**
   * Sends a cookie to the current page.
   *
   * @param sessionId session identifier
   * @param name cookie name
   * @param value cookie value
   * @param path cookie path
   * @param domain cookie domain
   * @param secureOnlyFlag secure only flag
   * @param httpOnlyFlag http only flag
   * @param expiryTime expiry time
   * @param sameSite same site flag
   * @since 0.1.0
   */
  public void addCookie(
      String sessionId,
      String name,
      String value,
      String path,
      String domain,
      boolean secureOnlyFlag,
      boolean httpOnlyFlag,
      LocalDateTime expiryTime,
      boolean sameSite) {
    // Clearing specified element
    var uri = URI.create(String.format("http://localhost:%s/session/%s/cookie", port, sessionId));
    var gson =
        new GsonBuilder()
            .disableHtmlEscaping()
            .registerTypeAdapter(AddCookieRequest.class, new AddCookieRequestSerializer())
            .registerTypeAdapter(ErrorResponse.class, new ErrorResponseDeserializer())
            .create();
    var properties =
        new AddCookieRequest(
            name, value, path, domain, secureOnlyFlag, httpOnlyFlag, expiryTime, sameSite);
    var response = Connector.request("POST", uri, gson.toJson(properties));
    if (response.contains("error")) {
      var error = gson.fromJson(response, ErrorResponse.class);
      throw new ChromeDriverException(error.message());
    }
  }

  /**
   * Deletes the current session.
   *
   * @param sessionId session identifier
   * @since 0.1.0
   */
  public void deleteSession(String sessionId) {
    // Deleting web driver session
    var uri = URI.create(String.format("http://localhost:%s/session/%s", port, sessionId));
    var gson =
        new GsonBuilder()
            .disableHtmlEscaping()
            .registerTypeAdapter(ErrorResponse.class, new ErrorResponseDeserializer())
            .create();
    var response = Connector.request("DELETE", uri, "{}");
    if (response.contains("error")) {
      var error = gson.fromJson(response, ErrorResponse.class);
      throw new ChromeDriverException(error.message());
    }
  }

  /**
   * Automatically terminates the chrome driver because tis object implements the AutoCloseable
   * interface
   */
  @Override
  public void close() {
    // Stopping web driver
    process.destroy();
  }

  private static Path getDefaultDriver() {
    // Getting default driver path
    var system = System.getProperty("os.name").toLowerCase(Locale.getDefault());
    return Path.of(system.contains("win") ? "chromedriver.exe" : "chromedriver");
  }
}
