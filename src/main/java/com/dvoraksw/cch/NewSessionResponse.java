package com.dvoraksw.cch;

import java.util.Objects;

record NewSessionResponse(
    boolean acceptInsecureCerts,
    String browserName,
    String browserVersion,
    String chromedriverVersion,
    String userDataDir,
    boolean fedcmAccounts,
    String debuggerAddress,
    boolean networkConnectionEnabled,
    String pageLoadStrategy,
    String platformName,
    boolean setWindowRect,
    boolean strictFileInteractability,
    int implicit,
    int pageLoad,
    int script,
    String unhandledPromptBehavior,
    boolean credBlob,
    boolean largeBlob,
    boolean minPinLength,
    boolean prf,
    boolean virtualAuthenticators,
    String sessionId) {

  public NewSessionResponse {
    // Canonical constructor
    Objects.requireNonNull(browserName, "Browser name is null!");
    Objects.requireNonNull(browserVersion, "Browser version is null!");
    Objects.requireNonNull(chromedriverVersion, "Driver version is null!");
    Objects.requireNonNull(userDataDir, "User data directory is null!");
    Objects.requireNonNull(debuggerAddress, "Debugger address is null!");
    Objects.requireNonNull(pageLoadStrategy, "Load strategy is null!");
    Objects.requireNonNull(platformName, "Platform name are null!");
    Objects.requireNonNull(unhandledPromptBehavior, "Unhandled prompt behavior in null!");
    Objects.requireNonNull(sessionId, "Session identifier is null!");
  }
}
