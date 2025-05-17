// MedalsProgressResponse.java
package com.keepu.webAPI.dto.response;

import java.util.List;

public record MedalsProgressResponse(List<MedalProgressResponse> medals, List<NewsResponse> news) {}
