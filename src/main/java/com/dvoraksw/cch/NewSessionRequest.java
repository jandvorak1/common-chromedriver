package com.dvoraksw.cch;

import java.util.List;
import java.util.Objects;

record NewSessionRequest(String defaultDirectory, List<String> args) {

  public NewSessionRequest {
    // Canonical constructor
    Objects.requireNonNull(defaultDirectory, "Default directory is null!");
    Objects.requireNonNull(args, "Options are null!");
  }
}
