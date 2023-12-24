package com.dvoraksw.cch;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.util.*;
import org.junit.jupiter.api.Test;

class ChromeDriverTest {

  private final List<String> args =
      List.of(
          "window-size=1440,900",
          "disable-extensions",
          "disable-gpu",
          "disable-logging",
          "disable-dev-shm-usage",
          "remote-allow-origins=*",
          "proxy-auto-detect",
          "safebrowsing-disable-download-protection",
          "safebrowsing-disable-extension-blacklist",
          "webview-disable-safebrowsing-support",
          "headless=new",
          "no-sandbox");

  @Test
  void newSession() {
    // Testing method newSession
    assertDoesNotThrow(
        () -> {
          try (var driver = new ChromeDriver()) {
            var sessionId = driver.newSession(args);
            driver.deleteSession(sessionId);
          }
        });
  }

  @Test
  void deleteSession() {
    // Testing method deleteSession
    assertDoesNotThrow(
        () -> {
          try (var driver = new ChromeDriver(9115)) {
            var sessionId = driver.newSession(args);
            driver.deleteSession(sessionId);
          }
        });
  }

  @Test
  void status() {
    // Testing method status
    var system = System.getProperty("os.name").toLowerCase(Locale.getDefault());
    var path = Path.of(system.contains("win") ? "chromedriver.exe" : "chromedriver");
    try (var driver = new ChromeDriver(path)) {
      var sessionId = driver.newSession(args);
      var result = driver.status();
      driver.deleteSession(sessionId);
      assertTrue(result);
    }
  }

  @Test
  void navigateTo() {
    // Testing method navigateTo
    assertDoesNotThrow(
        () -> {
          try (var driver = new ChromeDriver()) {
            var sessionId = driver.newSession(args);
            driver.navigateTo(
                sessionId, URI.create("https://www.selenium.dev/selenium/web/web-form.html"));
            driver.deleteSession(sessionId);
          }
        });
  }

  @Test
  void findElement() {
    // Testing method findElement
    try (var driver = new ChromeDriver()) {
      var sessionId = driver.newSession(args);
      driver.navigateTo(
          sessionId, URI.create("https://www.selenium.dev/selenium/web/web-form.html"));
      var result = driver.findElement(sessionId, LocationStrategy.XPATH, "//*[@id='my-text-id']");
      driver.deleteSession(sessionId);
      assertFalse(result.isEmpty());
    }
  }

  @Test
  void elementClear() {
    // Testing method findElement
    assertDoesNotThrow(
        () -> {
          try (var driver = new ChromeDriver()) {
            var sessionId = driver.newSession(args);
            driver.navigateTo(
                sessionId, URI.create("https://www.selenium.dev/selenium/web/web-form.html"));
            var elementId =
                driver.findElement(sessionId, LocationStrategy.XPATH, "//*[@id='my-text-id']");
            driver.elementClear(sessionId, elementId);
            driver.deleteSession(sessionId);
          }
        });
  }

  @Test
  void elementClick() {
    // Testing method elementClick
    try (var driver = new ChromeDriver()) {
      var sessionId = driver.newSession(args);
      driver.navigateTo(
          sessionId, URI.create("https://www.selenium.dev/selenium/web/web-form.html"));
      var elementId =
          driver.findElement(
              sessionId, LocationStrategy.XPATH, "/html/body/main/div/form/div/div[2]/button");
      driver.elementClick(sessionId, elementId);
      var result = driver.getTitle(sessionId);
      driver.deleteSession(sessionId);
      assertEquals("Web form - target page", result);
    }
  }

  @Test
  void elementDisplayed() {
    // Testing method elementDisplayed
    try (var driver = new ChromeDriver()) {
      var sessionId = driver.newSession(args);
      driver.navigateTo(
          sessionId, URI.create("https://www.selenium.dev/selenium/web/web-form.html"));
      var elementId =
          driver.findElement(
              sessionId, LocationStrategy.XPATH, "/html/body/main/div/form/div/div[1]/label[1]");
      var result = driver.elementDisplayed(sessionId, elementId);
      driver.deleteSession(sessionId);
      assertTrue(result);
    }
  }

