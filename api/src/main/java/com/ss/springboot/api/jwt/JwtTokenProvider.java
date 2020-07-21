package com.ss.springboot.api.jwt;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;

import static com.ss.springboot.api.constant.SecurityConstant.*;
import static java.util.Arrays.stream;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.ss.springboot.api.entity.UserPrincipal;

import org.apache.commons.lang3.*;

@Component
public class JwtTokenProvider {

	
	@Value("${jwt.secret}")
	private String secret;
	
	public	String generateJwtToken(UserPrincipal userPrincipal) {
		String[] claims = getClamimsFromUser(userPrincipal);
		return JWT.create().withIssuer(GET_ARRAYS_LLC).withAudience(GET_ARRAYS_ADMINISTRATION)
				.withIssuedAt(new Date()).withSubject(userPrincipal.getUsername())
				.withArrayClaim(AUTHORITIES,claims).withExpiresAt(new Date(System.currentTimeMillis()+EXPIRATION_TIME))
				.sign(Algorithm.HMAC512(secret.getBytes()));				
	}
	
	
	public List<GrantedAuthority> getAuthorities(String token){
		String [] clamis = getClaimsFromToken(token);
		return stream(clamis).map(SimpleGrantedAuthority::new).collect(Collectors.toList());
	}
	
	public Authentication getAuthentication(String username , List<GrantedAuthority> authorities, HttpServletRequest request) {
		UsernamePasswordAuthenticationToken  usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(username,null,authorities);
		usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
		return usernamePasswordAuthenticationToken;
	}
	
	public boolean isTokenValid(String username,String token) {
		JWTVerifier verifier = getJWTVerifier();
		return StringUtils.isNotEmpty(username) && isTokenExpired(verifier, token);
	}
	
	
	private boolean isTokenExpired(JWTVerifier verifier, String token) {
		Date expiration = verifier.verify(token).getExpiresAt();
		return expiration.before(new Date());
	}
	
	private String[] getClaimsFromToken(String token) {
		JWTVerifier verifier = getJWTVerifier();
		return verifier.verify(token).getClaim(AUTHORITIES).asArray(String.class);
	}
	
	
	public String getSubject(String token) {
		JWTVerifier verifier = getJWTVerifier();
		return verifier.verify(token).getSubject();
	}
	
	
	private JWTVerifier getJWTVerifier() {
		
		JWTVerifier verifier ;
		
		try {
			Algorithm algorithm = Algorithm.HMAC512(secret);
			verifier = JWT.require(algorithm).withIssuer(GET_ARRAYS_LLC).build();
		} catch (JWTVerificationException e) {
			throw new JWTVerificationException(TOKEN_PREFIX);
		}
		
		return verifier;
	}
	
	
	private String[] getClamimsFromUser(UserPrincipal userPrincipal) {
		List<String> authorities = new ArrayList<>();
		for(GrantedAuthority grantedAuthority : userPrincipal.getAuthorities()) {
			authorities.add(grantedAuthority.getAuthority());
		}
		return authorities.toArray(new String[0]);
	}
	
	
	
}
