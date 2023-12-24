package com.dvoraksw.cch;

/** The class implements various types of selectors for locating elements. */
@SuppressWarnings("unused")
public enum LocationStrategy {
  /** The value represents a CSS selector. */
  CSS("css selector"),
  /** The value represents a link text selector. */
  LINK("link text"),
  /** The value represents a partial link text selector. */
  PARTIAL_LINK("partial link text"),
  /** The value represents a tag name selector. */
  TAG("tag name"),
  /** The value represents a xpath selector. */
  XPATH("xpath");

  private final String value;

  LocationStrategy(String value) {
    // Class constructor
    this.value = value;
  }

  /**
   * Returns the textual value of the enum.
   *
   * @return textual value of the enum
   */
  public String valueOf() {
    // Returning value
    return value;
  }
}
