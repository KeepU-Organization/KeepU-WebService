// NewsResponse.java
package com.keepu.webAPI.dto.response;

import java.time.LocalDate;

public record NewsResponse(Long id, String title, String content, LocalDate publishedDate) {}
