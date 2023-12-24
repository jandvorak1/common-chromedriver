package com.dvoraksw.cch;

import java.util.Objects;

record GetElementTextResponse(String value) {
  public GetElementTextResponse {
    // Canonical constructor
    Objects.requireNonNull(value, "Value is null!");
  }
}
