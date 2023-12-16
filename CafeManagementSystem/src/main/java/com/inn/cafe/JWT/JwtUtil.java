/*package com.inn.cafe.JWT;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import java.security.Key;


@FunctionalInterface
interface ClaimsResolver<T> {
    T apply(Claims claims);
}


@Service
@SuppressWarnings("deprecation")
public class JwtUtil {

	private String secret="btechdaysASKIEJKHHIJHIJLHIEHOUF12654789535sfrwfgsf";
	
	public String extractUsername(String token)
	{
		return extractClaims(token,Claims::getSubject);
		//return extractAllClaims(token, secret).getSubject();
	}
	
	public Date extractExpiration(String token)
	{
		return extractClaims(token,Claims::getExpiration);
	}
	
	public<T>T extractClaims(String token,Function<Claims,T> claimsResolver)
	{
		final Claims claims=extractAllClaims(token);
		return claimsResolver.apply(claims);
	}
	*/
	/*
	public <T> T extractClaims(String token, ClaimsResolver<T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }
	*/
/*	
	public Claims extractAllClaims(String token)
	{
		//return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
		 /*return Jwts
	                .parserBuilder()
	                .setSigningKey(secret)
	                .build()
	                .parseClaimsJws(token)
	                .getBody();*//*
		return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
		
	}
	
	private Boolean isTokenExpired(String token)
	{
		return extractExpiration(token).before(new Date());
	}
	
	public String generateToken(String username,String role)
	{
		Map<String,Object> claims=new HashMap<>();
		claims.put("role", role);
		return createToken(claims,username);
		
	}
	
	private String createToken(Map<String,Object> claims,String subject)
	{
		return Jwts.builder()
				.setClaims(claims)
				.setSubject(subject)
				.setIssuedAt(new Date(System.currentTimeMillis()))
				.setExpiration(new Date(System.currentTimeMillis() + 10 * 60 * 60 * 1000))
				.signWith(SignatureAlgorithm.HS256,secret).compact();
		
	}
	
	
	public Boolean validateToken(String token,UserDetails userDetails)
	{
		final String username=extractUsername(token);
		return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
	}
	
	private Key getSignKey() {
        byte[] keyBytes= Decoders.BASE64.decode(secret);
        return Keys.hmacShaKeyFor(keyBytes);
    }
	
}
*/


package com.inn.cafe.JWT;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
public class JwtUtil {

    //@Value("${jwt.secret}")
    private String secret="btechdaysASKIEJKHHIJHIJLHIEHOUF12654789535sfrwfgsf";

    public String extractUsername(String token) {
        return extractClaims(token, Claims::getSubject);
    }

    public Date extractExpiration(String token) {
        return extractClaims(token, Claims::getExpiration);
    }

    public <T> T extractClaims(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    public Claims extractAllClaims(String token) {
        return Jwts.parserBuilder().setSigningKey(getSignKey()).build().parseClaimsJws(token).getBody();
    }

    private Boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    public String generateToken(String username, String role) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("role", role);
        return createToken(claims, username);
    }

    private String createToken(Map<String, Object> claims, String subject) {
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(subject)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 10 * 60 * 60 * 1000))
                .signWith(getSignKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    public Boolean validateToken(String token, UserDetails userDetails) {
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }

    private Key getSignKey() {
        return Keys.hmacShaKeyFor(secret.getBytes());
    }
}
