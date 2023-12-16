/*package com.inn.cafe.JWT;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtFilter extends OncePerRequestFilter {

	@Autowired
	private JwtUtil jwtUtil;
	
	@Autowired 
	CustomerUserDetailsService service;
	
	Claims claims=null;
	
	private String userName=null;
	
	@Override
	protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain)
			throws ServletException, IOException {
		
		if(httpServletRequest.getServletPath().matches("/user/login|/user/forgotPassword|user/signup"))
		{
			filterChain.doFilter(httpServletRequest,httpServletResponse);
		}
		else
		{
			String authorizationHeader=httpServletRequest.getHeader("Authorization");
			String token=null;
			
			if(authorizationHeader != null && authorizationHeader.startsWith("Bearer "))
			{
				token=authorizationHeader.substring(7);
				userName=jwtUtil.extractUsername(token);
				claims=jwtUtil.extractAllClaims(token);
			}
			if(userName!=null && SecurityContextHolder.getContext().getAuthentication()==null)
			{
				UserDetails userDetails=service.loadUserByUsername(userName);
				if(jwtUtil.validateToken(token, userDetails))
				{
					UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken=
							new UsernamePasswordAuthenticationToken(userDetails,null,userDetails.getAuthorities());
					usernamePasswordAuthenticationToken.setDetails(
							new WebAuthenticationDetailsSource().buildDetails(httpServletRequest));
					
					SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
				}
				
			}
		
		filterChain.doFilter(httpServletRequest,httpServletResponse);
			
	    }
}	
	
	public boolean isAdmin()
	{
		//System.out.println((String)claims.get("role"));
		return "admin".equalsIgnoreCase((String)claims.get("role"));
	}
	
	public boolean isUser()
	{
		return "user".equalsIgnoreCase((String)claims.get("role"));
	}
	
	public String getCurrentUser()
	{
		return userName;
	}

}
*/package com.inn.cafe.JWT;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtFilter extends OncePerRequestFilter {

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private CustomerUserDetailsService service;
    Claims claims = null;
    String userName=null;
    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain)
            throws ServletException, IOException {

        String servletPath = httpServletRequest.getServletPath();

        if (servletPath.matches("/user/login|/user/forgotPassword|user/signup")) {
            filterChain.doFilter(httpServletRequest, httpServletResponse);
        } else {
            String authorizationHeader = httpServletRequest.getHeader("Authorization");
            String token = null;
            

            if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
                token = authorizationHeader.substring(7);
                 userName = jwtUtil.extractUsername(token);

                if (userName != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                    UserDetails userDetails = service.loadUserByUsername(userName);

                    if (jwtUtil.validateToken(token, userDetails)) {
                        claims = jwtUtil.extractAllClaims(token);

                        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                                new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());

                        usernamePasswordAuthenticationToken.setDetails(
                                new WebAuthenticationDetailsSource().buildDetails(httpServletRequest));

                        SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
                    }
                }
            }

            filterChain.doFilter(httpServletRequest, httpServletResponse);
        }
    }

    public boolean isAdmin() {
        // Check if claims is not null before accessing its properties
        return claims != null && "admin".equalsIgnoreCase((String) claims.get("role"));
    }

    public boolean isUser() {
        // Check if claims is not null before accessing its properties
        return claims != null && "user".equalsIgnoreCase((String) claims.get("role"));
    }

    public String getCurrentUser() {
        // Check if userName is not null before returning
        return userName;
    }
}
