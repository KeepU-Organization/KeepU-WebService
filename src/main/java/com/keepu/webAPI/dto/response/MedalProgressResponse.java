// MedalProgressResponse.java
package com.keepu.webAPI.dto.response;

public record MedalProgressResponse(Long id, String medalName, int progress, boolean completed) {}
