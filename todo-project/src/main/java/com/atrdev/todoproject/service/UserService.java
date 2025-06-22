package com.atrdev.todoproject.service;

import com.atrdev.todoproject.model.dto.request.PasswordUpdateRequest;
import com.atrdev.todoproject.model.dto.response.UserResponse;

public interface UserService {
    UserResponse getUserInfo();
    void deleteUser();
    void updatePassword(PasswordUpdateRequest passwordUpdateRequest);
}
