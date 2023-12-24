package com.dvoraksw.cch;

import java.util.Objects;

record GetElementAttributeResponse(String value) {
  public GetElementAttributeResponse {
    // Canonical constructor
    Objects.requireNonNull(value, "Value is null!");
  }
}
