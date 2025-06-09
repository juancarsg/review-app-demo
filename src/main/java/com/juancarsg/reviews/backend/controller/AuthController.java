package com.juancarsg.reviews.backend.controller;

import com.juancarsg.reviews.backend.exception.CustomException;
import com.juancarsg.reviews.backend.util.JWTUtil;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.www.BasicAuthenticationConverter;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.juancarsg.reviews.backend.controller.AuthController.API_PATH_AUTH;

@RestController
@RequestMapping(API_PATH_AUTH)
public class AuthController {

    public static final String API_PATH_AUTH = "/api/v1/auth/token";

    private final AuthenticationManager authenticationManager;
    private final JWTUtil jwtUtil;

    public AuthController(AuthenticationManager authenticationManager, JWTUtil jwtUtil) {
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
    }

    public record JwtResponse(String token) {}

    @PostMapping
    public JwtResponse generateToken(HttpServletRequest request) {
        BasicAuthenticationConverter converter = new BasicAuthenticationConverter();
        UsernamePasswordAuthenticationToken authToken = converter.convert(request);

        if (authToken == null) {
            throw new CustomException("Missing or invalid Authorization header", HttpStatus.BAD_REQUEST.value());
        }

        try {
            Authentication authentication = authenticationManager.authenticate(authToken);
            return new JwtResponse(jwtUtil.generateToken(authentication.getName()));
        } catch (AuthenticationException ex) {
            throw new CustomException("Invalid credentials", HttpStatus.UNAUTHORIZED.value());
        }
    }

}
