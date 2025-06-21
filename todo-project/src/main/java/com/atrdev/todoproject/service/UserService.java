package com.atrdev.todoproject.service;

import com.atrdev.todoproject.model.dto.response.UserResponse;
import com.atrdev.todoproject.model.entity.User;

public interface UserService {
    UserResponse getUserInfo();
    void deleteUser();
}
