package com.atrdev.todoproject.controller;

import com.atrdev.todoproject.model.dto.response.UserResponse;
import com.atrdev.todoproject.service.AdminService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.Min;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Tag(name = "Admin REST API Endpoints", description = "OPerations related to a admin")
@RestController
@RequestMapping("/api/admin")
public class AdminController {

    private final AdminService adminService;

    public AdminController(AdminService adminService) {
        this.adminService = adminService;
    }

    @Operation(summary = "Get all users", description = "Retrieve a list of all users in the systems")
    @ResponseStatus(HttpStatus.OK)
    @GetMapping
    public ResponseEntity<List<UserResponse>> getAllUsers() {
        return ResponseEntity.ok(adminService.getAllUsers());
    }

    @Operation(summary = "Promote user to admin", description = "Promote user to admin role")
    @ResponseStatus(HttpStatus.OK)
    @PutMapping("/{userId}/role")
    public ResponseEntity<UserResponse> promoteToAdmin(@PathVariable @Min(1) long userId) {
        return ResponseEntity.ok(adminService.promoteToAdmin(userId));
    }

    @Operation(summary = "Delete user", description = "Delete a non-admin user from the system")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{userId}")
    public ResponseEntity<Void> deleteUser(@PathVariable @Min(1) long userId) {
        adminService.deleteNonAdminUser(userId);
        return ResponseEntity.noContent().build();
    }
}