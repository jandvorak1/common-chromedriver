package com.dvoraksw.cch;

/** A ChromeDriverException describes a runtime error condition in ChromeDriver */
public class ChromeDriverException extends RuntimeException {
  /**
   * Creates a default ChromeDriverException.
   *
   * @since 1.0
   */
  public ChromeDriverException() {
    // Class constructor
    super();
  }

  /**
   * Creates a default ChromeDriverException with the given message.
   *
   * @param message error message
   * @since 1.0
   */
  public ChromeDriverException(String message) {
    // Class constructor
    super(message);
  }

  /**
   * Creates a default ChromeDriverException with the given message and Throwable cause.
   *
   * @param message error message
   * @param cause Throwable cause
   * @since 1.0
   */
  @SuppressWarnings("unused")
  public ChromeDriverException(String message, Throwable cause) {
    // Class constructor
    super(message, cause);
  }

  /**
   * Creates a default ChromeDriverException with the given Throwable cause.
   *
   * @param cause Throwable cause
   * @since 1.0
   */
  public ChromeDriverException(Throwable cause) {
    // Class constructor
    super(cause);
  }
}
