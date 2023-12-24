package com.dvoraksw.cch;

import java.util.Objects;

record ExecuteScriptResponse(String value) {
  public ExecuteScriptResponse {
    // Canonical constructor
    Objects.requireNonNull(value, "Value is null!");
  }
}
