package com.dnd.oop.auth;

import static io.jsonwebtoken.security.Keys.*;
import static java.nio.charset.StandardCharsets.*;

import java.util.Date;

import javax.crypto.SecretKey;

import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component
public class JwtProvider {
	private final SecretKey key;
	private final long accessTokenExpirationTime;

	public JwtProvider(JwtProperties jwtProperties) {
		this.key = hmacShaKeyFor(jwtProperties.getSecretKey().getBytes(UTF_8));
		this.accessTokenExpirationTime = jwtProperties.getAccessTokenExpirationTime();
	}

	public String createToken(Long memberId) {
		Date now = new Date();
		Date expiration = new Date(now.getTime() + accessTokenExpirationTime);
		return Jwts.builder()
			.setSubject(memberId.toString())
			.setExpiration(expiration)
			.signWith(key, SignatureAlgorithm.HS256)
			.compact();
	}

	public String getPayload(String token) {
		return validateParseJws(token).getBody().getSubject();
	}

	public Jws<Claims> validateParseJws(String token) {
		try {
			return Jwts.parserBuilder()
				.setSigningKey(key)
				.build()
				.parseClaimsJws(token);
		} catch (ExpiredJwtException e) {
			throw new TokenException(AuthErrorCode.TOKEN_EXPIRED);
		} catch (JwtException e) {
			throw new TokenException(AuthErrorCode.TOKEN_INVALID);
		}
	}
}
