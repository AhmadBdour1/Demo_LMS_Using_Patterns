package com.lms.service.user;

import com.lms.common.enums.Role;
import com.lms.common.exceptions.EntityNotFoundException;
import com.lms.common.exceptions.ValidationException;
import com.lms.domain.user.User;
import com.lms.repository.interfaces.UserRepository;

import java.util.List;
import java.util.Objects;

public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = Objects.requireNonNull(userRepository, "userRepository cannot be null");
    }

    @Override
    public User createUser(User user) {
        validateUser(user);
        if (userRepository.existsById(user.getId())) {
            throw new ValidationException("User already exists with id: " + user.getId());
        }
        return userRepository.save(user);
    }

    @Override
    public User updateUser(User user) {
        validateUser(user);
        if (!userRepository.existsById(user.getId())) {
            throw new EntityNotFoundException("User not found with id: " + user.getId());
        }
        return userRepository.save(user);
    }

    @Override
    public User getUserById(String userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User not found with id: " + userId));
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public List<User> getUsersByRole(Role role) {
        return userRepository.findByRole(role);
    }

    @Override
    public void deleteUser(String userId) {
        if (!userRepository.existsById(userId)) {
            throw new EntityNotFoundException("User not found with id: " + userId);
        }
        userRepository.deleteById(userId);
    }

    private void validateUser(User user) {
        if (user == null) {
            throw new ValidationException("User cannot be null");
        }
        if (isBlank(user.getId())) {
            throw new ValidationException("User id cannot be blank");
        }
        if (isBlank(user.getName())) {
            throw new ValidationException("User name cannot be blank");
        }
        if (isBlank(user.getEmail()) || !user.getEmail().contains("@")) {
            throw new ValidationException("User email is invalid");
        }
    }

    private boolean isBlank(String value) {
        return value == null || value.trim().isEmpty();
    }
}
