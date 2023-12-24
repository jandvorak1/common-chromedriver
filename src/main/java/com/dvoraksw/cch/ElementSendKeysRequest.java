package com.dvoraksw.cch;

import java.util.Objects;

record ElementSendKeysRequest(String text) {

  public ElementSendKeysRequest {
    // Canonical constructor
    Objects.requireNonNull(text, "Text is null!");
  }
}
