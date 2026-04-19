package com.lms.controller;

import com.lms.common.enums.Role;
import com.lms.domain.user.User;
import com.lms.service.user.UserService;

import java.util.List;
import java.util.Objects;

public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = Objects.requireNonNull(userService, "userService cannot be null");
    }

    public User createUser(User user) {
        return userService.createUser(user);
    }

    public User getUser(String userId) {
        return userService.getUserById(userId);
    }

    public List<User> listUsers() {
        return userService.getAllUsers();
    }

    public List<User> listUsersByRole(Role role) {
        return userService.getUsersByRole(role);
    }
}
