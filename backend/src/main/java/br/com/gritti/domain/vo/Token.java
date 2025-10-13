package br.com.gritti.domain.vo;

import java.util.Date;

public record Token(String username, Boolean authenticated, Date created, Date expiration, String accessToken, String refreshToken) {}
