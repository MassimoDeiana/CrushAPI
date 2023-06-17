package com.crush.service;

import com.crush.domain.entity.User;
import com.crush.dtos.authentication.AuthenticationRequest;
import com.crush.dtos.authentication.AuthenticationResponse;
import com.crush.dtos.authentication.RegisterRequest;
import com.crush.repository.TokenRepository;
import com.crush.repository.UserRepository;
import com.crush.service.auth.JwtService;
import com.crush.service.auth.impl.AuthenticationServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AuthenticationServiceTest {

    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpServletResponse response;

    @Mock
    private UserRepository userRepository;

    @Mock
    private TokenRepository tokenRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private JwtService jwtService;

    @Mock
    private AuthenticationManager authenticationManager;

    @InjectMocks
    private AuthenticationServiceImpl authenticationService;

    private RegisterRequest registerRequest;
    private User expectedUser;
    private AuthenticationRequest authenticationRequest;
    private static final String JWT_TOKEN = "jwtToken";
    private static final String REFRESH_TOKEN = "refreshToken";
    private static final String USER_PHONE_NUMBER = "+32111223344";
    private static final String PASSWORD = "password";

    @BeforeEach
    public void setup() {
        registerRequest = new RegisterRequest(USER_PHONE_NUMBER, PASSWORD, PASSWORD);
        authenticationRequest = new AuthenticationRequest(USER_PHONE_NUMBER, PASSWORD);
        expectedUser = createUserFromRequest(registerRequest);
    }

    @Test
    public void shouldRegisterUser() {
        givenUserRegistrationDetails();

        AuthenticationResponse response = authenticationService.register(registerRequest);

        assertRegistrationResponse(response);
    }

    @Test
    public void shouldAuthenticateUser() {
        givenUserAuthenticationDetails();

        AuthenticationResponse response = authenticationService.authenticate(authenticationRequest);

        assertAuthenticationResponse(response);
    }


    private User createUserFromRequest(RegisterRequest request) {
        User user = new User();
        user.setPhoneNumber(request.getPhoneNumber());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        return user;
    }

    private void givenUserRegistrationDetails() {
        when(userRepository.save(any(User.class))).thenReturn(expectedUser);
        when(jwtService.generateToken(any(User.class))).thenReturn(JWT_TOKEN);
        when(jwtService.generateRefreshToken(any(User.class))).thenReturn(REFRESH_TOKEN);
    }

    private void givenUserAuthenticationDetails() {
        when(userRepository.findByPhoneNumber(anyString())).thenReturn(Optional.of(expectedUser));
        when(jwtService.generateToken(any(User.class))).thenReturn(JWT_TOKEN);
        when(jwtService.generateRefreshToken(any(User.class))).thenReturn(REFRESH_TOKEN);
    }

    private void assertRegistrationResponse(AuthenticationResponse response) {
        assertNotNull(response);
        assertEquals(JWT_TOKEN, response.getAccessToken());
        assertEquals(REFRESH_TOKEN, response.getRefreshToken());
        verify(userRepository).save(any(User.class));
        verify(jwtService, times(1)).generateToken(any(User.class));
        verify(jwtService).generateRefreshToken(any(User.class));
    }

    private void assertAuthenticationResponse(AuthenticationResponse response) {
        assertNotNull(response);
        assertEquals(JWT_TOKEN, response.getAccessToken());
        assertEquals(REFRESH_TOKEN, response.getRefreshToken());
        verify(userRepository).findByPhoneNumber(anyString());
        verify(authenticationManager).authenticate(any(UsernamePasswordAuthenticationToken.class));
        verify(jwtService, times(1)).generateToken(any(User.class));
        verify(jwtService).generateRefreshToken(any(User.class));
    }
}
