# common-chromedriver

Common-chromedriver is a simple Java framework for remote controlling the [Chrome](https://www.google.com/chrome/)
or [Chromium](https://www.chromium.org/Home/) browser using the [ChromeDriver](https://chromedriver.chromium.org)
driver. Easy installation, simple usage, only one dependency (gson), support for Java9+ modules. By default,
chromedriver will be launched in the path specified in the PATH environment variable and on port 9115. The framework
supports Java 21 and newer versions

Common-chromedriver is still in development and currently implements only the most commonly used functionalities. If you
find any missing features, please feel free to open an issue without hesitation.

## Prerequisites

* Download and install the [Chrome](https://www.google.com/chrome/)
  or [Chromium](https://www.chromium.org/getting-involved/download-chromium/) browser.
* Download and install the [ChromeDriver](https://chromedriver.chromium.org/downloads).
* Add the path to chromedriver to the PATH environment variable.
* Install Java 21 and newer.

## Usage

```java
// List of Chrome driver arguments, this example will run the browser in the background.
    var args =
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

// It will launch chromedriver in the background and automatically close it because it implements
// the AutoCloseable interface.
    try (var driver = new ChromeDriver()) {
      // Create driver session
      var sessionId = driver.newSession(args);

      // Navigate to test page
      driver.navigateTo(sessionId, URI.create("https://www.google.com"));

      // Read page title and print it to a console
      System.out.println(driver.getTitle(sessionId));

      // Delete driver session
      driver.deleteSession(sessionId);
    } catch (ChromeDriverException e) {
      e.printStackTrace();
    }
```

## Implemented functions

The implementation is based on the W3C [WebDriver specification version 2](https://www.w3.org/TR/webdriver2/). So far,
the following functions have been implemented (in alphabetical order):
* addCookie
* elementDisplayed
* executeScript
* findElement
* getElementAttribute
* getElementPropery
* navigateTo
* status
* takeScreenshot
If you find any missing features, please feel free to open an issue without hesitation. 

## Dependency

### Maven

```xml
<dependency>
  <groupId>com.dvoraksw.cch</groupId>
  <artifactId>common-chromedriver</artifactId>
  <version>0.1.0</version>
</dependency>
```

### Gradle

```groovy
compile group: 'com.dvoraksw.cch', name: 'common-chromedriver', version: '0.1.0'
```

## References

This project has been inspired by [Selenium](https://www.selenium.dev/) project
and [video](https://youtu.be/F2jMzBW1Vl4?si=X7cdXexT4nM4tEmA) tutorial by Rakibul Yeasin on YouTube. 