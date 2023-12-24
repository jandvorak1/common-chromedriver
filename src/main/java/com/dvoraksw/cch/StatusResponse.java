package com.dvoraksw.cch;

import java.util.Objects;

record StatusResponse(
    String message, String build, String arch, String name, String version, boolean ready) {
  public StatusResponse {
    // Canonical constructor
    Objects.requireNonNull(message, "Message is null!");
    Objects.requireNonNull(build, "Build is null!");
    Objects.requireNonNull(arch, "Architecture is null!");
    Objects.requireNonNull(name, "Name is null!");
    Objects.requireNonNull(version, "Version is null!");
  }
}
