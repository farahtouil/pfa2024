package com.farah.pfa2024.config;

import com.farah.pfa2024.service.JWTUtils;
import com.farah.pfa2024.service.UtilisateurDetService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

//This class extends OncePerRequestFilter and is used to filter each request to check for a JWT token:
/*extracting the JWT from the request headers, validating the token, and setting the authentication context if the token is valid*/
@Component
public class JWTAuthFilter extends OncePerRequestFilter {

    @Autowired
    private JWTUtils jwtUtils;

    @Autowired
    private UtilisateurDetService utilisateurDetService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        final String authHeader = request.getHeader("Authorization");
        final String jwtToken;
        final Long user_id;
        if (authHeader == null || authHeader.isBlank()) {
            filterChain.doFilter(request, response);
            return;
        }

        jwtToken = authHeader.substring(7);
        user_id = jwtUtils.extractUserId(jwtToken);

        if (user_id != null && SecurityContextHolder.getContext().getAuthentication()==null) {//This condition checks if the userMail is not null and if the security context does not already contain an authentication object.
            UserDetails userDetails = utilisateurDetService.loadUserByUsername(user_id.toString() );

            if (jwtUtils.isTokenValid(jwtToken, userDetails)) { //If authentication is successful, an Authentication object is created and set in the SecurityContext.
                SecurityContext securityContext = SecurityContextHolder.createEmptyContext();
                UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                token.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                securityContext.setAuthentication(token);
                SecurityContextHolder.setContext(securityContext);
            }

        }
        filterChain.doFilter(request, response);
    }
}
