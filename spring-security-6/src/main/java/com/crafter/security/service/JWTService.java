package com.crafter.security.service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import javax.crypto.SecretKey;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.crafter.security.entity.UserEntity;

import io.jsonwebtoken.*;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Service
public class JWTService {
	// generated the sceret key from : "https://jwtsecret.com/generate"
	private String SECRET_KEY = "e1370f3fe1873fc9600a850c6e5eb9da5a9c6b6dbbd586fa9f614c75527e617ff5276b177378d9d6da7f41eeb84c508f83e41891fcde65f988981180d7851b1f4e905b1c8d58a5eb90b2b332255d397b449ed717f772192b8fdf218662ca58d30d1b28e2bd78fb01bb1a848cc53f656cee662511ba17ea5c7d2a39c61a003d5fda3bb7c9f6f103950272785db141fea0d46b0457f36135324e8f9c7c47bf575f148111ae764050c983f8ca7e8501ed275c11696d54b495a2ba9aa916bd0a5bba93d6f028ea31a469a7c0e4ca5cacb1b647b0c0655bfad090d8fa37a594af92e06df387e7828d8609fa3855f5521a116efc644b0dce5d7287b2bf4d93d70500af";

	public String getSecretKey() {
		return SECRET_KEY;
	}

	public String generateToken(UserEntity user) {
		// TODO Auto-generated method stub
		Map<String, Object> claims = new HashMap<>();
		claims.put("role", "USER");
		claims.put("customClaim", "someValue");

		String jwt = Jwts.builder().setClaims(claims).setSubject(user.getUsername()).setIssuer("CRAFTER")
				.setIssuedAt(new Date(System.currentTimeMillis()))
				.setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 10)) // 10 hours
				.signWith(generateKey()).compact();
		return jwt;
	}

	private SecretKey generateKey() {
		// TODO Auto-generated method stub
		// byte[] byteData = Decoders.BASE64.decode(getSecretKey());
		byte[] byteData = Decoders.BASE64.decode(getSecretKey());
		return Keys.hmacShaKeyFor(byteData);
	}

	public String extractUserName(String jwtToken) {
		// TODO Auto-generated method stub

		return extractClaims(jwtToken, Claims::getSubject);
	}

	private <T> T extractClaims(String jwtToken, Function<Claims, T> claimResolver) {
		// TODO Auto-generated method stub
		Claims claims = extractClaimBasedOnToken(jwtToken);
		return claimResolver.apply(claims);
	}

	@SuppressWarnings("deprecation")
	private Claims extractClaimBasedOnToken(String jwtToken) {
		// TODO Auto-generated method stub
		return Jwts.parserBuilder().setSigningKey(generateKey()) // use your SecretKey
				.build().parseClaimsJws(jwtToken).getBody();
	}

	public boolean isTokenValid(String token, UserDetails userDetails) {
		final String userName = extractUserName(token);
		return (userName.equals(userDetails.getUsername()) && !isTokenExpired(token));
	}

	private boolean isTokenExpired(String token) {
		return extractExpiration(token).before(new Date());
	}

	private Date extractExpiration(String token) {
		return extractClaims(token, Claims::getExpiration);
	}

}
