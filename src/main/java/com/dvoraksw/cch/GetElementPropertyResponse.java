package com.dvoraksw.cch;

import java.util.Objects;

record GetElementPropertyResponse(String value) {
  public GetElementPropertyResponse {
    // Canonical constructor
    Objects.requireNonNull(value, "Value is null!");
  }
}
