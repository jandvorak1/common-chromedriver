package com.dvoraksw.cch;

import java.util.Objects;

record GetTitleResponse(String value) {
  public GetTitleResponse {
    // Canonical constructor
    Objects.requireNonNull(value, "Value is null!");
  }
}
