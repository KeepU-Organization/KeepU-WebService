// CreateBalanceAccessRequest.java
package com.keepu.webAPI.dto.request;

public record CreateBalanceAccessRequest(Long userId, String passwordOrPin) {}
