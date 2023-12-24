package com.dvoraksw.cch;

import java.util.Objects;

record ErrorResponse(String error, String message, String stacktrace) {

  public ErrorResponse {
    // Canonical constructor
    Objects.requireNonNull(error, "Error is null!");
    Objects.requireNonNull(message, "Message is null!");
    Objects.requireNonNull(stacktrace, "Stacktrace is null!");
  }
}
