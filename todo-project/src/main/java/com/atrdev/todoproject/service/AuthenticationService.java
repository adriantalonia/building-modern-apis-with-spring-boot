package com.atrdev.todoproject.service;

import com.atrdev.todoproject.model.dto.request.RegisterRequest;
import com.atrdev.todoproject.model.dto.response.RegisterResponse;

public interface AuthenticationService {
    RegisterResponse register(RegisterRequest input) throws Exception;
}
