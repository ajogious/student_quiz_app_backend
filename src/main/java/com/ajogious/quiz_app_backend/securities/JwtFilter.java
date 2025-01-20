package com.ajogious.quiz_app_backend.securities;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

import java.io.IOException;

@RequiredArgsConstructor
@Component
public class JwtFilter extends OncePerRequestFilter {

    private final JWTUtil jwtUtil;

    private final CustomUserDetailsService userDetailsService;
    
    
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        String authHeader = request.getHeader("Authorization");
        String token = null;
        String email = null;

        try {
            if (authHeader != null && authHeader.startsWith("Bearer ")) {
                token = authHeader.substring(7);
                email = jwtUtil.extractUsername(token);

                if (jwtUtil.isTokenExpired(token)) {
                    sendErrorResponse(response, "Session expired. Please log in again.", HttpServletResponse.SC_UNAUTHORIZED);
                    return; // Stop filter chain
                }
            }

            if (email != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                UserDetails userDetails = userDetailsService.loadUserByUsername(email);

                if (jwtUtil.validateToken(token, userDetails)) {
                    UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                            userDetails, null, userDetails.getAuthorities());
                    authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(authToken);
                }
            }

            filterChain.doFilter(request, response); // Continue if no issues
        } catch (Exception e) {
            sendErrorResponse(response, "Invalid or expired token.", HttpServletResponse.SC_UNAUTHORIZED);
        }
    }

    /**
     * Helper method to send a structured error response
     */
    private void sendErrorResponse(HttpServletResponse response, String message, int status) throws IOException {
        response.setContentType("application/json");
        response.setStatus(status);
        response.getWriter().write("{\"error\": \"" + message + "\"}");
        response.getWriter().flush();
        response.getWriter().close();
    }


   
}
