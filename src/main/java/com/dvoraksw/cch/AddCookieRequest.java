package com.dvoraksw.cch;

import java.time.LocalDateTime;
import java.util.Objects;

record AddCookieRequest(
    String name,
    String value,
    String path,
    String domain,
    boolean secureOnlyFlag,
    boolean httpOnlyFlag,
    LocalDateTime expiryTime,
    boolean sameSite) {

  public AddCookieRequest {
    // Canonical constructor
    Objects.requireNonNull(name, "Name is null!");
    Objects.requireNonNull(value, "Value is null!");
    Objects.requireNonNull(path, "Path is null!");
    Objects.requireNonNull(domain, "Domain is null!");
    Objects.requireNonNull(expiryTime, "Expiry time is null!");
  }
}
