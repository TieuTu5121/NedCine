package com.nthao.nedcine.config;

import com.nthao.nedcine.dto.auth.UserPrincipal;
import com.nthao.nedcine.entity.UserEntity;
import com.nthao.nedcine.repository.UserRepository;
import com.nthao.nedcine.service.impl.JwtServiceImpl;
import io.jsonwebtoken.ExpiredJwtException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collection;
import java.util.Date;
import java.util.Optional;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    JwtServiceImpl jwtService;

    @Autowired
    UserRepository userRepository;

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain) throws ServletException, IOException {
        String auth = request.getHeader("Authorization");
        if (auth == null || !auth.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        String token = auth.substring(7);
        try {
            String email = jwtService.extractEmail(token);
            System.out.printf("Email::" + email);

            if (email != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                Optional<UserEntity> user = userRepository.findByEmail(email);
                if (jwtService.isTokenValid(token, user.get())) {
                    System.out.printf("token valid");

                    Collection<GrantedAuthority> authorities = user.get().getAuthorities();

                    UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(user, null, authorities);
                    authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(authToken);

                    request.setAttribute("email", user.get().getEmail());
                }
            }

            filterChain.doFilter(request, response);
        } catch (ExpiredJwtException e) {
            // Xử lý ngoại lệ khi token hết hạn
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.setContentType("application/json");

            String errorMessage = "JWT token has expired.";
            String jsonResponse = String.format("{\"timestamp\": \"%s\", \"status\": %d, \"error\": \"%s\", \"message\": \"%s\", \"path\": \"%s\"}",
                    new Date(), HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized", errorMessage, request.getRequestURI());

            response.getWriter().write(jsonResponse);
            response.getWriter().flush();
        }
    }

}