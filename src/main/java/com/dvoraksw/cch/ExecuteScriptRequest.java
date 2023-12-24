package com.dvoraksw.cch;

import java.util.List;
import java.util.Objects;

record ExecuteScriptRequest(String script, List<String> args) {

  public ExecuteScriptRequest {
    // Canonical constructor
    Objects.requireNonNull(script, "Script is null!");
    Objects.requireNonNull(args, "Arguments are null!");
  }
}
