package com.atrdev.todoproject.service;

import com.atrdev.todoproject.model.dto.response.UserResponse;
import com.atrdev.todoproject.model.entity.Authority;
import com.atrdev.todoproject.model.entity.User;
import com.atrdev.todoproject.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.StreamSupport;

@Service
public class AdminServiceImpl implements AdminService {
    private final UserRepository userRepository;

    public AdminServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public List<UserResponse> getAllUsers() {
        return StreamSupport.stream(userRepository.findAll().spliterator(), false)
                .map(this::convertToUserResponse).toList();
    }

    @Override
    @Transactional
    public UserResponse promoteToAdmin(long userId) {
        Optional<User> user = userRepository.findById(userId);

        if (user.isEmpty() || isNotAdmin(user)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "User does not exist or already an admin");
        }
        List<Authority> authorities = new ArrayList<>();
        authorities.add(new Authority("ROLE_EMPLOYEE"));
        authorities.add(new Authority("ROLE_ADMIN"));
        user.get().setAuthorities(authorities);

        User savedUser = userRepository.save(user.get());
        return convertToUserResponse(savedUser);
    }

    @Override
    @Transactional
    public void deleteNonAdminUser(long id) {
        Optional<User> user = userRepository.findById(id);

        if (user.isEmpty() || isNotAdmin(user)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "User does not exist or already an admin");
        }

        userRepository.delete(user.get());
    }

    private static boolean isNotAdmin(Optional<User> user) {
        return user.get().getAuthorities().stream()
                .anyMatch(authority -> "ROLE_ADMIN".equals(authority.getAuthority()));
    }

    private UserResponse convertToUserResponse(User user) {
        return new UserResponse(
                user.getId(),
                user.getFirstName() + " " + user.getLastName(),
                user.getEmail(),
                user.getAuthorities().stream().map(auth -> (Authority) auth).toList());
    }
}
