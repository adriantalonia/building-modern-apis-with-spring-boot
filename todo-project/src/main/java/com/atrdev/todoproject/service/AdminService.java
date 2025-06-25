package com.atrdev.todoproject.service;

import com.atrdev.todoproject.model.dto.response.UserResponse;

import java.util.List;

public interface AdminService {
    List<UserResponse> getAllUsers();
    UserResponse promoteToAdmin(long userId);
    void deleteNonAdminUser(long id);
}
