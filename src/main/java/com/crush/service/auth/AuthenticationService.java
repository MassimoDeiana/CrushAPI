package com.crush.service.auth;

import com.crush.dtos.authentication.AuthenticationRequest;
import com.crush.dtos.authentication.AuthenticationResponse;
import com.crush.dtos.authentication.RegisterRequest;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

public interface AuthenticationService {

    AuthenticationResponse register(RegisterRequest request);

    AuthenticationResponse authenticate(AuthenticationRequest request);

    void refreshToken(HttpServletRequest request,HttpServletResponse response) throws IOException;

}