  @Test
  void elementSendKeys() {
    // Testing method elementSendKeys
    try (var driver = new ChromeDriver()) {
      var sessionId = driver.newSession(args);
      driver.navigateTo(
          sessionId, URI.create("https://www.selenium.dev/selenium/web/web-form.html"));
      var elementId =
          driver.findElement(
              sessionId,
              LocationStrategy.XPATH,
              "/html/body/main/div/form/div/div[2]/label[1]/select");
      driver.elementSendKeys(sessionId, elementId, "Two");
      var result = driver.elementDisplayed(sessionId, elementId);
      driver.deleteSession(sessionId);
      assertTrue(result);
    }
  }

  @Test
  void executeScript() {
    // Testing method executeScript
    try (var driver = new ChromeDriver()) {
      var sessionId = driver.newSession(args);
      driver.navigateTo(
          sessionId, URI.create("https://www.selenium.dev/selenium/web/web-form.html"));
      var result = driver.executeScript(sessionId, "return 'script';", List.of());
      driver.deleteSession(sessionId);
      assertEquals("script", result);
    }
  }

  @Test
  void getElementAttribute() {
    // Testing method getElementAttribute
    try (var driver = new ChromeDriver()) {
      var sessionId = driver.newSession(args);
      driver.navigateTo(
          sessionId, URI.create("https://www.selenium.dev/selenium/web/web-form.html"));
      var elementId =
          driver.findElement(
              sessionId,
              LocationStrategy.XPATH,
              "/html/body/main/div/form/div/div[1]/label[1]/input");
      var result = driver.getElementAttribute(sessionId, elementId, "id");
      driver.deleteSession(sessionId);
      assertEquals("my-text-id", result);
    }
  }

  @Test
  void getElementProperty() {
    // Testing method getElementProperty
    try (var driver = new ChromeDriver()) {
      var sessionId = driver.newSession(args);
      driver.navigateTo(
          sessionId, URI.create("https://www.selenium.dev/selenium/web/web-form.html"));
      var elementId =
          driver.findElement(
              sessionId, LocationStrategy.XPATH, "/html/body/main/div/form/div/div[1]/label[1]");
      var result = driver.getElementProperty(sessionId, elementId, "innerText");
      driver.deleteSession(sessionId);
      assertEquals("Text input", result);
    }
  }

  @Test
  void getElementText() {
    // Testing method getElementText
    try (var driver = new ChromeDriver()) {
      var sessionId = driver.newSession(args);
      driver.navigateTo(
          sessionId, URI.create("https://www.selenium.dev/selenium/web/web-form.html"));
      var elementId =
          driver.findElement(
              sessionId, LocationStrategy.XPATH, "/html/body/main/div/form/div/div[1]/label[1]");
      var result = driver.getElementText(sessionId, elementId);
      driver.deleteSession(sessionId);
      assertEquals("Text input", result);
    }
  }

  @Test
  void getTitle() {
    // Testing method getTitle
    try (var driver = new ChromeDriver()) {
      var sessionId = driver.newSession(args);
      driver.navigateTo(
          sessionId, URI.create("https://www.selenium.dev/selenium/web/web-form.html"));
      var result = driver.getTitle(sessionId);
      driver.deleteSession(sessionId);
      assertEquals("Web form", result);
    }
  }

  @Test
  void takeScreenshot() throws IOException {
    // Testing method takeScreenshot
    try (var driver = new ChromeDriver()) {
      var sessionId = driver.newSession(args);
      driver.navigateTo(
          sessionId, URI.create("https://www.selenium.dev/selenium/web/web-form.html"));
      var temp = Files.createTempFile("test_", ".tmp");
      driver.takeScreenshot(sessionId, temp);
      driver.deleteSession(sessionId);
      assertTrue(Files.exists(temp));
      Files.deleteIfExists(temp);
    }
  }

  @Test
  void addCookie() {
    // Testing method addCookie
    assertDoesNotThrow(
        () -> {
          try (var driver = new ChromeDriver()) {
            var sessionId = driver.newSession(args);
            driver.navigateTo(
                sessionId, URI.create("https://www.selenium.dev/selenium/web/web-form.html"));
            driver.addCookie(
                sessionId,
                "test",
                "test",
                "/",
                "www.selenium.dev",
                false,
                false,
                LocalDateTime.now().plusDays(1),
                false);
            driver.deleteSession(sessionId);
          }
        });
  }
}
