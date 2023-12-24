package com.dvoraksw.cch;

import java.util.Objects;

record TakeScreenshotResponse(String value) {
  public TakeScreenshotResponse {
    // Canonical constructor
    Objects.requireNonNull(value, "Value is null!");
  }
}
