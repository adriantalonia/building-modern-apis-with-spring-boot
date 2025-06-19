package com.atrdev.todoproject.service;

import com.atrdev.todoproject.model.dto.request.AuthenticationRequest;
import com.atrdev.todoproject.model.dto.request.RegisterRequest;
import com.atrdev.todoproject.model.dto.response.AuthenticationResponse;
import com.atrdev.todoproject.model.dto.response.RegisterResponse;

public interface AuthenticationService {
    RegisterResponse register(RegisterRequest input) throws Exception;
    AuthenticationResponse login(AuthenticationRequest request);
}
