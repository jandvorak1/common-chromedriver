package com.dvoraksw.cch;

import java.net.URI;
import java.util.Objects;

record NavigateToRequest(URI uri) {

  public NavigateToRequest {
    // Canonical constructor
    Objects.requireNonNull(uri, "URI is null!");
  }
}
