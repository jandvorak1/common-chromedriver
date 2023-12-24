package com.dvoraksw.cch;

import java.util.Objects;

record FindElementResponse(String id, String value) {
  public FindElementResponse {
    // Canonical constructor
    Objects.requireNonNull(id, "Identifier is null!");
    Objects.requireNonNull(value, "Value is null!");
  }
}
