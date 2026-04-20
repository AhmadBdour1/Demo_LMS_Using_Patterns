package com.lms.service.user;

import com.lms.common.enums.Role;
import com.lms.domain.user.User;

import java.util.List;

public interface UserService {
    User createUser(User user);

    User updateUser(User user);

    User getUserById(String userId);

    List<User> getAllUsers();

    List<User> getUsersByRole(Role role);

    void deleteUser(String userId);
}
