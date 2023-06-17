package com.crush.service.auth.impl;

import com.crush.domain.entity.Token;
import com.crush.domain.entity.User;
import com.crush.domain.enums.Role;
import com.crush.domain.enums.TokenType;
import com.crush.dtos.authentication.AuthenticationRequest;
import com.crush.dtos.authentication.AuthenticationResponse;
import com.crush.dtos.authentication.RegisterRequest;
import com.crush.repository.TokenRepository;
import com.crush.repository.UserRepository;
import com.crush.service.auth.AuthenticationService;
import com.crush.service.auth.JwtService;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {
    private final UserRepository userRepository;
    private final TokenRepository tokenRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    @Override
    public AuthenticationResponse register(RegisterRequest request) {
        var user = buildUserFromRequest(request);

        var savedUser = userRepository.save(user);
        var jwtToken = jwtService.generateToken(user);
        var refreshToken = jwtService.generateRefreshToken(user);

        saveUserToken(savedUser, jwtToken);

        return buildAuthenticationResponse(jwtToken, refreshToken);
    }

    @Override
    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        authenticateUser(request);

        var user = userRepository.findByPhoneNumber(request.getPhoneNumber()).orElseThrow();
        var jwtToken = jwtService.generateToken(user);
        var refreshToken = jwtService.generateRefreshToken(user);

        revokeAllUserTokens(user);

        saveUserToken(user, jwtToken);

        return buildAuthenticationResponse(jwtToken, refreshToken);
    }


    @Override
    public void refreshToken(HttpServletRequest request, HttpServletResponse response) throws IOException {
        final String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);

        if (authHeader == null ||!authHeader.startsWith("Bearer ")) {
            return;
        }

        String refreshToken = extractFromHeader(authHeader);
        String userPhoneNumber = jwtService.extractUsername(refreshToken);

        if (userPhoneNumber != null) {
            refreshUserToken(response, refreshToken, userPhoneNumber);
        }
    }

    private void refreshUserToken(HttpServletResponse response, String refreshToken, String userPhoneNumber) throws IOException {
        var user = this.userRepository.findByPhoneNumber(userPhoneNumber).orElseThrow();

        if (jwtService.isTokenValid(refreshToken, user)) {
            var accessToken = jwtService.generateToken(user);

            revokeAllUserTokens(user);
            saveUserToken(user, accessToken);

            var authResponse = buildAuthenticationResponse(accessToken, refreshToken);

            writeAuthResponseToOutputStream(response, authResponse);
        }
    }

    private void writeAuthResponseToOutputStream(HttpServletResponse response, AuthenticationResponse authResponse) throws IOException {
        new ObjectMapper().writeValue(response.getOutputStream(), authResponse);
    }

    private String extractFromHeader(String authHeader) {
        return authHeader.substring(7);
    }


    private void authenticateUser(AuthenticationRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getPhoneNumber(),
                        request.getPassword()
                )
        );
    }

    private AuthenticationResponse buildAuthenticationResponse(String jwtToken, String refreshToken) {
        return AuthenticationResponse.builder()
                .accessToken(jwtToken)
                .refreshToken(refreshToken)
                .build();
    }

    private User buildUserFromRequest(RegisterRequest request) {
        return User.builder()
                .phoneNumber(request.getPhoneNumber())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(Role.USER)
                .build();
    }

    private void saveUserToken(User user, String jwtToken) {
        Token token = buildToken(user, jwtToken);
        tokenRepository.save(token);
    }

    private Token buildToken(User user, String jwtToken) {
        return Token.builder()
                .user(user)
                .token(jwtToken)
                .tokenType(TokenType.BEARER)
                .expired(false)
                .revoked(false)
                .build();
    }

    private void revokeAllUserTokens(User user) {
        var validUserTokens = tokenRepository.findAllValidTokenByUser(user.getId());

        if (validUserTokens.isEmpty())
            return;

        validUserTokens.forEach(token -> {
            token.setExpired(true);
            token.setRevoked(true);
        });

        tokenRepository.saveAll(validUserTokens);
    }
}
