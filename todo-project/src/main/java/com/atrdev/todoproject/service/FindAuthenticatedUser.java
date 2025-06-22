package com.atrdev.todoproject.service;

import com.atrdev.todoproject.model.entity.User;

public interface FindAuthenticatedUser {
    User getAuthenticatedUser();
}
