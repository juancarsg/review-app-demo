package com.juancarsg.reviews.backend.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.juancarsg.reviews.backend.exception.CustomExceptionDto;
import com.juancarsg.reviews.backend.service.UserService;
import com.juancarsg.reviews.backend.util.JWTUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static com.juancarsg.reviews.backend.controller.AuthController.API_PATH_AUTH;
import static com.juancarsg.reviews.backend.controller.UserController.API_PATH_USERS;

@Component
public class JWTFilter extends OncePerRequestFilter {

    private final JWTUtil jwtUtil;
    private final UserService userService;
    private final ObjectMapper objectMapper;

    public JWTFilter(JWTUtil jwtUtil, UserService userService, ObjectMapper objectMapper) {
        this.jwtUtil = jwtUtil;
        this.userService = userService;
        this.objectMapper = objectMapper;
    }

    private final Map<String, List<String>> excludedPathsAndMethods = Map.of(
            API_PATH_AUTH, List.of(HttpMethod.POST.name()),
            API_PATH_USERS, List.of(HttpMethod.POST.name()),
            "/error", List.of("GET")
    );

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        return excludedPathsAndMethods.entrySet().stream().anyMatch(entry ->
                request.getServletPath().startsWith(entry.getKey()) &&
                        entry.getValue().contains(request.getMethod())
        );
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        String authHeader = request.getHeader("Authorization");

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            writeErrorResponse(response, HttpStatus.UNAUTHORIZED, "Bearer token required.", request.getRequestURI());
            return;
        }

        String token = authHeader.substring(7);

        Optional<String> optionalUsername = jwtUtil.extractUsernameSafe(token);

        if (optionalUsername.isEmpty()) {
            writeErrorResponse(response, HttpStatus.UNAUTHORIZED, "Invalid or malformed token.", request.getRequestURI());
            return;
        }

        String username = optionalUsername.get();

        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            UserDetails userDetails = userService.loadUserByUsername(username);

            if (jwtUtil.validateToken(token, userDetails)) {
                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authToken);
            } else {
                writeErrorResponse(response, HttpStatus.UNAUTHORIZED, "Expired or invalid token.", request.getRequestURI());
                return;
            }

        }

        filterChain.doFilter(request, response);
    }

    private void writeErrorResponse(HttpServletResponse response, HttpStatus status, String message, String path) throws IOException {
        CustomExceptionDto dto = new CustomExceptionDto();
        dto.setUrl(path);
        dto.setType(status.getReasonPhrase());
        dto.setMessage(message);
        dto.setStatus(status.value());

        response.setStatus(status.value());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.getWriter().write(objectMapper.writeValueAsString(dto));
    }

}
