package com.dvoraksw.cch;

import java.util.Objects;

record FindElementRequest(LocationStrategy using, String value) {

  public FindElementRequest {
    // Canonical constructor
    Objects.requireNonNull(using, "Using is null!");
    Objects.requireNonNull(value, "Value is null!");
  }
}
