package com.crush.service.auth;

import com.crush.dtos.authentication.AuthenticationRequest;
import com.crush.dtos.authentication.AuthenticationResponse;
import com.crush.dtos.authentication.RegisterRequest;
import com.crush.dtos.authentication.RegisterResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

public interface AuthenticationService {

    RegisterResponse register(RegisterRequest request);

    AuthenticationResponse authenticate(AuthenticationRequest request);

    void refreshToken(HttpServletRequest request,HttpServletResponse response) throws IOException;

}
